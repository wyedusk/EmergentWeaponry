package dev.wyedusk.emergentweaponry.mixin.common;

import dev.wyedusk.emergentweaponry.common.config.ServerConfig;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.gui.component.StylisedComponents;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.*;
import net.minecraft.core.Registry;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    @Final
    @Mutable
    PatchedDataComponentMap components;

    @Shadow
    public abstract Item getItem();

    // Add components to evolvable items
    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V", at = @At("TAIL"))
    public void emergentweaponry$init(ItemLike item, int count, PatchedDataComponentMap components, CallbackInfo ci) {
        ItemStack instance = (ItemStack) (Object) this;
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;
        Registry<TierDataHolder> registry = server.registryAccess().registry(Contents.DatapackRegistries.EVOLUTION).orElse(null);
        if (registry == null) return;
        ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(instance.getItem());

        // Add evolution data if this item is an evolvable item and doesn't have it already.
        registry.forEach(registryValues -> registryValues.values().forEach((resLoc, tierData) -> {
            final int maxPotential = tierData.startingMaxPotential();
            List<ResourceLocation> tierMembers = tierData.members();
            for (ResourceLocation memberLoc : tierMembers) {
                boolean match = BuiltInRegistries.ITEM.containsKey(memberLoc) || BuiltInRegistries.ITEM.getTag(TagKey.create(Registries.ITEM, memberLoc)).isPresent();
                if (match) {
                    if (!(components.has(Contents.DataComponents.EVOLUTION_DATA.get()))) {
                        components.set(Contents.DataComponents.EVOLUTION_DATA.get(), new ItemEvolutionData(0, maxPotential, 0));
                    }
                }
            }
        }));
        // If the item is evolvable, add any missing progression data components.
        if (EvolutionUtil.isEvolvable(instance)) {
            if (!components.has(Contents.DataComponents.PROGRESSION_DATA.get())) {
                components.set(Contents.DataComponents.PROGRESSION_DATA.get(), new ProgressionData(0, 0, 0, 0));
            }
            if (!components.has(Contents.DataComponents.PROGRESSION_LOOP_DATA.get())) {
                components.set(Contents.DataComponents.PROGRESSION_LOOP_DATA.get(), new ProgressionLoopData(0, 0, 0, 0));
            }
        }

        this.components = components;
    }

    // Increase rarity based on Improvement tier
    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    public void emergentweaponry$getRarity(CallbackInfoReturnable<Rarity> cir) {
        ItemStack instance = (ItemStack) (Object) this;
        Rarity rarity = cir.getReturnValue();
        Rarity[] rarities = Rarity.values();

        if (EvolutionUtil.getImprovementTier(instance) != -1) {
            if (EvolutionUtil.getImprovementTier(instance) == ServerConfig.MAX_IMPROVEMENT_TIER.getAsInt()) {
                cir.setReturnValue(Rarity.EPIC);
                return;
            }
            int nextRarity = Math.min(rarity.ordinal() + EvolutionUtil.getImprovementTier(instance), rarities.length - 1);
            cir.setReturnValue(rarities[nextRarity]);
        }
    }

    // Custom stylisation for maximum Improvement tier
    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    public void emergentweaponry$getHoverName(CallbackInfoReturnable<Component> cir) {
        ItemStack instance = (ItemStack) (Object) this;
        boolean isMaxImprovement = EvolutionUtil.getImprovementTier(instance) == ServerConfig.MAX_IMPROVEMENT_TIER.getAsInt()
                && EvolutionUtil.getPotential(instance) >= EvolutionUtil.getMaxPotential(instance);

        MutableComponent nameStyle = cir.getReturnValue().copy();

        if (isMaxImprovement) {
            Component customStyledName = StylisedComponents.getMaxImprovementStyleTextComponent(nameStyle.getString());
            nameStyle = customStyledName.copy();
        }

        cir.setReturnValue(nameStyle);
    }
}
