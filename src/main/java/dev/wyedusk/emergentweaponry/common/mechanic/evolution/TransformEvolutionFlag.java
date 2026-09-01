package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * Defines all transformation evolution flags. These are used for configuration, such as VANILLA_TOOL being used for
 * transforming vanilla tools into other vanilla tools (e.g. iron -> diamond) or CUSTOM being for custom items
 * added by a mod. Only VANILLA_TOOL and VANILLA_ARMOR transformations can be disabled through configuration.
 */
public enum TransformEvolutionFlag implements StringRepresentable {
    /**
     * The VANILLA_TOOL flag indicates that an evolution is an upgrade to and from a vanilla tool, such as that of
     * upgrading an iron sword to a diamond sword.
     */
    VANILLA_TOOL,
    /**
     * The VANILLA_ARMOR flag indicates that an evolution is an upgrade to and from a vanilla armour, such as that of
     * upgrading an iron chestplate to a diamond chestplate.
     */
    VANILLA_ARMOR,
    /**
     * The CUSTOM flag indicates that an evolution is an upgrade added by this or another mod, such as that of
     * upgrading a weapon to a unique variety of said weapon.
     */
    CUSTOM;

    @Override
    public @NotNull String getSerializedName() {
        return this.name();
    }
    public static final Codec<TransformEvolutionFlag> CODEC = StringRepresentable.fromEnum(TransformEvolutionFlag::values);
}
