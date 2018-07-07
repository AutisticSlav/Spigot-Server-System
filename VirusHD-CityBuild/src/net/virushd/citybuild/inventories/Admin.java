package net.virushd.citybuild.inventories;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import net.virushd.citybuild.main.FileManager;
import net.virushd.core.main.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Admin {

	public static void open(Player p) {
		
		Inventory inv = InventoryAPI.createInventory("&cAdmin Menu", InventoryType.CHEST);
		
		inv.setSlot(0, InventoryAPI.createItem("&cCityBuild Spawn", Arrays.asList("&7Change the CityBuild spawn location."), Material.valueOf(net.virushd.core.main.FileManager.inv_teleporter.getString("Items.Spawn.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				SaveUtils.SaveLocationToFile(FileManager.configF, FileManager.config, "Spawns.CityBuild", p.getLocation().add(-0.5, 0, -0.5));
			}
		});
		
		inv.setSlot(1, InventoryAPI.createItem("&cFarmworld Spawn", Arrays.asList("&7Change the farmworld spawn location."), Material.WHEAT, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				SaveUtils.SaveLocationToFile(FileManager.configF, FileManager.config, "Spawns.Farmworld", p.getLocation().add(-0.5, 0, -0.5));
			}
		});
		
		inv.setSlot(2, InventoryAPI.createItem("&cNether Spawn", Arrays.asList("&7Change the nether spawn location."), Material.NETHERRACK, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				SaveUtils.SaveLocationToFile(FileManager.configF, FileManager.config, "Spawns.Nether", p.getLocation().add(-0.5, 0, -0.5));
			}
		});
		
		inv.open(p);
	}
}
