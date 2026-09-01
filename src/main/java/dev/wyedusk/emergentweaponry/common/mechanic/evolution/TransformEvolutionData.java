package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

/**
 * The record used for the holding transformation evolution paths on items. For the record used in item data
 * components, see the ItemEvolutionType class, or for the record used to hold specific evolution items from this,
 * see the TransformEvolutionInstance class.
 *
 * @param evolutionList A list of TransformEvolutionInstance, where one instance is one evolution item.
 */
public record TransformEvolutionData(List<TransformEvolutionInstance> evolutionList) {
    public static final Codec<TransformEvolutionData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            TransformEvolutionInstance.CODEC.listOf().fieldOf("evolutionList").forGetter(TransformEvolutionData::evolutionList)
    ).apply(inst, TransformEvolutionData::new));
}
