package dev.wyedusk.emergentweaponry.common.config;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Config Specs
    private static final ModConfigSpec.IntValue MAX_IMPROVEMENT_TIER;

    // Config Values
    public static int maxImprovementTier;

    public static final ModConfigSpec SPEC;

    static {
        // Config Specs
        MAX_IMPROVEMENT_TIER = BUILDER.defineInRange("maxImprovementTier", 3, 0, 255);

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            // Config Values
            maxImprovementTier = MAX_IMPROVEMENT_TIER.get();
        }
    }
}
