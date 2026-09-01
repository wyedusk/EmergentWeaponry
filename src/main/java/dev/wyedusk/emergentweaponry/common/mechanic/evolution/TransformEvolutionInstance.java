package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record TransformEvolutionInstance(ResourceLocation evolvesInto, TransformEvolutionFlag evolutionFlag) {
    public static final Codec<TransformEvolutionInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("evolvesInto").forGetter(TransformEvolutionInstance::evolvesInto),
            TransformEvolutionFlag.CODEC.fieldOf("evolutionFlag").forGetter(TransformEvolutionInstance::evolutionFlag)
    ).apply(inst, TransformEvolutionInstance::new));
}