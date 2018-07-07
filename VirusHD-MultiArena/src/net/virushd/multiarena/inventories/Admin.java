package net.virushd.multiarena.inventories;

import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;
import net.virushd.multiarena.arena.Arena;
import net.virushd.multiarena.arena.ArenaManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
		import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class Admin {

	public static void open(Player p) {
		// FIXME Admin menu
		Inventory inv = InventoryAPI.createInventory("&cAdmin Menu", InventoryType.CHEST);
		//Inventory create = InventoryAPI.createInventory("&cAdmin Menu", InventoryType.CHEST);

		inv.setSlot(53, InventoryAPI.createItem("&cCreate Arena", Arrays.asList("&7Create an arena"), Material.WORKBENCH, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				// arenen erstellen
			}
		});

		int i = 0;
		for (Arena a : ArenaManager.getArenas()) {
			ItemStack item = InventoryAPI.createItem("&c" + a.getName(), Arrays.asList("Edit the arena with the ID " + a.getID() + "."), Material.INK_SACK, null, 1);
			item.setDurability((short) 13);
			inv.setSlot(i, item, new ItemListener() {
				@Override
				public void onItemClick(Player player) {
					openArenaInventory(p, a, inv);
				}
			});
			// display if the arena is complete
		}

		inv.open(p);
	}

	private static void openArenaInventory(Player p, Arena a, Inventory original) {

		Inventory arena = InventoryAPI.createInventory("&cArena: &7" + a.getName(), InventoryType.HOPPER);

		arena.setSlot(0, InventoryAPI.createItem("&cDelete Arena", Arrays.asList("&7Delete the arena (&4CANNOT BE UNDONE!&7)."), Material.BARRIER, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				ArenaManager.deleteArena(a.getID());
			}
		});

		arena.setSlot(1, InventoryAPI.createItem("&cAdd Spawn", Arrays.asList("&7Add an item."), Material.valueOf(net.virushd.core.main.FileManager.inv_teleporter.getString("Items.Spawn.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				a.addSpawn(p.getLocation().add(-0.5, 0, -0.5));
				ArenaManager.saveArena(a.getID());
			}
		});

		arena.setSlot(1, InventoryAPI.createItem("&cSet Lobby", Arrays.asList("&7Set the lobby."), Material.COMPASS, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				a.setLobby(p.getLocation().add(-0.5, 0, -0.5));
				ArenaManager.saveArena(a.getID());
			}
		});

		arena.setSlot(1, InventoryAPI.createItem("&cDelete Spawns", Arrays.asList("&7Delete all spawns of this arena (&4CANNOT BE UNDONE!&7)."), Material.BARRIER, null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				a.deleteSpawns();
				ArenaManager.saveArena(a.getID());
			}
		});
	}
}
