package tfc.debugoverlays.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Path;
import tfc.debugoverlays.utils.DataPacket;

public class PathPacket {
	public static DataPacket makePathPacket(Mob mob, Path path, float f) {
		return new DataPacket("debugoverlays:path", (buf) -> {
			PacketHandler.writePath(buf, path, mob, f);
		});
	}
}
