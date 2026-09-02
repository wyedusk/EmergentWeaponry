package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;

/**
 * Utility class for handling the progression of evolution items.
 */
public class ProgressionUtil {
    /**
     * Checks if an item has all required progression data.
     *
     * @param stack The ItemStack to check.
     * @return A boolean indicating whether the item has all required progression data.
     */
    public static boolean hasProgressionData(ItemStack stack) {
        return stack.has(Contents.DataComponents.PROGRESSION_DATA) && stack.has(Contents.DataComponents.PROGRESSION_LOOP_DATA);
    }

    /**
     * Get the Damage Dealt statistic of an item.
     *
     * @param stack The ItemStack to get the Damage Dealt statistic of.
     * @return An integer value of the item's Damage Dealt statistic, or -1 if the item is not an evolvable item.
     */
    public static int getDamageDealt(ItemStack stack) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        return data == null ? -1 : data.damageDealt();
    }
    /**
     * Set the Damage Dealt statistic of an item.
     *
     * @param stack The ItemStack to set the Damage Dealt statistic of.
     * @param damageDealt An integer value of what the item's Damage Dealt statistic should be
     */
    public static void setDamageDealt(ItemStack stack, int damageDealt) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Damage Dealt progression statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_DATA, new ProgressionData(
                damageDealt, data.entitiesKilled(), data.blocksBroken(), data.damageTaken()));
    }

    /**
     * Get the Entities Killed statistic of an item.
     *
     * @param stack The ItemStack to get the Entities Killed statistic of.
     * @return An integer value of the item's Entities Killed statistic, or -1 if the item is not an evolvable item.
     */
    public static int getEntitiesKilled(ItemStack stack) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        return data == null ? -1 : data.entitiesKilled();
    }
    /**
     * Set the Entities Killed statistic of an item.
     *
     * @param stack The ItemStack to set the Entities Killed statistic of.
     * @param entitiesKilled An integer value of what the item's Entities Killed statistic should be
     */
    public static void setEntitiesKilled(ItemStack stack, int entitiesKilled) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Entities Killed progression statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_DATA, new ProgressionData(
                data.damageDealt(), entitiesKilled, data.blocksBroken(), data.damageTaken()));
    }

    /**
     * Get the Blocks Broken statistic of an item.
     *
     * @param stack The ItemStack to get the Blocks Broken statistic of.
     * @return An integer value of the item's Blocks Broken statistic, or -1 if the item is not an evolvable item.
     */
    public static int getBlocksBroken(ItemStack stack) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        return data == null ? -1 : data.blocksBroken();
    }
    /**
     * Set the Blocks Broken statistic of an item.
     *
     * @param stack The ItemStack to set the Blocks Broken statistic of.
     * @param blocksBroken An integer value of what the item's Blocks Broken statistic should be
     */
    public static void setBlocksBroken(ItemStack stack, int blocksBroken) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Blocks Broken progression statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_DATA, new ProgressionData(
                data.damageDealt(), data.entitiesKilled(), blocksBroken, data.damageTaken()));
    }

    /**
     * Get the Damage Taken statistic of an item.
     *
     * @param stack The ItemStack to get the Damage Taken statistic of.
     * @return An integer value of the item's Damage Taken statistic, or -1 if the item is not an evolvable item.
     */
    public static int getDamageTaken(ItemStack stack) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        return data == null ? -1 : data.damageTaken();
    }
    /**
     * Set the Damage Taken statistic of an item.
     *
     * @param stack The ItemStack to set the Damage Taken statistic of.
     * @param damageTaken An integer value of what the item's Damage Taken statistic should be
     */
    public static void setDamageTaken(ItemStack stack, int damageTaken) {
        ProgressionData data = stack.get(Contents.DataComponents.PROGRESSION_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Damage Taken progression statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_DATA, new ProgressionData(
                data.damageDealt(), data.entitiesKilled(), data.blocksBroken(), damageTaken));
    }

    /**
     * Get the Damage Dealt loop statistic of an item.
     *
     * @param stack The ItemStack to get the Damage Dealt loop statistic of.
     * @return An integer value of the item's Damage Dealt loop statistic, or -1 if the item is not an evolvable item.
     */
    public static int getDamageDealtLoop(ItemStack stack) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        return data == null ? -1 : data.damageDealt();
    }
    /**
     * Set the Damage Dealt loop statistic of an item.
     *
     * @param stack The ItemStack to set the Damage Dealt loop statistic of.
     * @param damageDealt An integer value of what the item's Damage Dealt loop statistic should be
     */
    public static void setDamageDealtLoop(ItemStack stack, int damageDealt) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Damage Dealt progression loop statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_LOOP_DATA, new ProgressionLoopData(
                damageDealt, data.entitiesKilled(), data.blocksBroken(), data.damageTaken()));
    }

    /**
     * Get the Entities Killed loop statistic of an item.
     *
     * @param stack The ItemStack to get the Entities Killed loop statistic of.
     * @return An integer value of the item's Entities Killed loop statistic, or -1 if the item is not an evolvable item.
     */
    public static int getEntitiesKilledLoop(ItemStack stack) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        return data == null ? -1 : data.entitiesKilled();
    }
    /**
     * Set the Entities Killed loop statistic of an item.
     *
     * @param stack The ItemStack to set the Entities Killed loop statistic of.
     * @param entitiesKilled An integer value of what the item's Entities Killed loop statistic should be
     */
    public static void setEntitiesKilledLoop(ItemStack stack, int entitiesKilled) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Entities Killed progression loop statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_LOOP_DATA, new ProgressionLoopData(
                data.damageDealt(), entitiesKilled, data.blocksBroken(), data.damageTaken()));
    }

    /**
     * Get the Blocks Broken loop statistic of an item.
     *
     * @param stack The ItemStack to get the Blocks Broken loop statistic of.
     * @return An integer value of the item's Blocks Broken loop statistic, or -1 if the item is not an evolvable item.
     */
    public static int getBlocksBrokenLoop(ItemStack stack) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        return data == null ? -1 : data.blocksBroken();
    }
    /**
     * Set the Blocks Broken loop statistic of an item.
     *
     * @param stack The ItemStack to set the Blocks Broken loop statistic of.
     * @param blocksBroken An integer value of what the item's Blocks Broken loop statistic should be
     */
    public static void setBlocksBrokenLoop(ItemStack stack, int blocksBroken) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Blocks Broken progression loop statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_LOOP_DATA, new ProgressionLoopData(
                data.damageDealt(), data.entitiesKilled(), blocksBroken, data.damageTaken()));
    }

    /**
     * Get the Damage Taken loop statistic of an item.
     *
     * @param stack The ItemStack to get the Damage Taken loop statistic of.
     * @return An integer value of the item's Damage Taken loop statistic, or -1 if the item is not an evolvable item.
     */
    public static int getDamageTakenLoop(ItemStack stack) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        return data == null ? -1 : data.damageTaken();
    }
    /**
     * Set the Damage Taken loop statistic of an item.
     *
     * @param stack The ItemStack to set the Damage Taken loop statistic of.
     * @param damageTaken An integer value of what the item's Damage Taken loop statistic should be
     */
    public static void setDamageTakenLoop(ItemStack stack, int damageTaken) {
        ProgressionLoopData data = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (data == null) {
            EmergentWeaponry.LOGGER.error("Attempted to set the Damage Taken progression loop statistic of a non-evolvable item!");
            return;
        }
        stack.set(Contents.DataComponents.PROGRESSION_LOOP_DATA, new ProgressionLoopData(
                data.damageDealt(), data.entitiesKilled(), data.blocksBroken(), damageTaken));
    }

    /**
     * Returns the amount of Damage Dealt an item requires to be able to obtain a Potential point.
     * @param stack The ItemStack to check.
     * @return An integer value (rounded up) of the required Damage Dealt, or -1 if the item is invalid.
     */
    public static int getRequiredDamageDealt(ItemStack stack) {
        ProgressionLoopData progLoopData = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (!EvolutionUtil.isEvolvable(stack) || !ProgressionUtil.hasProgressionData(stack)) {
            EmergentWeaponry.LOGGER.error("Attempted to get the required Damage Dealt progression statistic of a non-evolvable item!");
            return -1;
        }
        TierData tierData = EvolutionUtil.getTierData(stack);
        if (tierData == null) return -1;
        assert progLoopData != null;

        int startingReq = tierData.startingDamageDealtRequirement();
        int loopCount = progLoopData.damageDealt();
        return (int) Math.ceil(startingReq + ((startingReq * 0.15) * loopCount));
    }

    /**
     * Returns the amount of Entities Killed an item requires to be able to obtain a Potential point.
     * @param stack The ItemStack to check.
     * @return An integer value (rounded up) of the required Entities Killed, or -1 if the item is invalid.
     */
    public static int getRequiredEntitiesKilled(ItemStack stack) {
        ProgressionLoopData progLoopData = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (!EvolutionUtil.isEvolvable(stack) || !ProgressionUtil.hasProgressionData(stack)) {
            EmergentWeaponry.LOGGER.error("Attempted to get the required Entities Killed progression statistic of a non-evolvable item!");
            return -1;
        }
        TierData tierData = EvolutionUtil.getTierData(stack);
        if (tierData == null) return -1;
        assert progLoopData != null;

        int startingReq = tierData.startingKillRequirement();
        int loopCount = progLoopData.entitiesKilled();
        return (int) Math.ceil(startingReq + ((startingReq * 0.15) * loopCount));
    }

    /**
     * Returns the amount of Blocks Broken an item requires to be able to obtain a Potential point.
     * @param stack The ItemStack to check.
     * @return An integer value (rounded up) of the required Blocks Broken, or -1 if the item is invalid.
     */
    public static int getRequiredBlocksBroken(ItemStack stack) {
        ProgressionLoopData progLoopData = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (!EvolutionUtil.isEvolvable(stack) || !ProgressionUtil.hasProgressionData(stack)) {
            EmergentWeaponry.LOGGER.error("Attempted to get the required Blocks Broken progression statistic of a non-evolvable item!");
            return -1;
        }
        TierData tierData = EvolutionUtil.getTierData(stack);
        if (tierData == null) return -1;
        assert progLoopData != null;

        int startingReq = tierData.startingBlockBreakRequirement();
        int loopCount = progLoopData.blocksBroken();
        return (int) Math.ceil(startingReq + ((startingReq * 0.15) * loopCount));
    }

    /**
     * Returns the amount of Damage Taken an item requires to be able to obtain a Potential point.
     * @param stack The ItemStack to check.
     * @return An integer value (rounded up) of the required Damage Taken, or -1 if the item is invalid.
     */
    public static int getRequiredDamageTaken(ItemStack stack) {
        ProgressionLoopData progLoopData = stack.get(Contents.DataComponents.PROGRESSION_LOOP_DATA);
        if (!EvolutionUtil.isEvolvable(stack) || !ProgressionUtil.hasProgressionData(stack)) {
            EmergentWeaponry.LOGGER.error("Attempted to get the required Damage Taken progression statistic of a non-evolvable item!");
            return -1;
        }
        TierData tierData = EvolutionUtil.getTierData(stack);
        if (tierData == null) return -1;
        assert progLoopData != null;

        int startingReq = tierData.startingDamageTakenRequirement();
        int loopCount = progLoopData.damageTaken();
        return (int) Math.ceil(startingReq + ((startingReq * 0.15) * loopCount));
    }

    private static int[] calculateLoopAndRemainder(int amt, int req) {
        if (amt < req) return new int[]{0, amt};
        int loops = (int) Math.floor((-0.925 + Math.sqrt(0.855625 + 0.3 * (double) amt / req)) / 0.15 + 1e-9);
        long totalRequired = (long) req * loops + (long) Math.ceil(req * 0.15 * loops * (loops - 1) / 2.0);
        return new int[]{loops, amt - Math.toIntExact(totalRequired)};
    }

    public static void progressCheck(ItemStack stack) {
        int potentialToAdd = 0;

        int[] damageDealtLR = calculateLoopAndRemainder(getDamageDealt(stack), getRequiredDamageDealt(stack));
        potentialToAdd += damageDealtLR[0];
        ProgressionUtil.setDamageDealtLoop(stack, ProgressionUtil.getDamageDealtLoop(stack) + damageDealtLR[0]);
        ProgressionUtil.setDamageDealt(stack, damageDealtLR[1]);

        int[] entitiesKilledLR = calculateLoopAndRemainder(getEntitiesKilled(stack), getRequiredEntitiesKilled(stack));
        potentialToAdd += entitiesKilledLR[0];
        ProgressionUtil.setEntitiesKilledLoop(stack, ProgressionUtil.getEntitiesKilledLoop(stack) + entitiesKilledLR[0]);
        ProgressionUtil.setEntitiesKilled(stack, entitiesKilledLR[1]);

        int[] blocksBrokenLR = calculateLoopAndRemainder(getBlocksBroken(stack), getRequiredBlocksBroken(stack));
        potentialToAdd += blocksBrokenLR[0];
        ProgressionUtil.setBlocksBrokenLoop(stack, ProgressionUtil.getBlocksBrokenLoop(stack) + blocksBrokenLR[0]);
        ProgressionUtil.setBlocksBroken(stack, blocksBrokenLR[1]);

        int[] damageTakenLR = calculateLoopAndRemainder(getDamageTaken(stack), getRequiredDamageTaken(stack));
        potentialToAdd += damageTakenLR[0];
        ProgressionUtil.setDamageTakenLoop(stack, ProgressionUtil.getDamageTakenLoop(stack) + damageTakenLR[0]);
        ProgressionUtil.setDamageTaken(stack, damageTakenLR[1]);

        EvolutionUtil.setPotential(stack, EvolutionUtil.getPotential(stack) + potentialToAdd);
    }

    /**
     * Whether an item is capable of progressing through tracking its Damage Dealt.
     * @param stack The ItemStack to check.
     * @return A boolean indicating whether the item can progress through Damage Dealt.
     */
    public static boolean canTrackDamageDealt(ItemStack stack) {
        return EvolutionUtil.isEvolvable(stack) && stack.is(Tags.Items.MELEE_WEAPON_TOOLS);
    }
    /**
     * Whether an item is capable of progressing through tracking its Entities Killed.
     * @param stack The ItemStack to check.
     * @return A boolean indicating whether the item can progress through Entities Killed.
     */
    public static boolean canTrackEntitiesKilled(ItemStack stack) {
        return EvolutionUtil.isEvolvable(stack) && stack.is(Tags.Items.MELEE_WEAPON_TOOLS);
    }
    /**
     * Whether an item is capable of progressing through tracking its Blocks Broken.
     * @param stack The ItemStack to check.
     * @return A boolean indicating whether the item can progress through Blocks Broken.
     */
    public static boolean canTrackBlocksBroken(ItemStack stack) {
        return EvolutionUtil.isEvolvable(stack) && (stack.is(ItemTags.SHOVELS) || stack.is(ItemTags.AXES) || stack.is(ItemTags.PICKAXES));
    }
    /**
     * Whether an item is capable of progressing through tracking its Damage Taken.
     * @param stack The ItemStack to check.
     * @return A boolean indicating whether the item can progress through Damage Taken.
     */
    public static boolean canTrackDamageTaken(ItemStack stack) {
        return EvolutionUtil.isEvolvable(stack) && (stack.is(Tags.Items.ARMORS) || stack.is(Tags.Items.TOOLS_SHIELD));
    }
}
