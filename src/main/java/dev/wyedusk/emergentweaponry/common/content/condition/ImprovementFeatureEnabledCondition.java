package dev.wyedusk.emergentweaponry.common.content.condition;

import com.mojang.serialization.MapCodec;
import dev.wyedusk.emergentweaponry.common.config.ServerConfig;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class ImprovementFeatureEnabledCondition implements ICondition {
    public static final MapCodec<ImprovementFeatureEnabledCondition> CODEC = MapCodec.unit(new ImprovementFeatureEnabledCondition());

    @Override
    public boolean test(@NotNull IContext context) {
        return ServerConfig.ALLOW_IMPROVEMENT_FEATURE.getAsBoolean();
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}