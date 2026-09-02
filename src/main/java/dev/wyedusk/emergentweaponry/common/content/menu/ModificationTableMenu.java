package dev.wyedusk.emergentweaponry.common.content.menu;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.block.entity.ModificationTableBlockEntity;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class ModificationTableMenu extends AbstractContainerMenu {
    private final ModificationTableBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    public final SimpleContainer temporaryInventory;

    public int highlightedResultSlot = 1;
    public int currentListIndex = 0;
    public ItemStack[] availableModifications = new ItemStack[]{};

    // Client-side Constructor
    public ModificationTableMenu(int containerId, Inventory playerInv, FriendlyByteBuf additionalData) {
        this(containerId, playerInv, new SimpleContainer(4), playerInv.player.level().getBlockEntity(additionalData.readBlockPos()));
    }

    // Server-side Constructor
    public ModificationTableMenu(int containerId, Inventory playerInv, SimpleContainer temporaryInventory, BlockEntity blockEntity) {
        super(Contents.Menus.MODIFICATION_TABLE_MENU.get(), containerId);
        if (blockEntity instanceof ModificationTableBlockEntity modificationTableBlockEntity) {
            this.blockEntity = modificationTableBlockEntity;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into ModificationTableMenu!".formatted(blockEntity.getClass().getCanonicalName()));
        }

        assert blockEntity.getLevel() != null;
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.temporaryInventory = temporaryInventory;

        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
        createBlockEntityInventory(modificationTableBlockEntity);
    }

    private void createBlockEntityInventory(ModificationTableBlockEntity modificationTableBlockEntity) {
        // Input Slot
        addSlot(new Slot(this.temporaryInventory, 0, 8, 47){
            @Override public boolean mayPlace(@NotNull ItemStack stack) {return EvolutionUtil.isEvolvable(stack);}
            @Override public void setChanged() {
                super.setChanged();
                ModificationTableMenu.this.updateAvailableModifications(this.getItem());
            }
        });

        // Output Slots
        addSlot(new Slot(this.temporaryInventory, 1, 43, 28){
            @Override public boolean mayPlace(@NotNull ItemStack stack) {return false;}
        });
        addSlot(new Slot(this.temporaryInventory, 1, 43, 47){
            @Override public boolean mayPlace(@NotNull ItemStack stack) {return false;}
        });
        addSlot(new Slot(this.temporaryInventory, 1, 43, 66){
            @Override public boolean mayPlace(@NotNull ItemStack stack) {return false;}
        });
    }

    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv, 9 + column + (row * 9), 8 + (column * 18), 109 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv, column, 8 + (column * 18), 167));
        }
    }

    private void updateAvailableModifications(ItemStack originalItem) {
        if (originalItem.isEmpty()) {
            availableModifications = new ItemStack[]{};
            return;
        }

        availableModifications = new ItemStack[]{
                this.temporaryInventory.getItem(0),
                Items.COD.asItem().getDefaultInstance(),
                Items.FISHING_ROD.asItem().getDefaultInstance()
        };
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot fromSlot = getSlot(index);
        ItemStack fromStack = fromSlot.getItem();

        if (fromStack.getCount() <= 0) fromSlot.set(ItemStack.EMPTY);
        if (!fromSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if (index < 36) {
            if (!moveItemStackTo(fromStack, 36, 42, false)) return ItemStack.EMPTY;
        } else if (index < 42) {
            if (!moveItemStackTo(fromStack, 0, 36, false)) return ItemStack.EMPTY;
        } else {
            EmergentWeaponry.LOGGER.error("Received invalid slot index {} in ModificationTableMenu", index);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(player, fromStack);

        return copyFromStack;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);

        if (!player.level().isClientSide) {
            this.clearContainer(player, this.temporaryInventory);
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.levelAccess, player, Contents.Blocks.MODIFICATION_TABLE.get());
    }

    public ModificationTableBlockEntity getBlockEntity() {
        return blockEntity;
    }
}