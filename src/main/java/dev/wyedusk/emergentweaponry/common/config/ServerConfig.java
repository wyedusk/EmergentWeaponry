package dev.wyedusk.emergentweaponry.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Config Specs
    public static final ModConfigSpec.BooleanValue ALLOW_VANILLA_TOOL_UPGRADING;
    public static final ModConfigSpec.BooleanValue ALLOW_VANILLA_ARMOR_UPGRADING;
    public static final ModConfigSpec.BooleanValue ALLOW_IMPROVEMENT_FEATURE;

    public static final ModConfigSpec.IntValue MAX_IMPROVEMENT_TIER;

    public static final ModConfigSpec SPEC;

    static {
        // Config Specs
        BUILDER.push("upgrading");
        ALLOW_VANILLA_TOOL_UPGRADING = BUILDER.define("allowVanillaToolUpgrading", true);
        ALLOW_VANILLA_ARMOR_UPGRADING = BUILDER.define("allowVanillaArmorUpgrading", true);
        BUILDER.pop();

        BUILDER.push("improving");
        ALLOW_IMPROVEMENT_FEATURE = BUILDER.define("allowImprovementFeature", true);
        MAX_IMPROVEMENT_TIER = BUILDER.defineInRange("maxImprovementTier", 3, 0, 10);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
