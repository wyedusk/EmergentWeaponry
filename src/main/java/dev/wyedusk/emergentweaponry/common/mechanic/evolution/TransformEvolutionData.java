package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record TransformEvolutionData(List<TransformEvolutionInstance> evolutionList) {
    public static final Codec<TransformEvolutionData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            TransformEvolutionInstance.CODEC.listOf().fieldOf("evolutionList").forGetter(TransformEvolutionData::evolutionList)
    ).apply(inst, TransformEvolutionData::new));
}
