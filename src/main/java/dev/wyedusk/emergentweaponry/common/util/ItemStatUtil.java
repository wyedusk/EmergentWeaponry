package dev.wyedusk.emergentweaponry.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.HashMap;
import java.util.Map;

public class ItemStatUtil {
    public static Map<Holder<Attribute>, Double> getDefaultItemStats(Item item, EquipmentSlot slot) {
        Map<Holder<Attribute>, Double> stats = new HashMap<>();
        ItemAttributeModifiers modifiers = item.components().get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (modifiers != null) {
            for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
                if (entry.slot().test(slot)) {
                    stats.put(entry.attribute(), stats.getOrDefault(entry.attribute(), 0.0) + entry.modifier().amount());
                }
            }
        }
        return stats;
    }

    public static Map<Holder<Attribute>, Double> getItemStats(ItemStack stack, EquipmentSlot slot) {
        Map<Holder<Attribute>, Double> stats = new HashMap<>();
        Map<Holder<Attribute>, Double> stats_base = new HashMap<>();
        Map<Holder<Attribute>, Double> stats_mulbase = new HashMap<>();
        Map<Holder<Attribute>, Double> stats_multotal = new HashMap<>();

        stack.forEachModifier(slot, (attr, mod) -> {
            if (mod.operation() == AttributeModifier.Operation.ADD_VALUE)
                stats_base.put(attr, stats_base.getOrDefault(attr, 0.0) + mod.amount());
            if (mod.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                stats_mulbase.put(attr, stats_mulbase.getOrDefault(attr, 0.0) + mod.amount());
            if (mod.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                stats_multotal.put(attr, stats_multotal.getOrDefault(attr, 0.0) + mod.amount());

        });

        stats_base.forEach((k, v) -> {
            stats.put(k, (v * stats_mulbase.getOrDefault(k, 1.0)) * stats_multotal.getOrDefault(k, 1.0));
        });

        return stats;
    }
}
