package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * The record used for holding how many times each progression statistic on an evolvable item has looped.
 *
 * @param damageDealt The amount of times the Damage Dealt progression has been looped.
 * @param entitiesKilled The amount of times the Entities Killed progression has been looped.
 * @param blocksBroken The amount of times the Blocks Broken progression has been looped.
 * @param damageTaken The amount of times the Damage Taken progression has been looped.
 */
public record ProgressionLoopData(int damageDealt, int entitiesKilled, int blocksBroken, int damageTaken) {
    public static final Codec<ProgressionLoopData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("damageDealt").forGetter(ProgressionLoopData::damageDealt),
            Codec.INT.fieldOf("entitiesKilled").forGetter(ProgressionLoopData::entitiesKilled),
            Codec.INT.fieldOf("blocksBroken").forGetter(ProgressionLoopData::blocksBroken),
            Codec.INT.fieldOf("damageTaken").forGetter(ProgressionLoopData::damageTaken)
    ).apply(inst, ProgressionLoopData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ProgressionLoopData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ProgressionLoopData::damageDealt,
            ByteBufCodecs.VAR_INT, ProgressionLoopData::entitiesKilled,
            ByteBufCodecs.VAR_INT, ProgressionLoopData::blocksBroken,
            ByteBufCodecs.VAR_INT, ProgressionLoopData::damageTaken,
            ProgressionLoopData::new
    );
}
