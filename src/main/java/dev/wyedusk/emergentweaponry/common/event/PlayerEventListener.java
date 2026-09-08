package dev.wyedusk.emergentweaponry.common.event;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.config.ServerConfig;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.entity.ThrownEssenceTrident;
import dev.wyedusk.emergentweaponry.common.content.item.EssenceTridentItem;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map.TierDataHolder;
import dev.wyedusk.emergentweaponry.common.network.packet.S2CTierDataPacket;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class PlayerEventListener {
    @SubscribeEvent
    @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            var server = event.getEntity().getServer();
            assert server != null;
            try {
                Registry<TierDataHolder> registry = server.registryAccess().registry(Contents.DatapackRegistries.EVOLUTION).orElse(null);
                PacketDistributor.sendToPlayer(serverPlayer, new S2CTierDataPacket(registry.getAny().get().value()));
            } catch (Exception e) {
                EmergentWeaponry.LOGGER.error("Failed to send tier data to {}!", serverPlayer.getName());
                EmergentWeaponry.LOGGER.trace("Tier data send failure:", e);
            }
        }
    }

    @SubscribeEvent
    public static void beforeLivingDamage(LivingDamageEvent.Pre event) {
        DamageSource source = event.getSource();
        Entity damager = source.getDirectEntity();
        float damageAmount = event.getNewDamage();

        // Essence Trident Life-steal (damage reduction)
        if (damager instanceof ThrownEssenceTrident essTrident) {
            float lifestealPercentage = ((float) ServerConfig.ESSENCE_TRIDENT_LIFESTEAL_PERCENTAGE.getAsInt() / 100);
            float damageModifier = 1 - lifestealPercentage;
            event.setNewDamage(damageAmount * damageModifier);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        DamageSource source = event.getSource();
        Entity damager = source.getDirectEntity();
        float damageAmount = event.getNewDamage();

        // Essence Trident Life-steal (healing)
        if (damager instanceof ThrownEssenceTrident essTrident) {
            Entity owner = essTrident.getOwner();
            float lifestealPercentage = ((float) ServerConfig.ESSENCE_TRIDENT_LIFESTEAL_PERCENTAGE.getAsInt() / 100);
            float damageModifier = 1 - lifestealPercentage;
            if (owner instanceof LivingEntity livingOwner) {
                livingOwner.heal((damageAmount / damageModifier) * lifestealPercentage);
            }
        }
    }
}
