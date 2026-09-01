package dev.wyedusk.emergentweaponry.common.config;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = EmergentWeaponry.MODID)
public class CommonConfig implements IModBusEvent {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Config Specs
    private static final ModConfigSpec.ConfigValue<String> SAMPLE_VALUE;

    // Config Values
    public static String sampleValue;

    public static final ModConfigSpec SPEC;

    static {
        // Config Specs
        SAMPLE_VALUE = BUILDER.define("sample_value", "");

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Config Values
        sampleValue = SAMPLE_VALUE.get();
    }
}