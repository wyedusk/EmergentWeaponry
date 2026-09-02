package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * The record used for marking item tiers and defining starting values for the evolution mechanic for items
 * that fall under a tier.
 *
 * @param members A list of ResourceLocation corresponding to each item in this tier.
 * @param startingMaxPotential The starting Max Potential value of each item in this tier.
 */
public record TierPotentialData(List<ResourceLocation> members, int startingMaxPotential) {
    public static final Codec<TierPotentialData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.listOf().fieldOf("members").forGetter(TierPotentialData::members),
            Codec.INT.fieldOf("startingMaxPotential").forGetter(TierPotentialData::startingMaxPotential)
    ).apply(inst, TierPotentialData::new));
}
