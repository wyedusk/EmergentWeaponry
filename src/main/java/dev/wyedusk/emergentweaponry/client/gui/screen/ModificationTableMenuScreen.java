package dev.wyedusk.emergentweaponry.client.gui.screen;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.menu.ModificationTableMenu;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
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
        if (menu.getBlockEntity().getLevel() != null)
            menu.availableModifications = EvolutionUtil.getAvailableEvolutionItems(
                    menu.getBlockEntity().getLevel().registryAccess(),
                    menu.temporaryInventory.getItem(0)).toArray(new ItemStack[0]
            );
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.blitSprite(SELECTION_BOX_SPRITE, this.leftPos + 40, this.topPos + 25 + (19 * menu.highlightedResultSlot), 22, 22);

        if (menu.availableModifications.length > 0) {
            int topSlot = Math.toIntExact(Math.floorMod((long) menu.currentListIndex - 1, (long) menu.availableModifications.length));
            int middleSlot = Math.clamp(menu.currentListIndex, 0, menu.availableModifications.length);
            int bottomSlot = Math.toIntExact(Math.floorMod((long) menu.currentListIndex + 1, (long) menu.availableModifications.length));

            graphics.renderItem(menu.availableModifications[topSlot], this.leftPos + 43, this.topPos + 28);
            graphics.renderItem(menu.availableModifications[middleSlot], this.leftPos + 43, this.topPos + 47);
            graphics.renderItem(menu.availableModifications[bottomSlot], this.leftPos + 43, this.topPos + 66);

            // Manually render tooltips because the items aren't real
            if (inArea(mouseX, mouseY, 43, 28, 58, 43)) {
                ItemStack slot = menu.availableModifications[topSlot];
                graphics.renderTooltip(this.font, getTooltipFromContainerItem(slot), slot.getTooltipImage(), slot, mouseX, mouseY);
            } else if (inArea(mouseX, mouseY, 43, 47, 58, 62)) {
                ItemStack slot = menu.availableModifications[middleSlot];
                graphics.renderTooltip(this.font, getTooltipFromContainerItem(slot), slot.getTooltipImage(), slot, mouseX, mouseY);
            } else if (inArea(mouseX, mouseY, 43, 66, 58, 81)) {
                ItemStack slot = menu.availableModifications[bottomSlot];
                graphics.renderTooltip(this.font, getTooltipFromContainerItem(slot), slot.getTooltipImage(), slot, mouseX, mouseY);
            }
        }

        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        // If in the vertical result select area, scroll the result slot
        if (inArea(mouseX, mouseY, 39, 17, 62, 92)) {
            if (menu.highlightedResultSlot == 0 && scrollY > 0) menu.currentListIndex -= 1;
            if (menu.highlightedResultSlot == 2 && scrollY < 0) menu.currentListIndex += 1;
            menu.highlightedResultSlot = Math.clamp(menu.highlightedResultSlot - (scrollY > 0 ? 1 : -1), 0, 2);
        }
        if (menu.currentListIndex < 0) menu.currentListIndex = menu.availableModifications.length - 1;
        if (menu.currentListIndex >= menu.availableModifications.length) menu.currentListIndex = 0;

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private boolean inArea(double mouseX, double mouseY, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        return (mouseX >= this.leftPos + topLeftX && mouseX <= this.leftPos + bottomRightX && mouseY >= this.topPos + topLeftY && mouseY <= this.topPos + bottomRightY);
    }
}