package dev.wyedusk.emergentweaponry.common.network.packet;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.TierDataHolder;
import dev.wyedusk.emergentweaponry.common.network.cache.client.ClientTierDataCache;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record S2CTierDataPacket(TierDataHolder dataHolder) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CTierDataPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID,
            "tier_data_s2c"));
    public static final StreamCodec<ByteBuf, S2CTierDataPacket> STREAM_CODEC = StreamCodec.composite(
            TierDataHolder.STREAM_CODEC, S2CTierDataPacket::dataHolder,
            S2CTierDataPacket::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(S2CTierDataPacket packet, IPayloadContext context) {
        if (!context.flow().isClientbound()) return;
        context.enqueueWork(() -> ClientTierDataCache.updateCache(packet.dataHolder));
    }
}
