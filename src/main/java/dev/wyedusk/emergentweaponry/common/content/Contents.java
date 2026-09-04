package dev.wyedusk.emergentweaponry.common.content;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.block.ModificationTableBlock;
import dev.wyedusk.emergentweaponry.common.content.block.entity.ModificationTableBlockEntity;
import dev.wyedusk.emergentweaponry.common.content.entity.ThrownInfernoTrident;
import dev.wyedusk.emergentweaponry.common.content.item.InfernoTridentItem;
import dev.wyedusk.emergentweaponry.common.content.menu.ModificationTableMenu;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.*;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.*;
import net.neoforged.neoforge.registries.datamaps.AdvancedDataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;
import java.util.function.Supplier;

public class Contents {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EmergentWeaponry.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EmergentWeaponry.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, EmergentWeaponry.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EmergentWeaponry.MODID);

    // Blocks
    public static class Blocks {
        public static final DeferredBlock<Block> MODIFICATION_TABLE = BLOCKS.register("modification_table", () ->
                new ModificationTableBlock(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE)));

        protected static void register(IEventBus modEventBus) { BLOCKS.register(modEventBus); }
    }
    // Block Entities
    public static class BlockEntities {
        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ModificationTableBlockEntity>> MODIFICATION_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("modification_table_block_entity", () ->
                BlockEntityType.Builder.of(ModificationTableBlockEntity::new, Blocks.MODIFICATION_TABLE.get()).build(null));

        protected static void register(IEventBus modEventBus) { BLOCK_ENTITIES.register(modEventBus); }
    }
    // Items
    public static class Items {
        public static final DeferredItem<Item> INFERNO_TRIDENT = ITEMS.register("inferno_trident", () ->
                new InfernoTridentItem(new Item.Properties()
                        .durability(325)
                        .rarity(Rarity.RARE)
                        .attributes(InfernoTridentItem.createAttributes())
                        .component(net.minecraft.core.component.DataComponents.TOOL, InfernoTridentItem.createToolProperties())));
        public static final DeferredItem<Item> FROST_TRIDENT = ITEMS.register("frost_trident", () ->
                new TridentItem(new Item.Properties()
                        .durability(325)
                        .rarity(Rarity.RARE)
                        .attributes(TridentItem.createAttributes())
                        .component(net.minecraft.core.component.DataComponents.TOOL, TridentItem.createToolProperties())));
        public static final DeferredItem<Item> ESSENCE_TRIDENT = ITEMS.register("essence_trident", () ->
                new TridentItem(new Item.Properties()
                        .durability(325)
                        .rarity(Rarity.RARE)
                        .attributes(TridentItem.createAttributes())
                        .component(net.minecraft.core.component.DataComponents.TOOL, TridentItem.createToolProperties())));

        public static final DeferredItem<Item> MODIFICATION_TABLE_ITEM = ITEMS.register("modification_table", () ->
                new BlockItem(Blocks.MODIFICATION_TABLE.get(), new Item.Properties()));

        public static final List<DeferredItem<Item>> tridents = List.of(INFERNO_TRIDENT);//, FROST_TRIDENT, ESSENCE_TRIDENT);

        protected static void register(IEventBus modEventBus) { ITEMS.register(modEventBus); }
    }
    // Entities
    public static class Entities {
        public static final Supplier<EntityType<ThrownInfernoTrident>> THROWN_INFERNO_TRIDENT = ENTITIES.register("inferno_trident", () ->
                EntityType.Builder.<ThrownInfernoTrident>of(ThrownInfernoTrident::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(4)
                        .updateInterval(20)
                        .build("inferno_trident"));

        protected static void register(IEventBus modEventBus) { ENTITIES.register(modEventBus); }
    }
    // Datapack Registries
    public static class DatapackRegistries {
        public static final ResourceKey<Registry<TierDataHolder>> EVOLUTION = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "evolution"));

        @SubscribeEvent
        public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
            event.dataPackRegistry(EVOLUTION, TierDataHolder.CODEC, TierDataHolder.CODEC);
        }

    }
    // Data Components
    public static class DataComponents {
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemEvolutionData>> EVOLUTION_DATA = DATA_COMPONENTS.registerComponentType(
                "evolution",
                builder -> builder.persistent(ItemEvolutionData.CODEC).networkSynchronized(ItemEvolutionData.STREAM_CODEC)
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<ProgressionData>> PROGRESSION_DATA = DATA_COMPONENTS.registerComponentType(
                "progression",
                builder -> builder.persistent(ProgressionData.CODEC).networkSynchronized(ProgressionData.STREAM_CODEC)
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<ProgressionLoopData>> PROGRESSION_LOOP_DATA = DATA_COMPONENTS.registerComponentType(
                "progression_loops",
                builder -> builder.persistent(ProgressionLoopData.CODEC).networkSynchronized(ProgressionLoopData.STREAM_CODEC)
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

        @SubscribeEvent
        public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
            event.register(TRANSFORM_EVOLUTION_DATA_MAP);
        }
    }
    // Menus
    public static class Menus {
        public static final DeferredHolder<MenuType<?>, MenuType<ModificationTableMenu>> MODIFICATION_TABLE_MENU = MENU_TYPES.register("modification_table_menu", () ->
                IMenuTypeExtension.create(ModificationTableMenu::new));

        protected static void register(IEventBus modEventBus) { MENU_TYPES.register(modEventBus); }
    }
    // Creative Mode Tabs
    public static class CreativeModeTabs {
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(EmergentWeaponry.MODID, () ->
                CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.emergentweaponry"))
                        .icon(() -> Items.MODIFICATION_TABLE_ITEM.get().getDefaultInstance())
                        .displayItems((parameters, output) -> {
                            output.accept(Items.MODIFICATION_TABLE_ITEM.get());

                            output.accept(Items.INFERNO_TRIDENT.get());
                            output.accept(Items.FROST_TRIDENT.get());
                            output.accept(Items.ESSENCE_TRIDENT.get());
                        }).build());

        protected static void register(IEventBus modEventBus) { CREATIVE_MODE_TABS.register(modEventBus); }
    }

    public static void registerContents(IEventBus modEventBus) {
        Blocks.register(modEventBus);
        BlockEntities.register(modEventBus);
        Items.register(modEventBus);
        Entities.register(modEventBus);
        DataComponents.register(modEventBus);
        Menus.register(modEventBus);
        CreativeModeTabs.register(modEventBus);

        modEventBus.register(DatapackRegistries.class);
        modEventBus.register(DataMaps.class);
    }
}