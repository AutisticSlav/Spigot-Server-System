package net.virushd.core.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import net.virushd.inventory.main.InventoryAPI;

public class SaveUtils {
	
	/*
	 * Locations
	 */
	public static Location GetLocationFromFile(FileConfiguration file, String path) {
		try {
			Location l = new Location(
				Bukkit.getWorld(file.getString(path + ".World")), 
				file.getDouble(path + ".X"),
				file.getDouble(path + ".Y"),
				file.getDouble(path + ".Z"),
				(float) file.getDouble(path + ".Yaw"),
				(float) file.getDouble(path + ".Pitch"));
			return l;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void SaveLocationToFile(File fileF, FileConfiguration file, String path, Location l) {
		file.set(path + ".World", l.getWorld().getName());
		file.set(path + ".X", l.getX());
		file.set(path + ".Y", l.getY());
		file.set(path + ".Z", l.getZ());
		file.set(path + ".Yaw", (double) l.getYaw());
		file.set(path + ".Pitch", (double) l.getPitch());
		SaveFile(fileF, file);
	}
	
	public static void DefaultLocationToFile(FileConfiguration file, String path, Location l) {
		file.addDefault(path + ".World", l.getWorld().getName());
		file.addDefault(path + ".X", l.getX());
		file.addDefault(path + ".Y", l.getY());
		file.addDefault(path + ".Z", l.getZ());
		file.addDefault(path + ".Yaw", (double) l.getYaw());
		file.addDefault(path + ".Pitch", (double) l.getPitch());
	}

	/*
	 * Items
	 */
	public static ItemStack GetItemFromFile(FileConfiguration file, String path) {
		try {
			ItemStack i = InventoryAPI.createItem(
				PlaceHolder.Normal(file.getString(path + ".DisplayName")),
				Arrays.asList(PlaceHolder.Normal(file.getString(path + ".Description"))),
				Material.valueOf(file.getString(path + ".Item")),
				null,
				1);
			return i;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void SaveItemToFile(File fileF, FileConfiguration file, String path, ItemStack item) {
		file.set(path + ".Item", item.getType().toString());
		file.set(path + ".DisplayName", item.getItemMeta().getDisplayName());
		file.set(path + ".Description", item.getItemMeta().getLore().get(0));
		SaveFile(fileF, file);
	}
	
	public static void DefaultItemToFile(FileConfiguration file, String path, ItemStack item) {
		file.addDefault(path + ".Item", item.getType().toString());
		file.addDefault(path + ".DisplayName", item.getItemMeta().getDisplayName());
		file.addDefault(path + ".Description", item.getItemMeta().getLore().get(0));
	}
	
	/*
	 * Scoreboards
	 */
	public static Scoreboard GetScoreboardFromFile(FileConfiguration file, String path, Player p) {
		Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = s.registerNewObjective("AAA", "BBB");
		o.setDisplayName(PlaceHolder.WithPlayer(file.getString(path + ".DisplayName"), p));
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		List<String> lines = file.getStringList(path + ".Lines");

		// fix a scoreboard space bug
		String spaces = "";
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).equals("{Space}")) {
				lines.set(i, spaces);
				spaces += " ";
			}
		}

		for (int i = 0; i < lines.size(); i++) {
			Score sc = o.getScore(PlaceHolder.WithPlayer(lines.get(i), p));
			sc.setScore(lines.size() - i);
		}
		
		return s;
	}
	
	public static void DefaultScoreboardToFile(FileConfiguration file, String path, String DisplayName, List<String> lines) {
		file.addDefault(path + ".DisplayName", DisplayName);
		file.addDefault(path + ".Lines", lines);
	}

	/*
	 * Save a file
	 */
	public static void SaveFile(File file, FileConfiguration conf) {
		try {
			conf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}














