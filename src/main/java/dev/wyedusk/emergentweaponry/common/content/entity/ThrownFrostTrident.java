package dev.wyedusk.emergentweaponry.common.content.entity;

import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.entity.base.BaseThrownTrident;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ThrownFrostTrident extends BaseThrownTrident {
    public ThrownFrostTrident(EntityType<? extends ThrownFrostTrident> type, Level level) {
        super(type, level);
    }

    public ThrownFrostTrident(Level level, LivingEntity shooter, ItemStack stack) {
        super(Contents.Entities.THROWN_FROST_TRIDENT.get(), level, shooter, stack);
        this.setOwner(shooter);
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(Contents.Items.FROST_TRIDENT.get());
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity entity) {
        entity.setTicksFrozen(180);
    }
}
