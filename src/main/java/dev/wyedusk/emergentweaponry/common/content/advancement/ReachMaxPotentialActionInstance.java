package dev.wyedusk.emergentweaponry.common.content.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record ReachMaxPotentialActionInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
    public static final Codec<ReachMaxPotentialActionInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(ReachMaxPotentialActionInstance::player)
    ).apply(inst, ReachMaxPotentialActionInstance::new));

    @Override
    public @NotNull Optional<ContextAwarePredicate> player() {
        return this.player;
    }

    public boolean matches(ItemStack stack) {
        if (!EvolutionUtil.isEvolvable(stack)) return false;
        return EvolutionUtil.getPotential(stack) >= EvolutionUtil.getMaxPotential(stack);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Criterion<ReachMaxPotentialActionInstance> instance(Optional<ContextAwarePredicate> player) {
        return Contents.TriggerTypes.REACH_MAX_POTENTIAL.get().createCriterion(new ReachMaxPotentialActionInstance(player));
    }
}
