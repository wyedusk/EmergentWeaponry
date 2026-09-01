package dev.wyedusk.emergentweaponry.common.content;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ItemEvolutionData;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TierPotentialData;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TransformEvolutionData;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TransformEvolutionDataMerger;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.AdvancedDataMapType;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class Contents {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EmergentWeaponry.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EmergentWeaponry.MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EmergentWeaponry.MODID);

    // Blocks
    // Items
    // Data Components
    public static class DataComponents {
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemEvolutionData>> EVOLUTION_DATA = DATA_COMPONENTS.registerComponentType(
                "evolution",
                builder -> builder.persistent(ItemEvolutionData.CODEC).networkSynchronized(ItemEvolutionData.STREAM_CODEC)
        );

        protected static void register(IEventBus modEventBus) { DATA_COMPONENTS.register(modEventBus); }
    }
    // Data Maps
    public static class DataMaps {
        public static final AdvancedDataMapType<Item, TransformEvolutionData, ?> TRANSFORM_EVOLUTION_DATA_MAP = AdvancedDataMapType.builder(
                ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "transform_evolutions"),
                Registries.ITEM,
                TransformEvolutionData.CODEC
        ).merger(new TransformEvolutionDataMerger()).build();
        public static final DataMapType<Item, TierPotentialData> TIER_POTENTIAL_DATA_MAP = DataMapType.builder(
                ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "tier_potential"),
                Registries.ITEM,
                TierPotentialData.CODEC
        ).build();

        @SubscribeEvent
        public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
            event.register(TRANSFORM_EVOLUTION_DATA_MAP);
            event.register(TIER_POTENTIAL_DATA_MAP);
        }
    }
    // Creative Mode Tabs

    public static void registerContents(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        DataComponents.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.register(DataMaps.class);
    }
}