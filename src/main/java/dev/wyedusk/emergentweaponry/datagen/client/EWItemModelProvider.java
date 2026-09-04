package dev.wyedusk.emergentweaponry.datagen.client;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EWItemModelProvider extends ItemModelProvider {
    public EWItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EmergentWeaponry.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generateTridentModel(Contents.Items.INFERNO_TRIDENT.get());
        generateTridentModel(Contents.Items.FROST_TRIDENT.get());
        generateTridentModel(Contents.Items.ESSENCE_TRIDENT.get());

    }

    private void generateTridentModel(Item tridentItem) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(tridentItem);
        String name = id.getPath();

        ItemModelBuilder handModel = getBuilder(name + "_in_hand")
                .parent(getExistingFile(mcLoc("item/trident_in_hand")));
        ItemModelBuilder throwModel = getBuilder(name + "_throwing")
                .parent(getExistingFile(mcLoc("item/trident_throwing")));

        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", modLoc("item/" + name))
                .override()
                .predicate(ResourceLocation.withDefaultNamespace("in_hand"), 1.0F)
                .model(handModel)
                .end()
                .override()
                .predicate(ResourceLocation.withDefaultNamespace("throwing"), 1.0F)
                .model(throwModel)
                .end();
    }
}
