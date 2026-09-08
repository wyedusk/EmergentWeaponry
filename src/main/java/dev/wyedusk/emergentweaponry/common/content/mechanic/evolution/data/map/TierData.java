package dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
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

    public static final StreamCodec<ByteBuf, TierData> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), TierData::members,
            ByteBufCodecs.VAR_INT, TierData::startingMaxPotential,
            ByteBufCodecs.VAR_INT, TierData::startingDamageDealtRequirement,
            ByteBufCodecs.VAR_INT, TierData::startingKillRequirement,
            ByteBufCodecs.VAR_INT, TierData::startingBlockBreakRequirement,
            ByteBufCodecs.VAR_INT, TierData::startingDamageTakenRequirement,
            TierData::new
    );
}
