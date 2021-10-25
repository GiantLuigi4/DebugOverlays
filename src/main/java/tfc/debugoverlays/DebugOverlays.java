package tfc.debugoverlays;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class DebugOverlays implements ModInitializer {
	@Override
	public void onInitialize() {
		Registry.register(
				Registry.ITEM,
				new ResourceLocation("debugoverlays:ai_tool"),
				new Item(new FabricItemSettings().rarity(Rarity.RARE)) {
					@Override
					public boolean isFoil(ItemStack itemStack) {
						return true;
					}
				}
		);
	}
}
