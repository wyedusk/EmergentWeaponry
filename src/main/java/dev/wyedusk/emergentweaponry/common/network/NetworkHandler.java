package dev.wyedusk.emergentweaponry.common.network;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.network.packet.C2SModifyItemPacket;
import dev.wyedusk.emergentweaponry.common.network.packet.S2CSendModificationsPacket;
import dev.wyedusk.emergentweaponry.common.network.packet.S2CTierDataPacket;
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
                )

                .playToClient(
                        S2CTierDataPacket.TYPE,
                        S2CTierDataPacket.STREAM_CODEC,
                        S2CTierDataPacket::handle
                )
                .playToClient(
                        S2CSendModificationsPacket.TYPE,
                        S2CSendModificationsPacket.STREAM_CODEC,
                        S2CSendModificationsPacket::handle
                );
    }
}
