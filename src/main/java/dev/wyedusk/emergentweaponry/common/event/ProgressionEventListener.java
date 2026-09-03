package dev.wyedusk.emergentweaponry.common.event;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ProgressionUtil;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class ProgressionEventListener {

    // onLivingDamage : Damage Dealt, Damage Taken
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        DamageSource source = event.getSource();
        if (source.is(Tags.DamageTypes.IS_ENVIRONMENT) || source.is(DamageTypeTags.IS_FALL)) return;

        // Attacker-side progression (damage dealt)
        ItemStack damagingItem = source.getWeaponItem();
        if (damagingItem != null) {
            if (ProgressionUtil.canTrackDamageDealt(damagingItem)) {
                ProgressionUtil.setDamageDealt(damagingItem, (int) Math.ceil(ProgressionUtil.getDamageDealt(damagingItem) + event.getNewDamage()));
                ProgressionUtil.progressCheck(damagingItem);
            }
        }
        // Defender-side progression (damage taken)
        LivingEntity entity = event.getEntity();
        ItemStack mainHandItem = entity.getMainHandItem();
        ItemStack offhandItem = entity.getOffhandItem();
        if (mainHandItem.is(Tags.Items.TOOLS_SHIELD) && ProgressionUtil.canTrackDamageTaken(mainHandItem)) {
            ProgressionUtil.setDamageTaken(mainHandItem, (int) Math.ceil(ProgressionUtil.getDamageTaken(mainHandItem) + event.getBlockedDamage()));
            ProgressionUtil.progressCheck(mainHandItem);
        }
        if (offhandItem.is(Tags.Items.TOOLS_SHIELD) && ProgressionUtil.canTrackDamageTaken(offhandItem)) {
            ProgressionUtil.setDamageTaken(offhandItem, (int) Math.ceil(ProgressionUtil.getDamageTaken(offhandItem) + event.getBlockedDamage()));
            ProgressionUtil.progressCheck(offhandItem);
        }
        entity.getArmorSlots().forEach(stack -> {
            if (ProgressionUtil.canTrackDamageTaken(stack)) {
                ProgressionUtil.setDamageTaken(stack, (int) Math.ceil(ProgressionUtil.getDamageTaken(stack) + (event.getOriginalDamage() - event.getNewDamage())));
                ProgressionUtil.progressCheck(stack);
            }
        });
    }

    // onLivingDeath: Entities Killed
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        ItemStack killingItem = event.getSource().getWeaponItem();
        if (killingItem == null) return;
        if (EvolutionUtil.isEvolvable(killingItem)) {
            ProgressionUtil.setEntitiesKilled(killingItem, ProgressionUtil.getEntitiesKilled(killingItem) + 1);
            ProgressionUtil.progressCheck(killingItem);
        }
    }

    // onBlockBreak : Blocks Broken
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        ItemStack breakingItem = event.getPlayer().getMainHandItem();
        BlockState brokenBlock = event.getState();
        if (breakingItem.isEmpty()) return;
        if (EvolutionUtil.isEvolvable(breakingItem)) {
            if (!breakingItem.isCorrectToolForDrops(brokenBlock)) return;
            int score = 1;
            if (brokenBlock.is(Tags.Blocks.ORES)) {
                score = 2;
                Tier tier = null;
                if (breakingItem.getItem() instanceof PickaxeItem pickaxeItem) tier = pickaxeItem.getTier();
                if (breakingItem.getItem() instanceof AxeItem axeItem) tier = axeItem.getTier();
                if (breakingItem.getItem() instanceof ShovelItem shovelItem) tier = shovelItem.getTier();
                if (breakingItem.getItem() instanceof HoeItem hoeItem) tier = hoeItem.getTier();
                if (tier != null) {
                    if (isExactTierMatch(tier, brokenBlock)) score = 5;
                }
            }
            ProgressionUtil.setBlocksBroken(breakingItem, ProgressionUtil.getBlocksBroken(breakingItem) + score);
        }
    }

    private static boolean isExactTierMatch(Tier toolTier, BlockState state) {
        switch (toolTier) {
            case Tiers.IRON -> {
                return !state.is(Tiers.STONE.getIncorrectBlocksForDrops());
            }
            case Tiers.DIAMOND -> {
                return !state.is(Tiers.IRON.getIncorrectBlocksForDrops());
            }
            case Tiers.NETHERITE -> {
                return !state.is(Tiers.DIAMOND.getIncorrectBlocksForDrops());
            }
            default -> {
                return true;
            }
        }
    }
}
