package dev.wyedusk.emergentweaponry.datagen.server;

import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TierPotentialData;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TransformEvolutionData;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TransformEvolutionFlag;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TransformEvolutionInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EWDataMapProvider extends DataMapProvider {
    public EWDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.@NotNull Provider provider) {
        // Transformation Evolutions Data Map
        // Add Vanilla Tool upgrades
        addVanillaToolUpgrades();
        // Add Vanilla Armour upgrades
        addVanillaArmorUpgrades();
        // Add Custom upgrades
        addCustomUpgrades();
    }

    private void addVanillaToolUpgrades() {
        this.builder(Contents.DataMaps.TRANSFORM_EVOLUTION_DATA_MAP)
                .replace(false)
                // Wood -> Stone
                .add(BuiltInRegistries.ITEM.getKey(Items.WOODEN_AXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.STONE_AXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.WOODEN_HOE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.STONE_HOE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.WOODEN_PICKAXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.STONE_PICKAXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.WOODEN_SHOVEL), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.STONE_SHOVEL), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.WOODEN_SWORD), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.STONE_SWORD), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                // Stone -> Iron
                .add(BuiltInRegistries.ITEM.getKey(Items.STONE_AXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_AXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.STONE_HOE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_HOE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.STONE_PICKAXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_PICKAXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.STONE_SHOVEL), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_SHOVEL), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.STONE_SWORD), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_SWORD), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                // Iron -> Gold
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_AXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_AXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_HOE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_HOE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_PICKAXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_PICKAXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_SHOVEL), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_SHOVEL), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_SWORD), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_SWORD), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                // Gold -> Diamond
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_AXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_AXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_HOE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_HOE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_PICKAXE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_PICKAXE), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_SHOVEL), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_SHOVEL), TransformEvolutionFlag.VANILLA_TOOL)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_SWORD), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_SWORD), TransformEvolutionFlag.VANILLA_TOOL)
                )), false);
    }
    private void addVanillaArmorUpgrades() {
        this.builder(Contents.DataMaps.TRANSFORM_EVOLUTION_DATA_MAP)
                .replace(false)
                // Leather -> Chainmail
                .add(BuiltInRegistries.ITEM.getKey(Items.LEATHER_HELMET), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_HELMET), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.LEATHER_CHESTPLATE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_CHESTPLATE), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.LEATHER_LEGGINGS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_LEGGINGS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.LEATHER_BOOTS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_BOOTS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                // Chainmail -> Iron
                .add(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_HELMET), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_HELMET), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_CHESTPLATE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_CHESTPLATE), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_LEGGINGS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_LEGGINGS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_BOOTS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.IRON_BOOTS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                // Iron -> Gold
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_HELMET), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_HELMET), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_CHESTPLATE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_CHESTPLATE), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_LEGGINGS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_LEGGINGS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_BOOTS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_BOOTS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                // Gold -> Diamond
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_HELMET), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_HELMET), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_CHESTPLATE), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_CHESTPLATE), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_LEGGINGS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_LEGGINGS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLDEN_BOOTS), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(BuiltInRegistries.ITEM.getKey(Items.DIAMOND_BOOTS), TransformEvolutionFlag.VANILLA_ARMOR)
                )), false);
    }
    private void addCustomUpgrades() {
        this.builder(Contents.DataMaps.TRANSFORM_EVOLUTION_DATA_MAP)
                .replace(false)
                // Tridents
                .add(BuiltInRegistries.ITEM.getKey(Items.TRIDENT), new TransformEvolutionData(List.of(
                        new TransformEvolutionInstance(ResourceLocation.fromNamespaceAndPath("emergentweaponry", "fire_trident"), TransformEvolutionFlag.CUSTOM),
                        new TransformEvolutionInstance(ResourceLocation.fromNamespaceAndPath("emergentweaponry", "soul_trident"), TransformEvolutionFlag.CUSTOM)
                )), false);
    }
}
