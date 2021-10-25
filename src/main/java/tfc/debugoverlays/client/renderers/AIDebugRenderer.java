package tfc.debugoverlays.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import tfc.debugoverlays.utils.Color;
import tfc.debugoverlays.utils.RenderingUtils;

import java.util.Map;

public class AIDebugRenderer {
	public static void drawLine(PoseStack stack, Path path, int camX, int camY, int camZ) {
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
		
		for (int i = 0; i < path.getNodeCount(); ++i) {
			Node point = path.getNode(i);
			Color c;
			if (point.type.equals(BlockPathTypes.BLOCKED)) {
				c = new Color(128, 128, 128);
			} else {
//				if (i < path.getNodeCount()) {
//					c = new Color(0, 255, 0);
//				} else
				if (i == path.getNextNodeIndex() - 1) {
					c = new Color(255, 0, 255);
				} else
				if (
						point.type.equals(BlockPathTypes.DANGER_FIRE) ||
								point.type.equals(BlockPathTypes.DANGER_OTHER) ||
								point.type.equals(BlockPathTypes.DAMAGE_CACTUS) ||
								point.type.equals(BlockPathTypes.DAMAGE_FIRE) ||
								point.type.equals(BlockPathTypes.DANGER_CACTUS)
				) {
					c = new Color(255, 0, 255);
				} else if (
						point.type.equals(BlockPathTypes.WATER) ||
								point.type.equals(BlockPathTypes.WATER_BORDER)
				) {
					c = new Color(0, 128, 255);
				} else {
					c = new Color(0, 255, 0);
				}
			}
//			double dist = Minecraft.getInstance().player.distanceToSqr(point.x, point.y, point.z);
//			if (dist >= (360 * 3)) {
//				RenderSystem.lineWidth(1f);
//			} else {
//				float distI = (float) Math.max(1, Math.abs(((360f * 3) - dist) / ((360f * 3) / 6f)));
//				RenderSystem.lineWidth(distI);
//			}
			bufferbuilder
					.vertex(stack.last().pose(), (float) point.x + 0.5F, (float) point.y + 0.7F, (float) point.z + 0.5F)
					.color(c.getRed(), c.getGreen(), c.getBlue(), 255)
					.endVertex();
		}
		
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		tessellator.end();
	}
	
	public void render(PoseStack stack, double playerX, double playerY, double playerZ) {
		stack.translate(-playerX, -playerY, -playerZ);
		
		Map<Integer, Path> pathMap = Minecraft.getInstance().debugRenderer.pathfindingRenderer.pathMap;
		if (Minecraft.getInstance().levelRenderer.getTranslucentTarget() != null)
			Minecraft.getInstance().levelRenderer.getTranslucentTarget().bindWrite(true);
		try {
			pathMap.forEach((id, path) -> {
				Entity e = Minecraft.getInstance().level.getEntity(id);
				if (e != null) {
					drawLine(stack, path, 0, 0, 0);
					renderLoop(stack, id, path, e);
				}
			});
		} catch (Throwable ignored) {
		}
		if (Minecraft.getInstance().levelRenderer.getTranslucentTarget() != null)
			Minecraft.getInstance().levelRenderer.getTranslucentTarget().unbindWrite();
	}
	
	void renderLoop(PoseStack stack, int id, Path path, Entity e) {
		for (int i = 0; i < path.getNodeCount(); i++) {
//			Vec3 pos = path.getEntityPosAtNode(e, i).subtract(e.getBbWidth() / 2, 0, e.getBbWidth() / 2).add(0.25, 0, 0.25);
			BlockPos blockPos = path.getNodePos(i);
			Vec3 pos = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()).add(0.5, 0, 0.5);
			Node point = path.getNode(i);
			
			float hue = (float) i / (float) path.getNodeCount() * 0.33F;
			int color = i == 0 ? 0 : Mth.hsvToRgb(hue, 0.9F, 0.9F);
			int red = color >> 16 & 255;
			int blue = color >> 8 & 255;
			int green = color & 255;
			RenderSystem.enableDepthTest();
			RenderSystem.disableTexture();
			RenderSystem.enableCull();
			RenderSystem.setShaderColor(1, 1, 1, 1);
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			drawBox(stack, pos.add(-0.1, 0.6, -0.1), 0.2, 0.2, 0.2, new Color(red, green, blue));
			
			RenderSystem.disableCull();
			drawText(i + "", stack, pos, 1.0);
			drawText(point.type.name(), stack, pos, 1.25);
			drawText(point.costMalus + "", stack, pos, 0.5);
		}
	}
	
	public void drawText(String text, PoseStack stack, Vec3 pos, double yOff) {
		{
			stack.pushPose();
			stack.translate(pos.x, pos.y + yOff, pos.z);
			int width = Minecraft.getInstance().font.width(text);
			stack.scale(0.02f, 0.02F, 0.02F);
			stack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().camera.rotation());
			stack.translate(width / 2f, 0, 0);
			stack.scale(-1, -1, 1);
			Minecraft.getInstance().font.draw(stack, text, 0, 0, new Color(255, 255, 255).getRGB());
			stack.popPose();
		}
	}
	
	public void drawBox(PoseStack stack, Vec3 pos, double w, double h, double l, Color color) {
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
		RenderingUtils.doDrawBox(
				stack.last().pose(),
				bufferbuilder,
				pos.x, pos.y, pos.z,
				pos.x + w, pos.y + h, pos.z + l,
				color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f
		);
		tessellator.end();
	}
}
