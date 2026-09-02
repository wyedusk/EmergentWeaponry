package dev.wyedusk.emergentweaponry.client.event;

import dev.wyedusk.emergentweaponry.client.gui.component.ClientPotentialBarTooltipComponent;
import dev.wyedusk.emergentweaponry.client.gui.screen.ModificationTableMenuScreen;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.gui.component.PotentialBarTooltipComponent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber
public class ClientModBusSubscriber implements IModBusEvent {
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(Contents.Menus.MODIFICATION_TABLE_MENU.get(), ModificationTableMenuScreen::new);
    }

    @SubscribeEvent
    public static void registerTooltipFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(PotentialBarTooltipComponent.class, ClientPotentialBarTooltipComponent::new);
    }
}
