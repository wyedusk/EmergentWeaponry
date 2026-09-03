package dev.wyedusk.emergentweaponry.datagen.client;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EWBlockStateProvider extends BlockStateProvider {
    public EWBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EmergentWeaponry.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(Contents.Blocks.MODIFICATION_TABLE.get(), models().withExistingParent(Contents.Blocks.MODIFICATION_TABLE.getId().toString(), "minecraft:block/cube")
                .texture("particle", "emergentweaponry:block/modification_table_side")
                .texture("up", "emergentweaponry:block/modification_table_top")
                .texture("down", "emergentweaponry:block/modification_table_bottom")
                .texture("north", "emergentweaponry:block/modification_table_side")
                .texture("east", "emergentweaponry:block/modification_table_side")
                .texture("south", "emergentweaponry:block/modification_table_side")
                .texture("west", "emergentweaponry:block/modification_table_side"));
    }
}
