package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record EvolutionTiersData(Map<ResourceLocation, TierPotentialData> values) {
    public static final Codec<EvolutionTiersData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.unboundedMap(ResourceLocation.CODEC, TierPotentialData.CODEC).fieldOf("values").forGetter(EvolutionTiersData::values)
    ).apply(inst, EvolutionTiersData::new));
}
