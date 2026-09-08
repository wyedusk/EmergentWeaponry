package dev.wyedusk.emergentweaponry.common.content.condition;

import com.mojang.serialization.MapCodec;
import dev.wyedusk.emergentweaponry.common.config.ServerConfig;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class AnyVanillaUpgradeEnabledCondition implements ICondition {
    public static final MapCodec<AnyVanillaUpgradeEnabledCondition> CODEC = MapCodec.unit(new AnyVanillaUpgradeEnabledCondition());

    @Override
    public boolean test(@NotNull IContext context) {
        return ServerConfig.ALLOW_VANILLA_TOOL_UPGRADING.getAsBoolean() || ServerConfig.ALLOW_VANILLA_ARMOR_UPGRADING.getAsBoolean();
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}