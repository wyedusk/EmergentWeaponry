package dev.wyedusk.emergentweaponry.client.event;

import com.mojang.datafixers.util.Either;
import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.gui.component.PotentialBarTooltipComponent;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ItemEvolutionData;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = EmergentWeaponry.MODID, value = Dist.CLIENT)
public class ClientItemEventListener {
    @SubscribeEvent
    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        ItemEvolutionData evoData = stack.get(Contents.DataComponents.EVOLUTION_DATA);
        if (evoData == null) return;
        Either<FormattedText, TooltipComponent> component = Either.right(new PotentialBarTooltipComponent(evoData.potential(), evoData.maxPotential()));
        if (event.getTooltipElements().size() > 1) {
            event.getTooltipElements().add(1, component);
        } else {
            event.getTooltipElements().add(component);
        }
    }
}
