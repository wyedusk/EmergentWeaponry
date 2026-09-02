package dev.wyedusk.emergentweaponry.common.content.block.entity;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.menu.ModificationTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModificationTableBlockEntity extends BlockEntity implements MenuProvider {
    public static final Component CONTAINER_NAME = Component.translatable("container." + EmergentWeaponry.MODID + ".modification_table");

    public ModificationTableBlockEntity(BlockPos pos, BlockState state) {
        super(Contents.BlockEntities.MODIFICATION_TABLE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return CONTAINER_NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        return new ModificationTableMenu(containerId, inventory, new SimpleContainer(4), this);
    }
}
