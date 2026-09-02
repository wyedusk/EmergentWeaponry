package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.config.ServerConfig;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

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
                Math.clamp(potential, 0, data.maxPotential()), data.maxPotential(), data.improvementTier()));
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
     * Get the tier data of an item.
     *
     * @param stack The ItemStack to get the tier data of.
     * @return A TierData object based on the stack item, or null if none exists.
     */
    public static TierData getTierData(ItemStack stack) {
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return null;
        Registry<TierDataHolder> registry = server.registryAccess().registry(Contents.DatapackRegistries.EVOLUTION).orElse(null);
        if (registry == null) return null;
        ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(stack.getItem());
        final TierData[] tierData = {null};

        registry.forEach(registryValues -> registryValues.values().forEach((resLoc, ttierData) -> {
            final int maxPotential = ttierData.startingMaxPotential();
            if (ttierData.members().contains(itemKey)) {
                tierData[0] = ttierData;
            }
        }));
        return tierData[0];
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

    public static List<ItemStack> getAvailableEvolutionItems(RegistryAccess registryAccess, ItemStack stack) {
        ArrayList<ItemStack> availableItems = new ArrayList<>();
        if (!canEvolve(stack)) return List.of();

        if (EvolutionUtil.getImprovementTier(stack) < ServerConfig.MAX_IMPROVEMENT_TIER.getAsInt()) {
            ItemStack improvedStack = stack.copy();
            // Modify evolution data
            improvedStack.set(Contents.DataComponents.EVOLUTION_DATA, new ItemEvolutionData(
                    0,
                    (int) (Math.round((EvolutionUtil.getMaxPotential(stack) * 1.25) / 5) * 5),
                    EvolutionUtil.getImprovementTier(stack) + 1));
            // Reset progression data
            improvedStack.set(Contents.DataComponents.PROGRESSION_DATA, new ProgressionData(0, 0, 0, 0));
            improvedStack.set(Contents.DataComponents.PROGRESSION_LOOP_DATA, new ProgressionLoopData(0, 0, 0, 0));

            availableItems.add(improvedStack);
        }

        Holder<Item> itemHolder = stack.getItemHolder();
        TransformEvolutionData transformData = itemHolder.getData(Contents.DataMaps.TRANSFORM_EVOLUTION_DATA_MAP);

        if (transformData != null) {
            transformData.evolutionList().forEach(evoInstance -> {
                Item evolvedStackItem = BuiltInRegistries.ITEM.get(evoInstance.evolvesInto());

                Registry<TierDataHolder> registry = registryAccess.registry(Contents.DatapackRegistries.EVOLUTION).orElse(null);
                if (registry == null) return;

                final ItemEvolutionData[] itemEvoData = {new ItemEvolutionData(0, 0, 0)};
                registry.forEach(registryValues -> registryValues.values().forEach((resLoc, tierData) -> {
                    final int maxPotential = tierData.startingMaxPotential();
                    if (tierData.members().contains(evoInstance.evolvesInto())) {
                        itemEvoData[0] = new ItemEvolutionData(0, maxPotential, 0);
                    }
                }));

                ItemStack evolvedStack = new ItemStack(evolvedStackItem);
                evolvedStack.applyComponents(stack.getComponents());
                evolvedStack.set(Contents.DataComponents.EVOLUTION_DATA, itemEvoData[0]);
                evolvedStack.set(Contents.DataComponents.PROGRESSION_DATA, new ProgressionData(0, 0, 0, 0));
                evolvedStack.set(Contents.DataComponents.PROGRESSION_LOOP_DATA, new ProgressionLoopData(0, 0, 0, 0));

                availableItems.add(evolvedStack);
            });
        }

        return availableItems.stream().toList();
    }
}
