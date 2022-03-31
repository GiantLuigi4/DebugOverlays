package tfc.debugoverlays.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.network.PacketDistributor;
import tfc.debugoverlays.DebugOverlays;

import java.util.Arrays;

public class PacketHandler {
	public static void sendPath(Level level, Mob mob, Path path, float distance) {
		try {
			level.players().forEach(player -> {
				if (Registry.ITEM.getKey(player.getOffhandItem().getItem()).toString().equals("debugoverlays:ai_tool")) {
					DebugOverlays.NETWORK_INSTANCE.send(
							PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
							PathPacket.makePathPacket(mob, path, distance)
					);
				}
			});
		} catch (Throwable ignored) {
		}
	}
	
	public static void handle(FriendlyByteBuf buf) {
		int id = buf.readInt();
		
		switch (id) {
			case 0: { // path packet
				if (buf.readBoolean()) {
					int len = buf.readInt();
					Node[] path = new Node[len];
					for (int i = 0; i < len; i++) path[i] = Node.createFromStream(buf);
					Path path1 = new Path(Arrays.asList(path), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()), false);
					Minecraft.getInstance().debugRenderer.pathfindingRenderer.addPath(buf.readInt(), path1, buf.readFloat());
					path1.setNextNodeIndex(buf.readInt());
				}
				break;
			}
		}
	}
	
	public static FriendlyByteBuf writePath(FriendlyByteBuf buf, Path path, Entity entity, float distance) {
		buf.writeInt(0); // path packet
		if (path == null) {
			buf.writeBoolean(false);
		} else {
			buf.writeBoolean(true);
			buf.writeInt(path.getNodeCount());
			for (int i = 0; i < path.getNodeCount(); i++) {
				buf.writeInt(path.getNode(i).x);
				buf.writeInt(path.getNode(i).y);
				buf.writeInt(path.getNode(i).z);
				buf.writeFloat(path.getNode(i).walkedDistance);
				buf.writeFloat(path.getNode(i).costMalus);
				buf.writeBoolean(path.getNode(i).closed);
				buf.writeInt(path.getNode(i).type.ordinal());
				buf.writeFloat(path.getNode(i).f);
			}
			buf.writeInt(path.getTarget().getX());
			buf.writeInt(path.getTarget().getY());
			buf.writeInt(path.getTarget().getZ());
			buf.writeInt(entity.getId());
			buf.writeFloat(distance);
			buf.writeInt(path.getNextNodeIndex());
		}
		return buf;
	}
}
