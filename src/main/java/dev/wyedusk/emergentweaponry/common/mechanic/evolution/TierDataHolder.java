package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

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
}
