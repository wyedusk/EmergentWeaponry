package dev.wyedusk.emergentweaponry.mixin.common;

import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionTiersData;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ItemEvolutionData;
import net.minecraft.core.Registry;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    @Final
    @Mutable
    PatchedDataComponentMap components;

    @Shadow
    public abstract Item getItem();

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V", at = @At("TAIL"))
    public void emergentweaponry$init(ItemLike item, int count, PatchedDataComponentMap components, CallbackInfo ci) {
        ItemStack instance = (ItemStack) (Object) this;
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;
        Registry<EvolutionTiersData> registry = server.registryAccess().registry(Contents.DatapackRegistries.EVOLUTION).orElse(null);
        if (registry == null) return;
        ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(instance.getItem());
        registry.forEach(registryValues -> registryValues.values().forEach((resLoc, tierData) -> {
            final int maxPotential = tierData.startingMaxPotential();
            if (tierData.members().contains(itemKey)) {
                components.set(Contents.DataComponents.EVOLUTION_DATA.get(), new ItemEvolutionData(0, maxPotential, 0));
            }
        }));
        this.components = components;
    }
}
