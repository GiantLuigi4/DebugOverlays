package tfc.debugoverlays.client;

import net.minecraft.resources.ResourceLocation;
import tfc.debugoverlays.networking.PacketHandler;
import tfc.debugoverlays.utils.DataPacket;

public class DebugOverlaysClient {
	public void onInitializeClient() {
		DataPacket.register(
				new ResourceLocation("debugoverlays:path"),
				PacketHandler::handle
		);
	}
}
