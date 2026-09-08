package dev.wyedusk.emergentweaponry.common.event;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map.TierDataHolder;
import dev.wyedusk.emergentweaponry.common.network.packet.S2CTierDataPacket;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class PlayerEventListener {
    @SubscribeEvent
    @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            var server = event.getEntity().getServer();
            assert server != null;
            try {
                Registry<TierDataHolder> registry = server.registryAccess().registry(Contents.DatapackRegistries.EVOLUTION).orElse(null);
                PacketDistributor.sendToPlayer(serverPlayer, new S2CTierDataPacket(registry.getAny().get().value()));
            } catch (Exception e) {
                EmergentWeaponry.LOGGER.error("Failed to send tier data to {}!", serverPlayer.getName());
                EmergentWeaponry.LOGGER.trace("Tier data send failure:", e);
            }
        }
    }
}
