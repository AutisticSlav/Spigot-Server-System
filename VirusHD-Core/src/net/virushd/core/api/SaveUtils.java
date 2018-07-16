package net.virushd.core.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.virushd.core.api.PlaceHolder;
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

@Deprecated
public class SaveUtils {

	// locations
	public static Location getLocationFromFile(FileConfiguration file, String path) {
		try {
			return new Location(
					Bukkit.getWorld(file.getString(path + ".World")),
					file.getDouble(path + ".X"),
					file.getDouble(path + ".Y"),
					file.getDouble(path + ".Z"),
					(float) file.getDouble(path + ".Yaw"),
					(float) file.getDouble(path + ".Pitch"));
		} catch (Exception e) {
			return null;
		}
	}

	public static void saveLocationToFile(File fileF, FileConfiguration file, String path, Location l) {
		file.set(path + ".World", l.getWorld().getName());
		file.set(path + ".X", l.getX());
		file.set(path + ".Y", l.getY());
		file.set(path + ".Z", l.getZ());
		file.set(path + ".Yaw", (double) l.getYaw());
		file.set(path + ".Pitch", (double) l.getPitch());
		saveFile(fileF, file);
	}

	public static void defaultLocationToFile(FileConfiguration file, String path, Location l) {
		file.addDefault(path + ".World", l.getWorld().getName());
		file.addDefault(path + ".X", l.getX());
		file.addDefault(path + ".Y", l.getY());
		file.addDefault(path + ".Z", l.getZ());
		file.addDefault(path + ".Yaw", (double) l.getYaw());
		file.addDefault(path + ".Pitch", (double) l.getPitch());
	}

	// items
	public static ItemStack getItemFromFile(FileConfiguration file, String path) {
		return InventoryAPI.createItem(
				PlaceHolder.normal(file.getString(path + ".DisplayName")),
				PlaceHolder.normal(file.getStringList(path + ".Description")),
				Material.valueOf(file.getString(path + ".Item")),
				null,
				1);
	}

	public static void saveItemToFile(File fileF, FileConfiguration file, String path, ItemStack item) {
		file.set(path + ".Item", item.getType().toString());
		file.set(path + ".DisplayName", PlaceHolder.betterColorCode(item.getItemMeta().getDisplayName()));
		file.set(path + ".Description", PlaceHolder.betterColorCode(item.getItemMeta().getLore()));
		saveFile(fileF, file);
	}

	public static void defaultItemToFile(FileConfiguration file, String path, ItemStack item) {
		file.addDefault(path + ".Item", item.getType().toString());
		file.addDefault(path + ".DisplayName", PlaceHolder.betterColorCode(item.getItemMeta().getDisplayName()));
		file.addDefault(path + ".Description", PlaceHolder.betterColorCode(item.getItemMeta().getLore()));
	}

	// scoreboards
	public static Scoreboard getScoreboardFromFile(FileConfiguration file, String path, Player p) {
		Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = s.registerNewObjective("AAA", "BBB");
		o.setDisplayName(PlaceHolder.withPlayer(file.getString(path + ".DisplayName"), p));
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
			Score sc = o.getScore(PlaceHolder.withPlayer(lines.get(i), p));
			sc.setScore(lines.size() - i);
		}

		return s;
	}

	public static void defaultScoreboardToFile(FileConfiguration file, String path, String DisplayName, List<String> lines) {
		file.addDefault(path + ".DisplayName", PlaceHolder.betterColorCode(DisplayName));
		file.addDefault(path + ".Lines",  PlaceHolder.betterColorCode(lines));
	}

	// save a file correctly
	public static void saveFile(File file, FileConfiguration conf) {
		try {
			conf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}














