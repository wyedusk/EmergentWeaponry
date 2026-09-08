package dev.wyedusk.emergentweaponry.common.content.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record EvolveItemActionInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
    public static final Codec<EvolveItemActionInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(EvolveItemActionInstance::player)
    ).apply(inst, EvolveItemActionInstance::new));

    @Override
    public @NotNull Optional<ContextAwarePredicate> player() {
        return this.player;
    }

    public boolean matches(ItemStack stack) {
        return true;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Criterion<EvolveItemActionInstance> instance(Optional<ContextAwarePredicate> player) {
        return Contents.TriggerTypes.EVOLVE_ITEM.get().createCriterion(new EvolveItemActionInstance(player));
    }
}
