package dev.wyedusk.emergentweaponry.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Config Specs
    public static final ModConfigSpec.BooleanValue ALLOW_VANILLA_TOOL_UPGRADING;
    public static final ModConfigSpec.BooleanValue ALLOW_VANILLA_ARMOR_UPGRADING;
    public static final ModConfigSpec.BooleanValue ALLOW_IMPROVEMENT_FEATURE;

    public static final ModConfigSpec.IntValue MAX_IMPROVEMENT_TIER;

    public static final ModConfigSpec.IntValue ESSENCE_TRIDENT_LIFESTEAL_PERCENTAGE;

    public static final ModConfigSpec SPEC;

    static {
        // Config Specs
        BUILDER.push("upgrading");
        ALLOW_VANILLA_TOOL_UPGRADING = BUILDER
                .comment("Should players be allowed to upgrade Vanilla tools?")
                .define("allowVanillaToolUpgrading", true);
        ALLOW_VANILLA_ARMOR_UPGRADING = BUILDER
                .comment("Should players be allowed to upgrade Vanilla armor?")
                .define("allowVanillaArmorUpgrading", true);
        BUILDER.pop();

        BUILDER.push("improving");
        ALLOW_IMPROVEMENT_FEATURE = BUILDER
                .comment("Should players be allowed to use the Improvement feature?")
                .define("allowImprovementFeature", true);
        MAX_IMPROVEMENT_TIER = BUILDER
                .comment("What Improvement tier should items be limited to?")
                .defineInRange("maxImprovementTier", 3, 0, 10);
        BUILDER.pop();

        BUILDER.push("weapons");
        ESSENCE_TRIDENT_LIFESTEAL_PERCENTAGE = BUILDER
                .comment("What percentage of the Essence Trident's damage should be turned into life-steal?")
                .defineInRange("essenceTridentLifestealPercentage", 15, 0, 100);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
