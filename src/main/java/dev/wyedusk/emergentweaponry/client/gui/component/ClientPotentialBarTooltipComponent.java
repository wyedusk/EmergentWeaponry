package dev.wyedusk.emergentweaponry.client.gui.component;

import dev.wyedusk.emergentweaponry.common.gui.component.PotentialBarTooltipComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import org.jetbrains.annotations.NotNull;

public class ClientPotentialBarTooltipComponent implements ClientTooltipComponent {
    private final int potential;
    private final int maxPotential;
    private final float percentage;

    private static final int COLOUR = 0xFFD4BFFF;
    private static final int SHADOW_COLOUR = 0xFF352F3F; // 25% for shadow

    private static final int BAR_WIDTH = 72;
    private static final int BAR_HEIGHT = 8;
    private static final int TEXT_PADDING = 4;

    public ClientPotentialBarTooltipComponent(PotentialBarTooltipComponent data) {
        this.potential = data.potential();
        this.maxPotential = data.maxPotential();
        this.percentage = Math.clamp((float) this.potential / this.maxPotential, 0.0f, 1.0f);
    }

    @Override
    public int getHeight() {
        return BAR_HEIGHT + 2;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        int width = BAR_WIDTH;
        width += font.width(String.valueOf(potential)) + TEXT_PADDING;
        width += font.width(String.valueOf(maxPotential)) + TEXT_PADDING;
        return width;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, GuiGraphics graphics) {
        int currentX = x;
        // Potential text
        graphics.drawString(font, String.valueOf(potential), x, y, COLOUR, true);
        currentX += font.width(String.valueOf(potential)) + TEXT_PADDING;

        // Potential bar
        int endX = currentX + BAR_WIDTH;
        int endY = y + BAR_HEIGHT;
        graphics.fill(currentX + 1, y + 1, endX, endY, SHADOW_COLOUR);
        int filledWidth = (int) (BAR_WIDTH * this.percentage);
        int filledEndX = currentX + filledWidth;
        if (filledWidth > 0) graphics.fill(currentX, y, filledEndX - 1, endY - 1, COLOUR);
        currentX += BAR_WIDTH + TEXT_PADDING;

        // Max Potential text
        graphics.drawString(font, String.valueOf(maxPotential), currentX, y, COLOUR, true);
    }
}
