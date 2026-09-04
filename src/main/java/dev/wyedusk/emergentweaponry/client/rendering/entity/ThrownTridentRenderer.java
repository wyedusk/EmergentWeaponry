package dev.wyedusk.emergentweaponry.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.wyedusk.emergentweaponry.common.content.entity.base.BaseThrownTrident;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ThrownTridentRenderer extends EntityRenderer<BaseThrownTrident> {
    private final TridentModel model;
    private final ResourceLocation texture;

    public ThrownTridentRenderer(EntityRendererProvider.Context context, ResourceLocation tridentId, ResourceLocation tridentTexture, ModelLayerLocation modelLayer) {
        super(context);

        this.texture = tridentTexture;
        this.model = new TridentModel(context.bakeLayer(modelLayer));
    }

    public void render(BaseThrownTrident trident, float p_116112_, float p_116113_, PoseStack stack, @NotNull MultiBufferSource buffer, int p_116116_) {
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116113_, trident.yRotO, trident.getYRot()) - 90.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_116113_, trident.xRotO, trident.getXRot()) + 90.0F));
        //stack.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(
                buffer, this.model.renderType(this.texture), false, trident.isFoil()
        );
        this.model.renderToBuffer(stack, vertexconsumer, p_116116_, OverlayTexture.NO_OVERLAY);
        stack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseThrownTrident trident) {
        return this.texture;
    }
}
