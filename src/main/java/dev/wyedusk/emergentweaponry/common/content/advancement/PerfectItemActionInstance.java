package dev.wyedusk.emergentweaponry.common.content.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.wyedusk.emergentweaponry.common.config.ServerConfig;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.EvolutionUtil;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record PerfectItemActionInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
    public static final Codec<PerfectItemActionInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(PerfectItemActionInstance::player)
    ).apply(inst, PerfectItemActionInstance::new));

    @Override
    public @NotNull Optional<ContextAwarePredicate> player() {
        return this.player;
    }

    public boolean matches(ItemStack stack) {
        return (EvolutionUtil.getImprovementTier(stack) == ServerConfig.MAX_IMPROVEMENT_TIER.getAsInt()) && (EvolutionUtil.getPotential(stack) == EvolutionUtil.getMaxPotential(stack));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Criterion<PerfectItemActionInstance> instance(Optional<ContextAwarePredicate> player) {
        return Contents.TriggerTypes.PERFECT_ITEM.get().createCriterion(new PerfectItemActionInstance(player));
    }
}
