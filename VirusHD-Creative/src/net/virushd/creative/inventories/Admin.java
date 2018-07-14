package net.virushd.creative.inventories;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import net.virushd.core.main.SaveUtils;
import net.virushd.creative.main.FileManager;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Admin {

	public static void open(Player p) {

		Inventory inv = InventoryAPI.createInventory("&cAdmin Menu", InventoryType.CHEST);

		// change the creative spawn
		inv.setSlot(0, InventoryAPI.createItem("&cCreative Spawn", Arrays.asList("&7Change the Creative spawn location."), Material.valueOf(net.virushd.core.main.FileManager.inv_teleporter.getString("Items.Spawn.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				SaveUtils.saveLocationToFile(FileManager.configF, FileManager.config, "Spawns.Creative", p.getLocation());
			}
		});

		inv.open(p);
	}
}