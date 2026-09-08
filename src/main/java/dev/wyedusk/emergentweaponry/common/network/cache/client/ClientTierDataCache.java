package dev.wyedusk.emergentweaponry.common.network.cache.client;

import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map.TierData;
import dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map.TierDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientTierDataCache {
    private static Map<ResourceLocation, TierData> tierData = Collections.emptyMap();

    public static void updateCache(TierDataHolder data) {
        tierData = data.values();
    }

    public static Map<ResourceLocation, TierData> getCache() {
        return tierData;
    }
}
