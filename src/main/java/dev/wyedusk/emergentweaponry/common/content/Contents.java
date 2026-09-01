package dev.wyedusk.emergentweaponry.common.content;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Contents {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EmergentWeaponry.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EmergentWeaponry.MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EmergentWeaponry.MODID);

    // Blocks
    // Items
    // Data Components
    public static class DataComponents {
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<EvolutionData>> EVOLUTION_DATA = DATA_COMPONENTS.registerComponentType(
                "evolution",
                builder -> builder.persistent(EvolutionData.CODEC).networkSynchronized(EvolutionData.STREAM_CODEC)
        );

        protected static void register(IEventBus modEventBus) { DATA_COMPONENTS.register(modEventBus); }
    }
    // Creative Mode Tabs
    public static void registerContents(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        DataComponents.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}