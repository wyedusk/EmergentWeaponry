package dev.wyedusk.emergentweaponry.common.gui.component;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.awt.*;

public class StylisedComponents {
    public static Component getMaxImprovementStyleTextComponent(String plainText) {
        MutableComponent mainComponent = Component.empty();
        long time = Util.getMillis();

        int baseR = 0xD4;
        int baseG = 0xBF;
        int targetR = 0xF6;
        int targetG = 0xF2;
        int b = 0xFF;

        double wavelength = 2000;
        double threshold = 0.95;

        for (int i = 0; i < plainText.length(); i++) {
            double phase = ((time % wavelength) / wavelength * Math.PI * 2) * 2 - (i * 0.25);
            double wave = Math.sin(phase);
            double intensity = 0.0;

            if ((time % wavelength) / wavelength * 2 > 1.0) {
                wave = -1.0;
            }

            if (wave > threshold) {
                double normalized = (wave - threshold) / (1.0 - threshold);
                intensity = Math.sin(normalized * Math.PI / 2);
            }

            int r = baseR + (int) ((targetR - baseR) * intensity);
            int g = baseG + (int) ((targetG - baseG) * intensity);
            int rgb = (r << 16) | (g << 8) | b;

            mainComponent.append(Component.literal(String.valueOf(plainText.charAt(i))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(rgb))));
        }

        return mainComponent;
    }
}
