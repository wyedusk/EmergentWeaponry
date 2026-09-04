package dev.wyedusk.emergentweaponry.datagen;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.datagen.client.EWBlockStateProvider;
import dev.wyedusk.emergentweaponry.datagen.client.EWItemModelProvider;
import dev.wyedusk.emergentweaponry.datagen.server.*;
import dev.wyedusk.emergentweaponry.datagen.server.loot_tables.EWLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class EWDataGenerator implements IModBusEvent {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        // Client-side Providers
        generator.addProvider(
                event.includeClient(),
                new EWBlockStateProvider(
                        output,
                        existingFileHelper
                )
        );
        generator.addProvider(
                event.includeClient(),
                new EWItemModelProvider(
                        output,
                        existingFileHelper
                )
        );
        // Server-side Providers
        event.createProvider(EWDataMapProvider::new);
        generator.addProvider(
                event.includeServer(),
                new EWEvolutionRegistriesProvider(
                        output,
                        provider
                )
        );
        generator.addProvider(
                event.includeServer(),
                new EWLootTableProvider(
                        output,
                        provider
                )
        );
        EWBlockTagsProvider blockTagsProvider = new EWBlockTagsProvider(
                output,
                provider,
                existingFileHelper
        );
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new EWItemTagsProvider(
                output,
                provider,
                blockTagsProvider.contentsGetter()
        ));
        generator.addProvider(
                event.includeServer(),
                new EWRecipeProvider(
                        output,
                        provider
                )
        );
    }
}