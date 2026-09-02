package dev.wyedusk.emergentweaponry.datagen.server;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TierDataHolder;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TierData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EWEvolutionRegistriesProvider extends DatapackBuiltinEntriesProvider {
    public EWEvolutionRegistriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, bootstrapRegistries(), Set.of(EmergentWeaponry.MODID));
    }

    private static RegistrySetBuilder bootstrapRegistries() {
        return new RegistrySetBuilder()
                .add(Contents.DatapackRegistries.EVOLUTION, context -> context.register(
                        ResourceKey.create(
                                Contents.DatapackRegistries.EVOLUTION,
                                ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "tiers")
                        ), createTierPotentials())
                );
    }

    private static TierDataHolder createTierPotentials() {
        return new TierDataHolder(Map.of(
                ResourceLocation.fromNamespaceAndPath("minecraft", "leather"), new TierData(
                        List.of(
                                BuiltInRegistries.ITEM.getKey(Items.LEATHER_HELMET),
                                BuiltInRegistries.ITEM.getKey(Items.LEATHER_CHESTPLATE),
                                BuiltInRegistries.ITEM.getKey(Items.LEATHER_LEGGINGS),
                                BuiltInRegistries.ITEM.getKey(Items.LEATHER_BOOTS)
                        ),
                        5,
                        0,
                        0,
                        0,
                        5
                ),
                ResourceLocation.fromNamespaceAndPath("minecraft", "wood"), new TierData(
                        List.of(
                                BuiltInRegistries.ITEM.getKey(Items.WOODEN_AXE),
                                BuiltInRegistries.ITEM.getKey(Items.WOODEN_HOE),
                                BuiltInRegistries.ITEM.getKey(Items.WOODEN_PICKAXE),
                                BuiltInRegistries.ITEM.getKey(Items.WOODEN_SHOVEL),
                                BuiltInRegistries.ITEM.getKey(Items.WOODEN_SWORD)
                        ),
                        30,
                        8,
                        1,
                        30,
                        0
                ),
                ResourceLocation.fromNamespaceAndPath("minecraft", "stone"), new TierData(
                        List.of(
                                BuiltInRegistries.ITEM.getKey(Items.STONE_AXE),
                                BuiltInRegistries.ITEM.getKey(Items.STONE_HOE),
                                BuiltInRegistries.ITEM.getKey(Items.STONE_PICKAXE),
                                BuiltInRegistries.ITEM.getKey(Items.STONE_SHOVEL),
                                BuiltInRegistries.ITEM.getKey(Items.STONE_SWORD)
                        ),
                        70,
                        16,
                        1,
                        60,
                        0
                ),
                ResourceLocation.fromNamespaceAndPath("minecraft", "iron"), new TierData(
                        List.of(
                                BuiltInRegistries.ITEM.getKey(Items.IRON_AXE),
                                BuiltInRegistries.ITEM.getKey(Items.IRON_HOE),
                                BuiltInRegistries.ITEM.getKey(Items.IRON_PICKAXE),
                                BuiltInRegistries.ITEM.getKey(Items.IRON_SHOVEL),
                                BuiltInRegistries.ITEM.getKey(Items.IRON_SWORD),

                                BuiltInRegistries.ITEM.getKey(Items.IRON_HELMET),
                                BuiltInRegistries.ITEM.getKey(Items.IRON_CHESTPLATE),
                                BuiltInRegistries.ITEM.getKey(Items.IRON_LEGGINGS),
                                BuiltInRegistries.ITEM.getKey(Items.IRON_BOOTS),

                                // Include chainmail here, because it has the same durability as iron
                                BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_HELMET),
                                BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_CHESTPLATE),
                                BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_LEGGINGS),
                                BuiltInRegistries.ITEM.getKey(Items.CHAINMAIL_BOOTS)
                        ),
                        125,
                        18,
                        2,
                        100,
                        15
                ),
                ResourceLocation.fromNamespaceAndPath("minecraft", "gold"), new TierData(
                        List.of(
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_AXE),
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_HOE),
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_PICKAXE),
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_SHOVEL),
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_SWORD),

                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_HELMET),
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_CHESTPLATE),
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_LEGGINGS),
                                BuiltInRegistries.ITEM.getKey(Items.GOLDEN_BOOTS)
                        ),
                        15,
                        8,
                        1,
                        30,
                        15
                ),
                ResourceLocation.fromNamespaceAndPath("minecraft", "diamond"), new TierData(
                        List.of(
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_AXE),
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_HOE),
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_PICKAXE),
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_SHOVEL),
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_SWORD),

                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_HELMET),
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_CHESTPLATE),
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_LEGGINGS),
                                BuiltInRegistries.ITEM.getKey(Items.DIAMOND_BOOTS)
                        ),
                        250,
                        20,
                        4,
                        200,
                        30
                ),
                ResourceLocation.fromNamespaceAndPath("minecraft", "netherite"), new TierData(
                        List.of(
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_AXE),
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_HOE),
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_PICKAXE),
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_SHOVEL),
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_SWORD),

                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_HELMET),
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_CHESTPLATE),
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_LEGGINGS),
                                BuiltInRegistries.ITEM.getKey(Items.NETHERITE_BOOTS)
                        ),
                        500,
                        40,
                        8,
                        400,
                        60
                )
        ));
    }
}
