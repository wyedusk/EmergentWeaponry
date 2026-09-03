package dev.wyedusk.emergentweaponry.datagen.server.loot_tables;

import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class EWBlockLootSubProvider extends BlockLootSubProvider {
    protected EWBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    @Override
    protected void generate() {
        this.dropSelf(Contents.Blocks.MODIFICATION_TABLE.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        //noinspection unchecked
        return (List<Block>) Contents.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList();
    }
}
