package dev.wyedusk.emergentweaponry.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.wyedusk.emergentweaponry.common.EmergentWeaponry;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EWBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private final TridentModel tridentModel;

    public EWBlockEntityWithoutLevelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

        this.tridentModel = new TridentModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.TRIDENT));
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext context, @NotNull PoseStack pose, @NotNull MultiBufferSource buffer, int light, int overlay) {
        if (context == ItemDisplayContext.GUI) {
            pose.popPose();
            pose.pushPose();
            BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
            pose.translate(-0.5F, -0.5F, 0.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, model.getRenderTypes(stack, false).getFirst(), true, stack.hasFoil());
            Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, light, overlay, pose, vertexConsumer);
            return;
        }
        if (stack.is(Contents.Items.INFERNO_TRIDENT)) {
            pose.pushPose();
            pose.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.tridentModel.renderType(
                    ResourceLocation.fromNamespaceAndPath(EmergentWeaponry.MODID, "textures/entity/inferno_trident/inferno_trident.png")
            ), false, stack.hasFoil());
            this.tridentModel.renderToBuffer(pose, vertexConsumer, light, overlay);
            pose.popPose();
        }
    }
}
