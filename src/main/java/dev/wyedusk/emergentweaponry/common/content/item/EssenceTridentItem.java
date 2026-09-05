package dev.wyedusk.emergentweaponry.common.content.item;

import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.entity.ThrownEssenceTrident;
import dev.wyedusk.emergentweaponry.common.content.entity.base.BaseThrownTrident;
import dev.wyedusk.emergentweaponry.common.content.item.base.BaseTridentItem;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EssenceTridentItem extends BaseTridentItem {
    int xpToll = 1;
    int healthToll = 4;

    public EssenceTridentItem(Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 8.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.9F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0F, 2);
    }

    @Override
    public EntityType<? extends BaseThrownTrident> getEntityType() {
        return Contents.Entities.THROWN_ESSENCE_TRIDENT.get();
    }

    @Override
    public boolean meetsRiptideCondition(Player player) {
        if (player.isCreative()) return true;
        return player.totalExperience >= xpToll || player.getHealth() >= healthToll;
    }

    @Override
    public void riptidePenalty(Player player) {
        if (player.totalExperience >= xpToll) {
            player.giveExperiencePoints(-xpToll);
        } else if (player.getHealth() >= healthToll) {
            player.heal(-healthToll);
        }
    }

    @Override
    protected <T extends AbstractArrow> T createThrownTrident(Level level, LivingEntity shooter, ItemStack stack) {
        //noinspection unchecked
        return (T) new ThrownEssenceTrident(level, shooter, stack);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, @NotNull Position position, @NotNull ItemStack stack, @NotNull Direction direction) {
        ThrownEssenceTrident thrownTrident = new ThrownEssenceTrident(Contents.Entities.THROWN_ESSENCE_TRIDENT.get(), level);
        thrownTrident.setPos(position.x(), position.y(), position.z());
        thrownTrident.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownTrident;
    }
}