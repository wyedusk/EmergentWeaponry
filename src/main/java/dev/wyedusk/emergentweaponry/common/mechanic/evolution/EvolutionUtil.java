package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.world.item.ItemStack;

/**
 * Utility class for handling evolution items. An evolvable item is defined by having the EVOLUTION_DATA
 * component.
 */
public class EvolutionUtil {
    /**
     * Check whether to not an item is classed as an evolvable item.
     *
     * @param stack The ItemStack to be checked for evolution capabilities.
     * @return A boolean indicating whether the item is an evolvable item.
     */
    public static boolean isEvolvable(ItemStack stack) {
        return stack.has(Contents.DataComponents.EVOLUTION_DATA);
    }

    /**
     * Get the Potential value for an item.
     *
     * @param stack The ItemStack to get the Potential for.
     * @return An integer value of how much Potential the item has, or -1 if the item is not an evolvable item.
     */
    public static int getPotential(ItemStack stack) {
        ItemEvolutionData data = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        return data == null ? -1 : data.potential();
    }

    /**
     * Set the Potential value for an item.
     *
     * @param stack The ItemStack to set the Potential for.
     * @param potential An integer value of how much Potential the item should have.
     */
    public static void setPotential(ItemStack stack, int potential) {
        ItemEvolutionData data = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Potential of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.EVOLUTION_DATA, new ItemEvolutionData(
                potential, data.maxPotential(), data.improvementTier()));
    }

    /**
     * Get the Max Potential value for an item.
     *
     * @param stack The ItemStack to get the Max Potential for.
     * @return An integer value of how much Max Potential the item has, or -1 if the item is not an evolvable item.
     */
    public static int getMaxPotential(ItemStack stack) {
        ItemEvolutionData data = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        return data == null ? -1 : data.maxPotential();
    }
    /**
     * Set the Max Potential value for an item.
     *
     * @param stack The ItemStack to set the Max Potential for.
     * @param maxPotential An integer value of how much Max Potential the item should have.
     */
    public static void setMaxPotential(ItemStack stack, int maxPotential) {
        ItemEvolutionData data = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Max Potential of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.EVOLUTION_DATA, new ItemEvolutionData(
                data.potential(), maxPotential, data.improvementTier()));
    }

    /**
     * Get the Improvement Tier of an item.
     *
     * @param stack The ItemStack to get the Improvement Tier for.
     * @return An integer value of what Improvement Tier the item is, or -1 if the item is not an evolvable item.
     */
    public static int getImprovementTier(ItemStack stack) {
        ItemEvolutionData data = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        return data == null ? -1 : data.improvementTier();
    }
    /**
     * Se the Improvement Tier of an item.
     *
     * @param stack The ItemStack to set the Improvement Tier for.
     * @param improvementTier An integer value of what Improvement Tier the item should be at
     */
    public static void setImprovementTier(ItemStack stack, int improvementTier) {
        ItemEvolutionData data = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Improvement Tier of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.EVOLUTION_DATA, new ItemEvolutionData(
                data.potential(), data.maxPotential(), improvementTier));
    }

    /**
     * Check whether an item has enough Potential to evolve. Does NOT check if it has an available transformation
     * evolution.
     *
     * @param stack The ItemStack to check if it can evolve
     * @return A boolean indicating whether the item has enough Potential to evolve. Always returns false for
     *         non-evolvable items.
     */
    public static boolean canEvolve(ItemStack stack) {
        ItemEvolutionData data = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        return data != null && data.potential() >= data.maxPotential();
    }
}
