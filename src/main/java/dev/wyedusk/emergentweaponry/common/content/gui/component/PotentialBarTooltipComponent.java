package dev.wyedusk.emergentweaponry.common.content.gui.component;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record PotentialBarTooltipComponent(int potential, int maxPotential, int improvementTier) implements TooltipComponent {}
