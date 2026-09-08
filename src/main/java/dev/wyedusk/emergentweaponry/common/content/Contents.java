package dev.wyedusk.emergentweaponry.common.content;

import com.mojang.serialization.MapCodec;
import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.advancement.EvolveItemActionTrigger;
import dev.wyedusk.emergentweaponry.common.content.advancement.ImproveItemActionTrigger;
import dev.wyedusk.emergentweaponry.common.content.advancement.PerfectItemActionTrigger;
import dev.wyedusk.emergentweaponry.common.content.advancement.ReachMaxPotentialActionTrigger;
import dev.wyedusk.emergentweaponry.common.content.block.ModificationTableBlock;
import dev.wyedusk.emergentweaponry.common.content.block.entity.ModificationTableBlockEntity;
import dev.wyedusk.emergentweaponry.common.content.condition.AnyVanillaUpgradeEnabledCondition;
import dev.wyedusk.emergentweaponry.common.content.condition.ImprovementFeatureEnabledCondition;
import dev.wyedusk.emergentweaponry.common.content.entity.ThrownEssenceTrident;
import dev.wyedusk.emergentweaponry.common.content.entity.ThrownFrostTrident;
import dev.wyedusk.emergentweaponry.common.content.entity.ThrownInfernoTrident;
import dev.wyedusk.emergentweaponry.common.content.item.EssenceTridentItem;
import dev.wyedusk.emergentweaponry.common.content.item.FrostTridentItem;
import dev.wyedusk.emergentweaponry.common.content.item.InfernoTridentItem;
import dev.wyedusk.emergentweaponry.common.content.gui.menu.ModificationTableMenu;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.component.ItemEvolutionData;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.component.ProgressionData;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.component.ProgressionLoopData;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map.TierDataHolder;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map.TransformEvolutionData;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map.TransformEvolutionDataMerger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.*;
import net.neoforged.neoforge.registries.datamaps.AdvancedDataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;
import java.util.function.Supplier;

public class Contents {
    // Deferred Registers
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EmergentWeaponry.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EmergentWeaponry.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EmergentWeaponry.MODID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, EmergentWeaponry.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EmergentWeaponry.MODID);
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, EmergentWeaponry.MODID);
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(Registries.TRIGGER_TYPE, EmergentWeaponry.MODID);

    // Content Classes
    public static class Blocks {
        public static final DeferredBlock<Block> MODIFICATION_TABLE = BLOCKS.register("modification_table", () ->
                new ModificationTableBlock(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE)));

        protected static void register(IEventBus modEventBus) { BLOCKS.register(modEventBus); }
    }

    public static class BlockEntities {
        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ModificationTableBlockEntity>> MODIFICATION_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("modification_table_block_entity", () ->
                BlockEntityType.Builder.of(ModificationTableBlockEntity::new, Blocks.MODIFICATION_TABLE.get()).build(null));

        protected static void register(IEventBus modEventBus) { BLOCK_ENTITIES.register(modEventBus); }
    }

    public static class Conditions {
        public static final Supplier<MapCodec<ImprovementFeatureEnabledCondition>> IMPROVEMENT_FEATURE_ENABLED = CONDITION_CODECS.register(
                "improvement_feature_enabled", () -> ImprovementFeatureEnabledCondition.CODEC
        );
        public static final Supplier<MapCodec<AnyVanillaUpgradeEnabledCondition>> ANY_VANILLA_UPGRADE_ENABLED = CONDITION_CODECS.register(
                "any_vanilla_upgrade_enabled", () -> AnyVanillaUpgradeEnabledCondition.CODEC
        );

        protected static void register(IEventBus modEventBus) { CONDITION_CODECS.register(modEventBus); }
    }

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

    public static class DamageTypes {
        public static final ResourceKey<DamageType> ESSENCE_TRIDENT_RIPTIDE =
                ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "essence_trident_riptide"));

        public static DamageSource essenceTridentRiptide(Entity entity, Item item) {
            return new DamageSource(
                    entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ESSENCE_TRIDENT_RIPTIDE),
                    entity
            );
        }

        public static void register() {}
    }

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

    public static class DatapackRegistries {
        public static final ResourceKey<Registry<TierDataHolder>> EVOLUTION = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "evolution"));

        @SubscribeEvent
        public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
            event.dataPackRegistry(EVOLUTION, TierDataHolder.CODEC, TierDataHolder.CODEC);
        }

    }

    public static class Entities {
        public static final Supplier<EntityType<ThrownInfernoTrident>> THROWN_INFERNO_TRIDENT = ENTITIES.register("inferno_trident", () ->
                EntityType.Builder.<ThrownInfernoTrident>of(ThrownInfernoTrident::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(4)
                        .updateInterval(20)
                        .build("inferno_trident"));
        public static final Supplier<EntityType<ThrownFrostTrident>> THROWN_FROST_TRIDENT = ENTITIES.register("frost_trident", () ->
                EntityType.Builder.<ThrownFrostTrident>of(ThrownFrostTrident::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(4)
                        .updateInterval(20)
                        .build("frost_trident"));
        public static final Supplier<EntityType<ThrownEssenceTrident>> THROWN_ESSENCE_TRIDENT = ENTITIES.register("essence_trident", () ->
                EntityType.Builder.<ThrownEssenceTrident>of(ThrownEssenceTrident::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(4)
                        .updateInterval(20)
                        .build("essence_trident"));

        protected static void register(IEventBus modEventBus) { ENTITIES.register(modEventBus); }
    }

    public static class Items {
        public static final DeferredItem<Item> INFERNO_TRIDENT = ITEMS.register("inferno_trident", () ->
                new InfernoTridentItem(new Item.Properties()
                        .durability(325)
                        .rarity(Rarity.RARE)
                        .attributes(InfernoTridentItem.createAttributes())
                        .component(net.minecraft.core.component.DataComponents.TOOL, InfernoTridentItem.createToolProperties())));
        public static final DeferredItem<Item> FROST_TRIDENT = ITEMS.register("frost_trident", () ->
                new FrostTridentItem(new Item.Properties()
                        .durability(325)
                        .rarity(Rarity.RARE)
                        .attributes(FrostTridentItem.createAttributes())
                        .component(net.minecraft.core.component.DataComponents.TOOL, FrostTridentItem.createToolProperties())));
        public static final DeferredItem<Item> ESSENCE_TRIDENT = ITEMS.register("essence_trident", () ->
                new EssenceTridentItem(new Item.Properties()
                        .durability(325)
                        .rarity(Rarity.RARE)
                        .attributes(EssenceTridentItem.createAttributes())
                        .component(net.minecraft.core.component.DataComponents.TOOL, EssenceTridentItem.createToolProperties())));

        public static final DeferredItem<Item> MODIFICATION_TABLE_ITEM = ITEMS.register("modification_table", () ->
                new BlockItem(Blocks.MODIFICATION_TABLE.get(), new Item.Properties()));

        public static final List<DeferredItem<Item>> tridents = List.of(
                INFERNO_TRIDENT,
                FROST_TRIDENT,
                ESSENCE_TRIDENT
        );

        protected static void register(IEventBus modEventBus) { ITEMS.register(modEventBus); }
    }

    public static class Menus {
        public static final DeferredHolder<MenuType<?>, MenuType<ModificationTableMenu>> MODIFICATION_TABLE_MENU = MENU_TYPES.register("modification_table_menu", () ->
                IMenuTypeExtension.create(ModificationTableMenu::new));

        protected static void register(IEventBus modEventBus) { MENU_TYPES.register(modEventBus); }
    }

    public static class TriggerTypes {
        public static final Supplier<ReachMaxPotentialActionTrigger> REACH_MAX_POTENTIAL = TRIGGER_TYPES.register(
                "reach_max_potential", ReachMaxPotentialActionTrigger::new
        );
        public static final Supplier<EvolveItemActionTrigger> EVOLVE_ITEM = TRIGGER_TYPES.register(
                "evolve_item", EvolveItemActionTrigger::new
        );
        public static final Supplier<ImproveItemActionTrigger> IMPROVE_ITEM = TRIGGER_TYPES.register(
                "improve_item", ImproveItemActionTrigger::new
        );
        public static final Supplier<PerfectItemActionTrigger> PERFECT_ITEM = TRIGGER_TYPES.register(
                "perfect_item", PerfectItemActionTrigger::new
        );

        protected static void register(IEventBus modEventBus) { TRIGGER_TYPES.register(modEventBus); }
    }

    // Register Function
    public static void registerContents(IEventBus modEventBus) {
        Blocks.register(modEventBus);
        BlockEntities.register(modEventBus);
        Conditions.register(modEventBus);
        CreativeModeTabs.register(modEventBus);
        DataComponents.register(modEventBus);
        Entities.register(modEventBus);
        Items.register(modEventBus);
        Menus.register(modEventBus);
        TriggerTypes.register(modEventBus);

        modEventBus.register(DataMaps.class);
        modEventBus.register(DatapackRegistries.class);
    }
}