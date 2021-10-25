package tfc.debugoverlays.utils;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.LevelRenderer;

public class RenderingUtils {
	public static void doDrawBox(Matrix4f matrix4f, BufferBuilder builder, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
		if (matrix4f == null) {
			LevelRenderer.addChainedFilledBoxVertices(builder, x1, y1, z1, x2, y2, z2, red, green, blue, alpha);
			return;
		}
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
	}
}
