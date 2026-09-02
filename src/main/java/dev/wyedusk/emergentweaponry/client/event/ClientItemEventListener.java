package dev.wyedusk.emergentweaponry.client.event;

import com.mojang.datafixers.util.Either;
import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.gui.component.PotentialBarTooltipComponent;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ProgressionUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = EmergentWeaponry.MODID, value = Dist.CLIENT)
public class ClientItemEventListener {
    @SubscribeEvent
    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        if (!EvolutionUtil.isEvolvable(stack)) return;
        Either<FormattedText, TooltipComponent> component = Either.right(new PotentialBarTooltipComponent(EvolutionUtil.getPotential(stack), EvolutionUtil.getMaxPotential(stack)));
        List<FormattedText> extraComponents = new ArrayList<>(List.of());

        if (Screen.hasShiftDown()) {
            if (ProgressionUtil.canTrackDamageDealt(stack))
                extraComponents.add(FormattedText.of("\uD83D\uDDE1 %s / %s".formatted(ProgressionUtil.getDamageDealt(stack), ProgressionUtil.getRequiredDamageDealt(stack)), Style.EMPTY.withColor(0xFF6A5F7F)));
            if (ProgressionUtil.canTrackEntitiesKilled(stack))
                extraComponents.add(FormattedText.of("☠ %s / %s".formatted(ProgressionUtil.getEntitiesKilled(stack), ProgressionUtil.getRequiredEntitiesKilled(stack)), Style.EMPTY.withColor(0xFF6A5F7F)));
            if (ProgressionUtil.canTrackDamageTaken(stack))
                extraComponents.add(FormattedText.of("\uD83D\uDEE1 %s / %s".formatted(ProgressionUtil.getDamageTaken(stack), ProgressionUtil.getRequiredDamageTaken(stack)), Style.EMPTY.withColor(0xFF6A5F7F)));
            if (ProgressionUtil.canTrackBlocksBroken(stack))
                extraComponents.add(FormattedText.of("⛏ %s / %s".formatted(ProgressionUtil.getBlocksBroken(stack), ProgressionUtil.getRequiredBlocksBroken(stack)), Style.EMPTY.withColor(0xFF6A5F7F)));
        }

        if (event.getTooltipElements().size() > 1) {
            event.getTooltipElements().add(1, component);
            extraComponents.reversed().forEach(comp -> event.getTooltipElements().add(2, Either.left(comp)));
        } else {
            event.getTooltipElements().add(component);
            extraComponents.reversed().forEach(comp -> event.getTooltipElements().add(Either.left(comp)));
        }
    }
}
