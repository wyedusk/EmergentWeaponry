package dev.wyedusk.emergentweaponry.common.config;

import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig implements IModBusEvent {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Config Specs
    private static final ModConfigSpec.ConfigValue<String> SAMPLE_VALUE;

    public static final ModConfigSpec SPEC;

    static {
        // Config Specs
        SAMPLE_VALUE = BUILDER.define("sample_value", "");

        SPEC = BUILDER.build();
    }
}