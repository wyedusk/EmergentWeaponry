package dev.wyedusk.emergentweaponry.common.network;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.network.packet.C2SModifyItemPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class NetworkHandler {
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
                .playToServer(
                        C2SModifyItemPacket.TYPE,
                        C2SModifyItemPacket.STREAM_CODEC,
                        C2SModifyItemPacket::handle
                );
    }
}
