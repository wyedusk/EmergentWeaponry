package dev.wyedusk.emergentweaponry.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.wyedusk.emergentweaponry.common.content.Contents;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SpinAttackEffectLayer.class)
public class SpinAttackEffectLayerMixin<T extends LivingEntity> {
    @Final
    @Shadow
    private ModelPart box;

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    public void emergentweaponry$render(PoseStack pose, MultiBufferSource buffer, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        ItemStack mainHandItem = entity.getMainHandItem();
        ItemStack offHandItem = entity.getOffhandItem();
        ItemStack useItem = mainHandItem.is(ItemTags.TRIDENT_ENCHANTABLE) ? mainHandItem : offHandItem;
        ResourceLocation useItemId = BuiltInRegistries.ITEM.getKey(useItem.getItem());

        List<Item> customTridents = new ArrayList<>();
        Contents.Items.tridents.forEach(defItem -> customTridents.add(defItem.get()));

        if (entity.isAutoSpinAttack() && customTridents.contains(useItem.getItem())) {
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(useItemId.getNamespace(), "textures/entity/" + useItemId.getPath() + "/" + useItemId.getPath() + "_riptide.png");
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));

            for(int i = 0; i < 3; ++i) {
                pose.pushPose();
                float f = ageInTicks * (float)(-(45 + i * 5));
                pose.mulPose(Axis.YP.rotationDegrees(f));
                float f1 = 0.75F * (float)i;
                pose.scale(f1, f1, f1);
                pose.translate(0.0F, -0.2F + 0.6F * (float)i, 0.0F);
                this.box.render(pose, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
                pose.popPose();
            }

            ci.cancel();
        }
    }
}
