package tfc.debugoverlays.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.debugoverlays.networking.PacketHandler;

@Mixin(DebugPackets.class)
public class DebugPacketsMixin {
	@Inject(at = @At("HEAD"), method = "sendPathFindingPacket")
	private static void preSendPathingPacket(Level level, Mob mob, Path path, float f, CallbackInfo ci) {
		try {
			level.players().forEach(player -> {
				if (Registry.ITEM.getKey(player.getOffhandItem().getItem()).toString().equals("debugoverlays:ai_tool")) {
					ServerPlayNetworking.send(
							(ServerPlayer) player,
							new ResourceLocation("debugoverlays:networking"),
							PacketHandler.writePath(PacketByteBufs.create(), path, mob, f)
					);
//					AssortedUtils.NETWORK_INSTANCE.send(
//							PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PathPacket(path, entity, distance)
//					);
				}
			});
		} catch (Throwable ignored) {
		}
	}
}
