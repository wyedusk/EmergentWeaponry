package dev.wyedusk.emergentweaponry.datagen.server;

import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EWRecipeProvider extends RecipeProvider {
    public EWRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output, HolderLookup.@NotNull Provider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Contents.Blocks.MODIFICATION_TABLE.get())
                .pattern("II")
                .pattern("SS")
                .pattern("SS")
                .define('S', Items.STONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("obtain_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(output);
    }
}