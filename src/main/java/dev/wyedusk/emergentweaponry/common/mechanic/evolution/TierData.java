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
 * @param startingDamageDealtRequirement The starting Damage Dealt requirement to progress a single Potential point.
 * @param startingKillRequirement The starting Entities Killed requirement to progress a single Potential point.
 * @param startingBlockBreakRequirement The starting Blocks Broken requirement to progress a single Potential point.
 * @param startingDamageTakenRequirement The starting Hits Taken requirement to progress a single Potential point.
 */
public record TierData(List<ResourceLocation> members, int startingMaxPotential, int startingDamageDealtRequirement, int startingKillRequirement, int startingBlockBreakRequirement, int startingDamageTakenRequirement) {
    public static final Codec<TierData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.listOf().fieldOf("members").forGetter(TierData::members),
            Codec.INT.fieldOf("startingMaxPotential").forGetter(TierData::startingMaxPotential),
            Codec.INT.fieldOf("startingDamageDealtRequirement").forGetter(TierData::startingDamageDealtRequirement),
            Codec.INT.fieldOf("startingKillRequirement").forGetter(TierData::startingKillRequirement),
            Codec.INT.fieldOf("startingBlockBreakRequirement").forGetter(TierData::startingBlockBreakRequirement),
            Codec.INT.fieldOf("startingDamageTakenRequirement").forGetter(TierData::startingDamageTakenRequirement)
    ).apply(inst, TierData::new));
}
