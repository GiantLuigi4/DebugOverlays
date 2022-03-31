package tfc.debugoverlays.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.debugoverlays.networking.PacketHandler;

@Mixin(EnderDragon.class)
public class EnderDragonMixin {
	@Inject(at = @At("RETURN"), method = "findPath")
	public void preFindPath(int i, int l, Node f, CallbackInfoReturnable<Path> cir) {
		Path pth = cir.getReturnValue();
		Level lvl = ((Entity) (Object) this).getLevel();
		PacketHandler.sendPath(lvl, ((Mob) (Object) this), pth, pth.getDistToTarget());
	}
}
