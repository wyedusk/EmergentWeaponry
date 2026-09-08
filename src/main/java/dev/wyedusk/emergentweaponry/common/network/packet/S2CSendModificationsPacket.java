package dev.wyedusk.emergentweaponry.common.network.packet;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.gui.menu.ModificationTableMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record S2CSendModificationsPacket(List<ItemStack> items) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CSendModificationsPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID,
            "send_evolutions_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSendModificationsPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), S2CSendModificationsPacket::items,
            S2CSendModificationsPacket::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(S2CSendModificationsPacket packet, IPayloadContext context) {
        if (!context.flow().isClientbound()) return;
        Player player = context.player();
        AbstractContainerMenu abstractMenu = player.containerMenu;
        if (!(abstractMenu instanceof ModificationTableMenu menu)) return;
        menu.availableModifications = packet.items.toArray(new ItemStack[0]);
    }
}
