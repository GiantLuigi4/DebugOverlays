package tfc.debugoverlays.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import tfc.debugoverlays.networking.PacketHandler;

@Environment(EnvType.CLIENT)
public class DebugOverlaysClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation("debugoverlays:networking"), (client, handler, buf, responseSender) -> {
			PacketHandler.handle(buf, handler, responseSender);
		});
	}
}
