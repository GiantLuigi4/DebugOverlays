package tfc.debugoverlays.utils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.function.Consumer;

public class DataPacket {
	static HashMap<ResourceLocation, Consumer<FriendlyByteBuf>> handlers = new HashMap<>();
	
	ResourceLocation loc;
	Consumer<FriendlyByteBuf> hndlr;
	FriendlyByteBuf buf;
	
	private DataPacket(FriendlyByteBuf buf) {
		hndlr = handlers.get(buf.readResourceLocation());
		this.buf = buf;
	}
	
	public DataPacket(String location, Consumer<FriendlyByteBuf> encoder) {
		this(new ResourceLocation(location), encoder);
	}
	
	public DataPacket(ResourceLocation location, Consumer<FriendlyByteBuf> encoder) {
		loc = location;
		hndlr = encoder;
	}
	
	public static void register(ResourceLocation location, Consumer<FriendlyByteBuf> handler) {
		handlers.put(location, handler);
	}
	
	public static void setup(SimpleChannel networkInstance) {
		networkInstance.registerMessage(
				0, DataPacket.class,
				DataPacket::enc, DataPacket::new,
				(pckt, ctx) -> {
					ctx.get().setPacketHandled(true);
					pckt.hndlr.accept(pckt.buf);
				}
		);
	}
	
	public static void enc(DataPacket packet, FriendlyByteBuf buf) {
		buf.writeResourceLocation(packet.loc);
		packet.hndlr.accept(buf);
	}
}
