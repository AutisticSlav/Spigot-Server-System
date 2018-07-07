package net.virushd.core.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import net.virushd.coins.inventories.Lottery;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;
import net.virushd.pets.inventories.Pets;

public class Cosmetics {
	
	public static void open(Player p) {
		
		String InventoryDisplayName = PlaceHolder.Normal(FileManager.inv_cosmetics.getString("Inventory.DisplayName"));
		
		Inventory inv = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);
		
		// inv
		int i = 0;
		if (Bukkit.getServer().getPluginManager().getPlugin("VirusHD-Pets") != null) {
			inv.setSlot(i, SaveUtils.GetItemFromFile(FileManager.inv_cosmetics, "Items.Pets"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Pets.open(p);
				}
			});
			i++;
		}
		
		if (Bukkit.getServer().getPluginManager().getPlugin("VirusHD-Hats") != null) {
			inv.setSlot(i, SaveUtils.GetItemFromFile(FileManager.inv_cosmetics, "Items.Hats"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					p.closeInventory();
					p.sendMessage(PlaceHolder.WithPlayer("{Prefix}Coming soon...", p));
				}
			});
			i++;
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("VirusHD-Coins") != null) {
			inv.setSlot(i, SaveUtils.GetItemFromFile(FileManager.inv_cosmetics, "Items.Lottery"), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Lottery.open(p);
				}
			});
		}

		inv.open(p);
	}
}
