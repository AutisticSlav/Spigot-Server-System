package net.virushd.troll.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.main.InventoryAPI;

public class Troll {

	// TODO (Troll) inventar machen (hauptteil dieses plugins)
	
	public static void open(Player p) {
		Inventory inv = InventoryAPI.createInventory("", InventoryType.CHEST);
		inv.open(p);
	}
}
