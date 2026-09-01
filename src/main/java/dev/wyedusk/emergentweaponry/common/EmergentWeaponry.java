package dev.wyedusk.emergentweaponry.common;

import com.mojang.logging.LogUtils;
import dev.wyedusk.emergentweaponry.common.config.CommonConfig;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(EmergentWeaponry.MODID)
public class EmergentWeaponry {
    public static final String MODID = "emergentweaponry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EmergentWeaponry(IEventBus modEventBus, ModContainer modContainer) {
        Contents.registerContents(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

}