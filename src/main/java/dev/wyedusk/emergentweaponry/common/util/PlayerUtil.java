package dev.wyedusk.emergentweaponry.common.util;

import net.minecraft.world.entity.player.Player;

public class PlayerUtil {
    public static int getTotalExperience(Player player) {
        int level = player.experienceLevel;
        int basePoints;

        if (level >= 31) {
            basePoints = (int) (4.5 * level * level - 162.5 * level + 2220);
        } else if (level >= 16) {
            basePoints = (int) (2.5 * level * level - 40.5 * level + 360);
        } else {
            basePoints = level * level + 6 * level;
        }

        int pointsProgress = Math.round(player.getXpNeededForNextLevel() * player.experienceProgress);

        return basePoints + pointsProgress;
    }
}
