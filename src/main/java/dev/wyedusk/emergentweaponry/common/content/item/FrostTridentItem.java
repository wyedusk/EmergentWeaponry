package dev.wyedusk.emergentweaponry.common.content.item;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.entity.ThrownFrostTrident;
import dev.wyedusk.emergentweaponry.common.content.entity.base.BaseThrownTrident;
import dev.wyedusk.emergentweaponry.common.content.item.base.BaseTridentItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class FrostTridentItem extends BaseTridentItem {
    public FrostTridentItem(Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 8.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.9F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        String itemId = Contents.Items.FROST_TRIDENT.getId().getPath();
        tooltipComponents.add(Component.translatable("tooltip." + EmergentWeaponry.MODID + "." + itemId + ".attack_special").withStyle(ChatFormatting.DARK_GRAY));

        HolderLookup.Provider registries = context.registries();
        if (registries != null) {
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = registries.lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> riptideEnchant = enchantmentRegistry.get(Enchantments.RIPTIDE);
            if (riptideEnchant.isPresent()) {
                ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
                if (enchantments != null) {
                    int riptideLevel = enchantments.getLevel(riptideEnchant.get().getDelegate());
                    if (riptideLevel > 0)
                        tooltipComponents.add(Component.translatable("tooltip." + EmergentWeaponry.MODID + "." + itemId + ".riptide_special").withStyle(ChatFormatting.DARK_GRAY));
                }
            }
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0F, 2);
    }

    @Override
    public EntityType<? extends BaseThrownTrident> getEntityType() {
        return Contents.Entities.THROWN_FROST_TRIDENT.get();
    }

    @Override
    public boolean meetsRiptideCondition(Player player) {
        return player.isInWaterOrRain() || player.isFullyFrozen();
    }

    @Override
    protected <T extends AbstractArrow> T createThrownTrident(Level level, LivingEntity shooter, ItemStack stack) {
        //noinspection unchecked
        return (T) new ThrownFrostTrident(level, shooter, stack);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, @NotNull Position position, @NotNull ItemStack stack, @NotNull Direction direction) {
        ThrownFrostTrident thrownTrident = new ThrownFrostTrident(Contents.Entities.THROWN_FROST_TRIDENT.get(), level);
        thrownTrident.setPos(position.x(), position.y(), position.z());
        thrownTrident.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownTrident;
    }
}