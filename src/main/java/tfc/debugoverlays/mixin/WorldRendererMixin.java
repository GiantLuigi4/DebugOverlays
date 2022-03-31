package tfc.debugoverlays.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.debugoverlays.client.renderers.AIDebugRenderer;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {
	@Shadow @Final private Minecraft minecraft;
	@Unique
	AIDebugRenderer AIRenderer = new AIDebugRenderer();
	
	@Inject(at = @At("TAIL"), method = "renderLevel")
	public void postRender(PoseStack poseStack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
		if (minecraft.player == null) return;
		if (!Registry.ITEM.getKey(minecraft.player.getOffhandItem().getItem()).toString().equals("debugoverlays:ai_tool")) return;

//		Matrix4f srcMat = RenderSystem.getModelViewMatrix();
//
//		Matrix4f mdlMat = srcMat.copy();
//
//		mdlMat.multiply(
//				new Quaternion(
//						new Vector3f(1, 0, 0),
//						Minecraft.getInstance().getEntityRenderDispatcher().camera.getXRot(),
//						false
//				)
//		);
//		mdlMat.multiply(
//				new Quaternion(
//						new Vector3f(0, 1, 0),
//						Minecraft.getInstance().getEntityRenderDispatcher().camera.getYRot() + 180,
//						false
//				)
//		);
		
		poseStack.pushPose();
//		poseStack.translate(
//				-Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().x,
//				-Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().y,
//				-Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().z
//		);
//		Quaternion rotation = Minecraft.getInstance().getEntityRenderDispatcher().camera.rotation().copy();
//		rotation.mul(-1);
//		poseStack.mulPose(rotation);
		
//		mdlMat.multiplyWithTranslation(
//				(float) -Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().x,
//				(float) -Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().y,
//				(float) -Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().z
//		);
		
//		RenderSystem.setProjectionMatrix(mdlMat);
		
		AIRenderer.render(poseStack,
				Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().x,
				Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().y,
				Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().z
		);
		poseStack.popPose();
//		RenderSystem.setProjectionMatrix(srcMat);
	}
	
	@Inject(at = @At("HEAD"), method = "setLevel")
	public void postResetChunks(ClientLevel clientLevel, CallbackInfo ci) {
		Minecraft.getInstance().debugRenderer.pathfindingRenderer.pathMap.clear();
	}
}
