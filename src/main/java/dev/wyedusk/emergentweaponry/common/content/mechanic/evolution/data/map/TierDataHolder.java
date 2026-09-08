package dev.wyedusk.emergentweaponry.common.content.mechanic.evolution.data.map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds TierData entries so their format matches that of a data map.
 *
 * @param values A list of TierData entries.
 */
public record TierDataHolder(Map<ResourceLocation, TierData> values) {
    public static final Codec<TierDataHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.unboundedMap(ResourceLocation.CODEC, TierData.CODEC).fieldOf("values").forGetter(TierDataHolder::values)
    ).apply(inst, TierDataHolder::new));

    public static final StreamCodec<ByteBuf, TierDataHolder> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, TierData.STREAM_CODEC), TierDataHolder::values,
            TierDataHolder::new
    );
}
