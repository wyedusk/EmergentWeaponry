package dev.wyedusk.emergentweaponry.datagen.server.loot_tables;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EWLootTableProvider extends LootTableProvider {
    public EWLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(
                        EWBlockLootSubProvider::new,
                        LootContextParamSets.BLOCK
                )
        ), lookupProvider);
    }
}