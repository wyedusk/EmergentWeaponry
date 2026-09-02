package dev.wyedusk.emergentweaponry.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Config Specs
    public static final ModConfigSpec.IntValue MAX_IMPROVEMENT_TIER;

    public static final ModConfigSpec SPEC;

    static {
        // Config Specs
        MAX_IMPROVEMENT_TIER = BUILDER.defineInRange("maxImprovementTier", 3, 0, 255);

        SPEC = BUILDER.build();
    }
}
