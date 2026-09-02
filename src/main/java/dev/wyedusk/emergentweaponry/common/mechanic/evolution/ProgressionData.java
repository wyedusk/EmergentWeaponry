package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * The record used for holding progression information for evolvable items.
 *
 * @param damageDealt The amount of damage dealt using this item. Counts only for weapons (swords, axes, ...)
 * @param entitiesKilled The amount of entities killed using this item. Counts only for weapons (swords, axes, ...)
 * @param blocksBroken The amount of blocks broken using this item. Counts only for destructive tools (pickaxes, axes, ...)
 * @param damageTaken The amount of damage this item has taken. Counts only for protective items (armour, shields, ...)
 */
public record ProgressionData(int damageDealt, int entitiesKilled, int blocksBroken, int damageTaken) {
    public static final Codec<ProgressionData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("damageDealt").forGetter(ProgressionData::damageDealt),
            Codec.INT.fieldOf("entitiesKilled").forGetter(ProgressionData::entitiesKilled),
            Codec.INT.fieldOf("blocksBroken").forGetter(ProgressionData::blocksBroken),
            Codec.INT.fieldOf("damageTaken").forGetter(ProgressionData::damageTaken)
    ).apply(inst, ProgressionData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ProgressionData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ProgressionData::damageDealt,
            ByteBufCodecs.VAR_INT, ProgressionData::entitiesKilled,
            ByteBufCodecs.VAR_INT, ProgressionData::blocksBroken,
            ByteBufCodecs.VAR_INT, ProgressionData::damageTaken,
            ProgressionData::new
    );
}
