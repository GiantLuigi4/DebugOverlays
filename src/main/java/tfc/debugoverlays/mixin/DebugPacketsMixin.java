package tfc.debugoverlays.mixin;

import net.minecraft.core.Registry;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.debugoverlays.DebugOverlays;
import tfc.debugoverlays.networking.PacketHandler;
import tfc.debugoverlays.networking.PathPacket;

@Mixin(DebugPackets.class)
public class DebugPacketsMixin {
	@Inject(at = @At("HEAD"), method = "sendPathFindingPacket")
	private static void preSendPathingPacket(Level level, Mob mob, Path path, float f, CallbackInfo ci) {
		PacketHandler.sendPath(level, mob, path, f);
	}
}
