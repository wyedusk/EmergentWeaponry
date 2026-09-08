package dev.wyedusk.emergentweaponry.common.content.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.EvolutionUtil;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record ImproveItemActionInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
    public static final Codec<ImproveItemActionInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(ImproveItemActionInstance::player)
    ).apply(inst, ImproveItemActionInstance::new));

    @Override
    public @NotNull Optional<ContextAwarePredicate> player() {
        return this.player;
    }

    public boolean matches(ItemStack stack) {
        return EvolutionUtil.getImprovementTier(stack) >= 1;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Criterion<ImproveItemActionInstance> instance(Optional<ContextAwarePredicate> player) {
        return Contents.TriggerTypes.IMPROVE_ITEM.get().createCriterion(new ImproveItemActionInstance(player));
    }
}
