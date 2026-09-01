package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * The record used for the evolution data component on items. For the record used in the data map for transformation
 * evolutions, see the TransformEvolutionData class.
 *
 * @param potential An integer value of how much Potential the item has.
 * @param maxPotential An integer value of the item's Max Potential.
 * @param improvementTier An integer value of the item's Improvement Tier
 */
public record ItemEvolutionData(int potential, int maxPotential, int improvementTier) {
    public static final Codec<ItemEvolutionData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("potential").forGetter(ItemEvolutionData::potential),
            Codec.INT.fieldOf("maxPotential").forGetter(ItemEvolutionData::maxPotential),
            Codec.INT.fieldOf("improvementTier").forGetter(ItemEvolutionData::improvementTier)
    ).apply(inst, ItemEvolutionData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemEvolutionData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ItemEvolutionData::potential,
            ByteBufCodecs.VAR_INT, ItemEvolutionData::maxPotential,
            ByteBufCodecs.VAR_INT, ItemEvolutionData::improvementTier,
            ItemEvolutionData::new
    );
}
