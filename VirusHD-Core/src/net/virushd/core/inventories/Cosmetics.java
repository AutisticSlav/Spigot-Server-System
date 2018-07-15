package net.virushd.core.inventories;

import net.virushd.core.main.CoreMain;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import net.virushd.coins.inventories.Lottery;
import net.virushd.core.main.FileManager;
import net.virushd.core.api.PlaceHolder;
import net.virushd.core.api.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;
import net.virushd.pets.inventories.Pets;

public class Cosmetics {

	public static void open(Player p) {

		String InventoryDisplayName = PlaceHolder.normal(FileManager.inv_cosmetics.getString("Inventory.DisplayName"));

		Inventory inv = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);

		int i = 0;

		// pets
		if (CoreMain.pluginAvailable("VirusHD-Pets")) {
			inv.setSlot(i, SaveUtils.getItemFromFile(FileManager.inv_cosmetics, "Items.Pets"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Pets.open(p);
				}
			});
			i++;
		}

		// hats (coming soon)
		if (CoreMain.pluginAvailable("VirusHD-Hats")) {
			inv.setSlot(i, SaveUtils.getItemFromFile(FileManager.inv_cosmetics, "Items.Hats"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					p.closeInventory();
					p.sendMessage(PlaceHolder.withPlayer("{Prefix}Coming soon...", p));
				}
			});
			i++;
		}

		// lottery
		if (CoreMain.pluginAvailable("VirusHD-Coins")) {
			inv.setSlot(i, SaveUtils.getItemFromFile(FileManager.inv_cosmetics, "Items.Lottery"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Lottery.open(p);
				}
			});
		}

		inv.open(p);
	}
}
