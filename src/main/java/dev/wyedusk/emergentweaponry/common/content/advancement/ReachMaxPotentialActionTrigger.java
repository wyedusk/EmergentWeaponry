package dev.wyedusk.emergentweaponry.common.content.advancement;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ReachMaxPotentialActionTrigger extends SimpleCriterionTrigger<ReachMaxPotentialActionInstance> {
    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(stack));
    }

    @Override
    public @NotNull Codec<ReachMaxPotentialActionInstance> codec() {
        return ReachMaxPotentialActionInstance.CODEC;
    }
}