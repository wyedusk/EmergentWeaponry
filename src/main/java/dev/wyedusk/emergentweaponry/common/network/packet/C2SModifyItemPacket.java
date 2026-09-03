package dev.wyedusk.emergentweaponry.common.network.packet;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.menu.ModificationTableMenu;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
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

public record C2SModifyItemPacket(ResourceLocation itemId, boolean isImprovementModification) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2SModifyItemPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID,
            "modify_item_c2s"));
    public static final StreamCodec<ByteBuf, C2SModifyItemPacket> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, C2SModifyItemPacket::itemId,
            ByteBufCodecs.BOOL, C2SModifyItemPacket::isImprovementModification,
            C2SModifyItemPacket::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(C2SModifyItemPacket packet, IPayloadContext context) {
        Player player = context.player();
        AbstractContainerMenu abstractMenu = player.containerMenu;
        if (!(abstractMenu instanceof ModificationTableMenu menu)) return;
        if (!BuiltInRegistries.ITEM.containsKey(packet.itemId)) return;

        ItemStack recipeItem = menu.temporaryInventory.getItem(0);
        List<ItemStack> availableCrafts = EvolutionUtil.getAvailableEvolutionItems(player.registryAccess(), recipeItem);

        boolean doCraft = false;
        ItemStack outputItem = null;

        for (ItemStack stack : availableCrafts) {
            if (stack.is(recipeItem.getItem()) && packet.isImprovementModification) {
                doCraft = true;
                outputItem = stack;
                break;
            }
            if (stack.is(BuiltInRegistries.ITEM.get(packet.itemId))) {
                doCraft = true;
                outputItem = stack;
                break;
            }
        }

        if (doCraft) {
            menu.temporaryInventory.setItem(0, ItemStack.EMPTY);
            menu.setCarried(outputItem);
        }
    }
}
