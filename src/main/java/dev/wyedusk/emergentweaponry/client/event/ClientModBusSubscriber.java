package dev.wyedusk.emergentweaponry.client.event;

import dev.wyedusk.emergentweaponry.client.gui.component.ClientPotentialBarTooltipComponent;
import dev.wyedusk.emergentweaponry.client.gui.screen.ModificationTableMenuScreen;
import dev.wyedusk.emergentweaponry.client.rendering.EWBlockEntityWithoutLevelRenderer;
import dev.wyedusk.emergentweaponry.client.rendering.entity.ThrownTridentRenderer;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.item.base.BaseTridentItem;
import dev.wyedusk.emergentweaponry.common.gui.component.PotentialBarTooltipComponent;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class ClientModBusSubscriber implements IModBusEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (DeferredItem<Item> item : Contents.Items.tridents) {
                ItemProperties.register(item.get(), ResourceLocation.withDefaultNamespace("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
                ItemProperties.register(item.get(), ResourceLocation.withDefaultNamespace("in_hand"), (stack, world, entity, seed) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) ? 1.0F : 0.0F);
            }
        });
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(Contents.Menus.MODIFICATION_TABLE_MENU.get(), ModificationTableMenuScreen::new);
    }

    @SubscribeEvent
    public static void registerTooltipFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(PotentialBarTooltipComponent.class, ClientPotentialBarTooltipComponent::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (DeferredItem<Item> item : Contents.Items.tridents) {
            ResourceLocation tridentId = BuiltInRegistries.ITEM.getKey(item.get());
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(tridentId.getNamespace(), "textures/entity/" + tridentId.getPath() + "/" + tridentId.getPath() + ".png");
            ModelLayerLocation modelLayer = ModelLayers.TRIDENT;

            event.registerEntityRenderer(((BaseTridentItem) item.get()).getEntityType(), context -> new ThrownTridentRenderer(context, tridentId, texture, modelLayer));
        }
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new EWBlockEntityWithoutLevelRenderer();
            }
        }, Contents.Items.INFERNO_TRIDENT);
    }
}
