package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record EvolutionData(int potential, int maxPotential, int improvementTier) {
    public static final Codec<EvolutionData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("potential").forGetter(EvolutionData::potential),
            Codec.INT.fieldOf("maxPotential").forGetter(EvolutionData::maxPotential),
            Codec.INT.fieldOf("improvementTier").forGetter(EvolutionData::improvementTier)
    ).apply(inst, EvolutionData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, EvolutionData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, EvolutionData::potential,
            ByteBufCodecs.VAR_INT, EvolutionData::maxPotential,
            ByteBufCodecs.VAR_INT, EvolutionData::improvementTier,
            EvolutionData::new
    );
}
