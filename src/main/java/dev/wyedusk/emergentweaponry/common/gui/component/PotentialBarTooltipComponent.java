package dev.wyedusk.emergentweaponry.common.gui.component;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record PotentialBarTooltipComponent(int potential, int maxPotential) implements TooltipComponent {}
