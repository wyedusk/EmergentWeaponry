package dev.wyedusk.emergentweaponry.datagen.server;

import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EWItemTagsProvider extends ItemTagsProvider {
    public EWItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockLookup) {
        super(output, lookupProvider, blockLookup);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (DeferredItem<Item> defItem : Contents.Items.tridents) {
            Item item = defItem.get();
            addGenericUsableItemTags(item);
            addWeaponTags(item);
            tag(ItemTags.TRIDENT_ENCHANTABLE).add(item);
            tag(Tags.Items.MELEE_WEAPON_TOOLS).add(item);
            tag(Tags.Items.RANGED_WEAPON_TOOLS).add(item);
        }
    }

    private void addGenericUsableItemTags(Item item) {
        tag(ItemTags.DURABILITY_ENCHANTABLE).add(item);
        tag(ItemTags.VANISHING_ENCHANTABLE).add(item);
    }

    private void addWeaponTags(Item item) {
        addGenericUsableItemTags(item);
        tag(ItemTags.WEAPON_ENCHANTABLE).add(item);
    }
}
