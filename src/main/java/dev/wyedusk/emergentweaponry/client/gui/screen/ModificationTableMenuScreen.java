package dev.wyedusk.emergentweaponry.client.gui.screen;

import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.config.ServerConfig;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import dev.wyedusk.emergentweaponry.common.content.menu.ModificationTableMenu;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.EvolutionUtil;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ItemEvolutionData;
import dev.wyedusk.emergentweaponry.common.mechanic.evolution.ProgressionUtil;
import dev.wyedusk.emergentweaponry.common.network.packet.C2SModifyItemPacket;
import dev.wyedusk.emergentweaponry.common.util.ItemStatUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class ModificationTableMenuScreen extends AbstractContainerScreen<ModificationTableMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "textures/gui/container/modification_table.png");
    private static final ResourceLocation SELECTION_BOX_SPRITE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "container/selection_box");
    private static final ResourceLocation TITLE_BOX_SPRITE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "container/modification_table/title_box");
    private static final ResourceLocation ATTRIBUTE_BOX_SPRITE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "container/modification_table/attribute_box");
    private static final ResourceLocation SCROLLER_TOP_SPRITE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "container/modification_table/scroller_top");
    private static final ResourceLocation SCROLLER_MIDDLE_SPRITE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "container/modification_table/scroller_middle");
    private static final ResourceLocation SCROLLER_BOTTOM_SPRITE = ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "container/modification_table/scroller_bottom");

    private int topSlot;
    private int middleSlot;
    private int bottomSlot;
    private int detailPanelScroll = 0;
    private int detailPanelMaxScroll = 0;

    private final BiPredicate<Integer, Integer> isInTopSlot = (x, y) -> inArea(x, y, 43, 28, 58, 43);
    private final BiPredicate<Integer, Integer> isInMiddleSlot = (x, y) -> inArea(x, y, 43, 47, 58, 62);
    private final BiPredicate<Integer, Integer> isInBottomSlot = (x, y) -> inArea(x, y, 43, 66, 58, 81);

    public ModificationTableMenuScreen(ModificationTableMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 191;
        this.inventoryLabelY = 97;
    }

    private record ModifiedStatDetail(int min, int max, double original, double modified) {}

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        renderTransparentBackground(graphics);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (menu.getBlockEntity().getLevel() != null)
            menu.availableModifications = EvolutionUtil.getAvailableEvolutionItems(
                    menu.getBlockEntity().getLevel().registryAccess(),
                    menu.temporaryInventory.getItem(0)).toArray(new ItemStack[0]
            );
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.blitSprite(SELECTION_BOX_SPRITE, this.leftPos + 40, this.topPos + 25 + (19 * menu.highlightedResultSlot), 22, 22);

        if (menu.availableModifications.length > 0) {
            this.topSlot = Math.toIntExact(Math.floorMod((long) menu.currentListIndex - 1, (long) menu.availableModifications.length));
            this.middleSlot = Math.clamp(menu.currentListIndex, 0, menu.availableModifications.length - 1);
            this.bottomSlot = Math.toIntExact(Math.floorMod((long) menu.currentListIndex + 1, (long) menu.availableModifications.length));

            graphics.renderItem(menu.availableModifications[this.topSlot], this.leftPos + 43, this.topPos + 28);
            graphics.renderItem(menu.availableModifications[this.middleSlot], this.leftPos + 43, this.topPos + 47);
            graphics.renderItem(menu.availableModifications[this.bottomSlot], this.leftPos + 43, this.topPos + 66);

            // Manually render tooltips because the items aren't real
            if (isInTopSlot.test(mouseX, mouseY)) {
                ItemStack slot = menu.availableModifications[this.topSlot];
                if (!slot.isEmpty())
                    graphics.renderTooltip(this.font, getTooltipFromContainerItem(slot), slot.getTooltipImage(), slot, mouseX, mouseY);
            } else if (isInMiddleSlot.test(mouseX, mouseY)) {
                ItemStack slot = menu.availableModifications[this.middleSlot];
                if (!slot.isEmpty())
                    graphics.renderTooltip(this.font, getTooltipFromContainerItem(slot), slot.getTooltipImage(), slot, mouseX, mouseY);
            } else if (isInBottomSlot.test(mouseX, mouseY)) {
                ItemStack slot = menu.availableModifications[this.bottomSlot];
                if (!slot.isEmpty())
                    graphics.renderTooltip(this.font, getTooltipFromContainerItem(slot), slot.getTooltipImage(), slot, mouseX, mouseY);
            }
        }

        drawDetailsPanelInfo(graphics);
        drawDetailsPanelScroller(graphics);
        renderTooltip(graphics, mouseX, mouseY);
    }

    private void drawDetailsPanelInfo(GuiGraphics graphics) {
        if (menu.availableModifications.length == 0) return;

        ItemStack slot = switch (menu.highlightedResultSlot) {
            case 0 -> menu.availableModifications[this.topSlot];
            case 1 -> menu.availableModifications[this.middleSlot];
            case 2 -> menu.availableModifications[this.bottomSlot];
            default -> null;
        };
        if (slot == null) {
            detailPanelScroll = 0;
            detailPanelMaxScroll = 0;
            return;
        }

        HashMap<String, ModifiedStatDetail> trackedStats = new HashMap<>();

        ItemStack maxStack = slot.copy();
        maxStack.set(Contents.DataComponents.EVOLUTION_DATA, new ItemEvolutionData(1, 1, ServerConfig.MAX_IMPROVEMENT_TIER.getAsInt()));

        var defaultMainHandStats = ItemStatUtil.getDefaultItemStats(slot.getItem(), EquipmentSlot.MAINHAND);
        var oldMainHandStats = ItemStatUtil.getItemStats(menu.temporaryInventory.getItem(0), EquipmentSlot.MAINHAND);
        var currentMainHandStats = ItemStatUtil.getItemStats(slot, EquipmentSlot.MAINHAND);
        var maxMainHandStats = ItemStatUtil.getItemStats(maxStack, EquipmentSlot.MAINHAND);

        if (ProgressionUtil.canTrackDamageDealt(slot) || ProgressionUtil.canTrackEntitiesKilled(slot)) {
            double baseDamage = 1.0 + defaultMainHandStats.getOrDefault(Attributes.ATTACK_DAMAGE, 0.0);
            double oldDamage = 1.0 + oldMainHandStats.getOrDefault(Attributes.ATTACK_DAMAGE, 0.0);
            double currentDamage = 1.0 + currentMainHandStats.getOrDefault(Attributes.ATTACK_DAMAGE, 0.0);
            double maxDamage = 1.0 + maxMainHandStats.getOrDefault(Attributes.ATTACK_DAMAGE, 0.0);
            trackedStats.put("Damage", new ModifiedStatDetail(
                    (int) (Math.floor(baseDamage / 5) * 5),
                    (int) (Math.ceil(maxDamage / 5) * 5),
                    oldDamage,
                    currentDamage
            ));
            double baseKnockback = 1.0 + defaultMainHandStats.getOrDefault(Attributes.ATTACK_KNOCKBACK, 0.0);
            double oldKnockback = 1.0 + oldMainHandStats.getOrDefault(Attributes.ATTACK_KNOCKBACK, 0.0);
            double currentKnockback = 1.0 + currentMainHandStats.getOrDefault(Attributes.ATTACK_KNOCKBACK, 0.0);
            double maxKnockback = 1.0 + maxMainHandStats.getOrDefault(Attributes.ATTACK_KNOCKBACK, 0.0);
            trackedStats.put("Knockback", new ModifiedStatDetail(
                    (int) (Math.floor(baseKnockback / 5) * 5),
                    (int) (Math.ceil(maxKnockback / 5) * 5),
                    oldKnockback,
                    currentKnockback
            ));
        }
        if (ProgressionUtil.canTrackBlocksBroken(slot)) {
            double baseEfficiency = 1.0 + defaultMainHandStats.getOrDefault(Attributes.MINING_EFFICIENCY, 0.0);
            double oldEfficiency = 1.0 + oldMainHandStats.getOrDefault(Attributes.MINING_EFFICIENCY, 0.0);
            double currentEfficiency = 1.0 + currentMainHandStats.getOrDefault(Attributes.MINING_EFFICIENCY, 0.0);
            double maxEfficiency = 1.0 + maxMainHandStats.getOrDefault(Attributes.MINING_EFFICIENCY, 0.0);
            trackedStats.put("Efficiency", new ModifiedStatDetail(
                    (int) (Math.floor(baseEfficiency / 5) * 5),
                    (int) (Math.ceil(maxEfficiency / 5) * 5),
                    oldEfficiency,
                    currentEfficiency
            ));
            double baseMineSpeed = 1.0 + defaultMainHandStats.getOrDefault(Attributes.BLOCK_BREAK_SPEED, 0.0);
            double oldMineSpeed = 1.0 + oldMainHandStats.getOrDefault(Attributes.BLOCK_BREAK_SPEED, 0.0);
            double currentMineSpeed = 1.0 + currentMainHandStats.getOrDefault(Attributes.BLOCK_BREAK_SPEED, 0.0);
            double maxMineSpeed = 1.0 + maxMainHandStats.getOrDefault(Attributes.BLOCK_BREAK_SPEED, 0.0);
            trackedStats.put("Mining Speed", new ModifiedStatDetail(
                    (int) (Math.floor(baseMineSpeed / 5) * 5),
                    (int) (Math.ceil(maxMineSpeed / 5) * 5),
                    oldMineSpeed,
                    currentMineSpeed
            ));
        }
        if (ProgressionUtil.canTrackDamageTaken(slot)) {
            double baseArmor = 1.0 + defaultMainHandStats.getOrDefault(Attributes.ARMOR, 0.0);
            double oldArmor = 1.0 + oldMainHandStats.getOrDefault(Attributes.ARMOR, 0.0);
            double currentArmor = 1.0 + currentMainHandStats.getOrDefault(Attributes.ARMOR, 0.0);
            double maxArmor = 1.0 + maxMainHandStats.getOrDefault(Attributes.ARMOR, 0.0);
            trackedStats.put("Armor", new ModifiedStatDetail(
                    (int) (Math.floor(baseArmor / 5) * 5),
                    (int) (Math.ceil(maxArmor / 5) * 5),
                    oldArmor,
                    currentArmor
            ));
            double baseToughness = 1.0 + defaultMainHandStats.getOrDefault(Attributes.ARMOR_TOUGHNESS, 0.0);
            double oldToughness = 1.0 + oldMainHandStats.getOrDefault(Attributes.ARMOR_TOUGHNESS, 0.0);
            double currentToughness = 1.0 + currentMainHandStats.getOrDefault(Attributes.ARMOR_TOUGHNESS, 0.0);
            double maxToughness = 1.0 + maxMainHandStats.getOrDefault(Attributes.ARMOR_TOUGHNESS, 0.0);
            trackedStats.put("Toughness", new ModifiedStatDetail(
                    (int) (Math.floor(baseToughness / 5) * 5),
                    (int) (Math.ceil(maxToughness / 5) * 5),
                    oldToughness,
                    currentToughness
            ));
        }

        ModifiedStatDetail defaultStatDetail = new ModifiedStatDetail(0, 1, 0, 0);

        graphics.enableScissor(this.leftPos + 68, this.topPos + 16, this.leftPos + 159, this.topPos + 88);
        int y = this.topPos + 17 - detailPanelScroll;
        graphics.blitSprite(TITLE_BOX_SPRITE, this.leftPos + 68, y, 91, 18);
        graphics.drawString(font, slot.getItem().getName(slot).getString(), this.leftPos + 70, y + 4, 0xFFFFFFFF, true);
        y += 18;
        for (Map.Entry<String, ModifiedStatDetail> entry : trackedStats.entrySet()) {
            String name = entry.getKey();
            ModifiedStatDetail stat = entry.getValue();
            graphics.blitSprite(ATTRIBUTE_BOX_SPRITE, this.leftPos + 68, y, 91, 16);
            graphics.drawString(font, name, this.leftPos + 70, y + 2, 0xFFFFFFFF, true);
            int filledWidth = (int) (87 * (stat.max / stat.original));
            int filledEndX = this.leftPos + 70 + filledWidth;
            if (filledWidth > 0) {
                graphics.fill(this.leftPos + 70, y + 12, filledEndX - 1, y + 13, 0xFFFFFFFF);
                int filledModifiedWidth = (int) (stat.max * (stat.modified - stat.original));
                int filledModifiedEndX = filledEndX + filledModifiedWidth;
                if (filledModifiedWidth != 0) {
                    if (filledModifiedEndX > filledEndX) graphics.fill(filledEndX - 1, y + 12, filledModifiedEndX - 1, y + 13, 0xFF00FF00);
                    else graphics.fill(filledModifiedEndX - 1, y + 12, filledEndX - 1, y + 13, 0xFFFF0000);
                }
            }
            y += 16;
        }
        graphics.disableScissor();

        int usedHeight = 18 + (16 * trackedStats.size());
        if (usedHeight > 72) detailPanelMaxScroll = 72 - usedHeight;
    }

    private void drawDetailsPanelScroller(GuiGraphics graphics) {
        final int maxMidRepeat = 32;
        final int trackHeight = 72;
        final int fixedMinHeight = 7;

        int repeats = maxMidRepeat;
        int scrollerOffset = 0;
        if (detailPanelMaxScroll != 0) {
            float thumbRatio = (float) trackHeight / (trackHeight + detailPanelMaxScroll);
            int scrollerHeight = Math.round(trackHeight * thumbRatio);
            repeats = Math.round((scrollerHeight - fixedMinHeight) / 2.0f);
            repeats = Mth.clamp(repeats, 1, maxMidRepeat);

            scrollerHeight = fixedMinHeight + repeats * 2;
            int travel = trackHeight - scrollerHeight;
            if (detailPanelMaxScroll > 0) {
                scrollerOffset = Math.round((float) detailPanelScroll / detailPanelMaxScroll * travel);
            }
        }

        int y = topPos + 17 + scrollerOffset;

        graphics.blitSprite(SCROLLER_TOP_SPRITE, leftPos + 161, y, 7, 4);
        for (int i = 0; i < repeats; i++) {
            graphics.blitSprite(SCROLLER_MIDDLE_SPRITE, leftPos + 161, (y + 4) + (i * 2), 7, 2);
        }
        graphics.blitSprite(SCROLLER_BOTTOM_SPRITE, leftPos + 161, y + 4 + repeats * 2, 7, 3);
    }

    private void sendModifyItemPacket(ItemStack slot) {
        PacketDistributor.sendToServer(new C2SModifyItemPacket(
                BuiltInRegistries.ITEM.getKey(slot.getItem()), slot.getItem() == menu.temporaryInventory.getItem(0).getItem()
        ));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (menu.availableModifications.length > 0) {
            if (isInTopSlot.test((int) mouseX, (int) mouseY)) {
                ItemStack slot = menu.availableModifications[this.topSlot];
                if (!slot.isEmpty())
                    sendModifyItemPacket(slot);
            } else if (isInMiddleSlot.test((int) mouseX, (int) mouseY)) {
                ItemStack slot = menu.availableModifications[this.middleSlot];
                if (!slot.isEmpty())
                    sendModifyItemPacket(slot);
            } else if (isInBottomSlot.test((int) mouseX, (int) mouseY)) {
                ItemStack slot = menu.availableModifications[this.bottomSlot];
                if (!slot.isEmpty())
                    sendModifyItemPacket(slot);
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        // If in the vertical result select area, scroll the result slot
        if (inArea(mouseX, mouseY, 39, 17, 62, 92)) {
            detailPanelScroll = 0;
            detailPanelMaxScroll = 0;
            if (menu.highlightedResultSlot == 0 && scrollY > 0) menu.currentListIndex -= 1;
            if (menu.highlightedResultSlot == 2 && scrollY < 0) menu.currentListIndex += 1;
            menu.highlightedResultSlot = Math.clamp(menu.highlightedResultSlot - (scrollY > 0 ? 1 : -1), 0, 2);
        }
        // If in the details panel area, scroll the details panel
        if (inArea(mouseX, mouseY, 67, 16, 168, 93)) {
            detailPanelScroll = (int) (detailPanelScroll - scrollY * 3);
        }

        // Clamp scroll values
        if (detailPanelScroll < 0) detailPanelScroll = 0;
        if (detailPanelScroll >= detailPanelMaxScroll) detailPanelScroll = detailPanelMaxScroll;
        if (menu.currentListIndex < 0) menu.currentListIndex = menu.availableModifications.length - 1;
        if (menu.currentListIndex >= menu.availableModifications.length) menu.currentListIndex = 0;

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private boolean inArea(double mouseX, double mouseY, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        return (mouseX >= this.leftPos + topLeftX && mouseX <= this.leftPos + bottomRightX && mouseY >= this.topPos + topLeftY && mouseY <= this.topPos + bottomRightY);
    }
}