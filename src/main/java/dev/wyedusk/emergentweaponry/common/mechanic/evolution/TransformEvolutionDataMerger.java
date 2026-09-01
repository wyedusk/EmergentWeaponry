package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapValueMerger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A merger to try let Transformation Evolvable items have more items added to them without other datapacks
 * completely overriding previous values.
 * <p>
 * I'm not even sure if this works. -Dusk
 */
public class TransformEvolutionDataMerger implements DataMapValueMerger<Item, TransformEvolutionData> {

    @Override
    public @NotNull TransformEvolutionData merge(@NotNull Registry<Item> registry, @NotNull Either<TagKey<Item>, ResourceKey<Item>> first, TransformEvolutionData firstValue, @NotNull Either<TagKey<Item>, ResourceKey<Item>> second, TransformEvolutionData secondValue) {
        List<TransformEvolutionInstance> merged = new ArrayList<>();

        merged.addAll(firstValue.evolutionList());
        merged.addAll(secondValue.evolutionList());

        return new TransformEvolutionData(merged);
    }
}
