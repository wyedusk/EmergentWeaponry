package dev.wyedusk.emergentweaponry.datagen.server;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.advancement.EvolveItemActionInstance;
import dev.wyedusk.emergentweaponry.common.content.advancement.ImproveItemActionInstance;
import dev.wyedusk.emergentweaponry.common.content.advancement.PerfectItemActionInstance;
import dev.wyedusk.emergentweaponry.common.content.advancement.ReachMaxPotentialActionInstance;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public class EWAdvancementProvider implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer, @NotNull ExistingFileHelper existingFileHelper) {
        AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
                .display(
                        Contents.Items.MODIFICATION_TABLE_ITEM,
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".root.title"),
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".root.description"),
                        ResourceLocation.withDefaultNamespace("textures/block/stone.png"),
                        AdvancementType.TASK,
                        true,
                        false,
                        false
                )
                .addCriterion("has_modification_table", InventoryChangeTrigger.TriggerInstance.hasItems(Contents.Items.MODIFICATION_TABLE_ITEM))
                .save(consumer, EmergentWeaponry.MODID + ":root");

        AdvancementHolder reachMaxPotentialAdvancement = Advancement.Builder.advancement()
                .display(
                        Items.IRON_SWORD,
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".reach_max_potential.title"),
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".reach_max_potential.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(rootAdvancement)
                .addCriterion("reach_max_potential", ReachMaxPotentialActionInstance.instance(Optional.empty()))
                .save(consumer, EmergentWeaponry.MODID + ":reach_max_potential");

        AdvancementHolder evolveItemAdvancement = Advancement.Builder.advancement()
                .display(
                        Items.DIAMOND_SWORD,
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".evolve_item.title"),
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".evolve_item.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(reachMaxPotentialAdvancement)
                .addCriterion("evolve_item", EvolveItemActionInstance.instance(Optional.empty()))
                .save(consumer, EmergentWeaponry.MODID + ":evolve_item");

        ItemStack foiledIronSword = new ItemStack(Items.IRON_SWORD);
        foiledIronSword.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        AdvancementHolder improveItemAdvancement = Advancement.Builder.advancement()
                .display(
                        foiledIronSword,
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".improve_item.title"),
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".improve_item.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .parent(reachMaxPotentialAdvancement)
                .addCriterion("improve_item", ImproveItemActionInstance.instance(Optional.empty()))
                .save(consumer, EmergentWeaponry.MODID + ":improve_item");

        ItemStack foiledNetheriteSword = new ItemStack(Items.NETHERITE_SWORD);
        foiledNetheriteSword.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        AdvancementHolder perfectItemAdvancement = Advancement.Builder.advancement()
                .display(
                        foiledNetheriteSword,
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".perfect_item.title"),
                        Component.translatable("advancements."+ EmergentWeaponry.MODID + ".perfect_item.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .parent(improveItemAdvancement)
                .addCriterion("perfect_item", PerfectItemActionInstance.instance(Optional.empty()))
                .save(consumer, EmergentWeaponry.MODID + ":perfect_item");
    }
}
