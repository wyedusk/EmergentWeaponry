package dev.wyedusk.emergentweaponry.client.gui.screen;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.menu.ModificationTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ModificationTableMenuScreen extends AbstractContainerScreen<ModificationTableMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "textures/gui/container/modification_table.png");
    private static final ResourceLocation SELECTION_BOX_SPRITE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "container/selection_box");

    public ModificationTableMenuScreen(ModificationTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 191;
        this.inventoryLabelY = 94;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        renderTransparentBackground(graphics);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // temp
        if (this.menu.temporaryInventory.getItem(0).isEmpty()) {
            menu.availableModifications = new ItemStack[]{};
        } else {
            menu.availableModifications = new ItemStack[]{
                    this.menu.temporaryInventory.getItem(0),
                    Items.COD.asItem().getDefaultInstance(),
            };
        }
        // end of temp

        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.blitSprite(SELECTION_BOX_SPRITE, this.leftPos + 40, this.topPos + 25 + (19 * menu.highlightedResultSlot), 22, 22);

        if (menu.availableModifications.length > 0) {
            int topSlot = Math.toIntExact(Math.floorMod((long) menu.currentListIndex - 1, (long) menu.availableModifications.length));
            int middleSlot = Math.clamp(menu.currentListIndex, 0, menu.availableModifications.length);
            int bottomSlot = Math.toIntExact(Math.floorMod((long) menu.currentListIndex + 1, (long) menu.availableModifications.length));

            graphics.renderItem(menu.availableModifications[topSlot], this.leftPos + 43, this.topPos + 28);
            graphics.renderItem(menu.availableModifications[middleSlot], this.leftPos + 43, this.topPos + 47);
            graphics.renderItem(menu.availableModifications[bottomSlot], this.leftPos + 43, this.topPos + 66);
        }

        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        // If in the vertical result select area, scroll the result slot
        if (mouseX > this.leftPos + 37 && mouseX < this.leftPos + 63 && mouseY > this.topPos + 16 && mouseY < this.topPos + 93) {
            if (menu.highlightedResultSlot == 0 && scrollY > 0) menu.currentListIndex -= 1;
            if (menu.highlightedResultSlot == 2 && scrollY < 0) menu.currentListIndex += 1;
            menu.highlightedResultSlot = Math.clamp(menu.highlightedResultSlot - (scrollY > 0 ? 1 : -1), 0, 2);
        }
        if (menu.currentListIndex < 0) menu.currentListIndex = menu.availableModifications.length - 1;
        if (menu.currentListIndex >= menu.availableModifications.length) menu.currentListIndex = 0;

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}