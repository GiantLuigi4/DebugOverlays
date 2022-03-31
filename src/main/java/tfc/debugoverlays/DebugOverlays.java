package tfc.debugoverlays;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tfc.debugoverlays.client.DebugOverlaysClient;
import tfc.debugoverlays.utils.DataPacket;

@Mod("debugoverlays")
public class DebugOverlays {
	public static final SimpleChannel NETWORK_INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation("debugoverlays"),
			()->"1",
			"1"::equals,
			"1"::equals
	);
	
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "debugoverlays");
	private static final RegistryObject<Item> AI_TOOL = ITEMS.register(
			"ai_tool", () -> new Item(new Item.Properties().rarity(Rarity.RARE)) {
				@Override
				public boolean isFoil(ItemStack itemStack) {
					return true;
				}
			}
	);
	
	public DebugOverlays() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(modBus);
		if (FMLEnvironment.dist.isClient()) new DebugOverlaysClient().onInitializeClient();
		DataPacket.setup(NETWORK_INSTANCE);
	}
}
