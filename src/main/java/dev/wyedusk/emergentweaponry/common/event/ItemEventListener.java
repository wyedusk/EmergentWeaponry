package dev.wyedusk.emergentweaponry.common.event;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ProgressionUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class ItemEventListener {
    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        if (!EvolutionUtil.isEvolvable(stack)) return;

        int improvementTier = EvolutionUtil.getImprovementTier(stack);
        if (!(improvementTier > 0)) return;

        if (ProgressionUtil.canTrackDamageDealt(stack) || ProgressionUtil.canTrackEntitiesKilled(stack)) {
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "improve_attack_damage"),
                    0.5 * improvementTier,
                    AttributeModifier.Operation.ADD_VALUE
            ), EquipmentSlotGroup.MAINHAND);
        }
        if (ProgressionUtil.canTrackDamageTaken(stack)) {
            event.addModifier(Attributes.ARMOR, new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "improve_armor"),
                    0.5 * improvementTier,
                    AttributeModifier.Operation.ADD_VALUE
            ), EquipmentSlotGroup.ARMOR);
            event.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "improve_armor_toughness"),
                    0.35 * improvementTier,
                    AttributeModifier.Operation.ADD_VALUE
            ), EquipmentSlotGroup.ARMOR);
        }
        if (ProgressionUtil.canTrackBlocksBroken(stack)) {
            event.addModifier(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "improve_block_break_speed"),
                    0.25 * improvementTier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.MINING_EFFICIENCY, new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "improve_mining_efficiency"),
                    0.25 * improvementTier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.SUBMERGED_MINING_SPEED, new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "improve_submerged_mining_efficiency"),
                    0.1 * improvementTier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ), EquipmentSlotGroup.MAINHAND);
        }
    }
}
