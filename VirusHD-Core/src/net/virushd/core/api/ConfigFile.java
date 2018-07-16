package net.virushd.core.api;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

	private File file;
	private FileConfiguration config;
	private FileType type;

	@SuppressWarnings("static-access")
	public ConfigFile(String name, FileType type, Plugin pl) {
		this.type = type;
		file = new File("plugins/" + pl.getName() + getTypeDirectory(type) , name + ".yml");
		config = new YamlConfiguration().loadConfiguration(file);
		config.options().copyDefaults(true);
	}

	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getFile() {
		return file;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public FileType getType() {
		return type;
	}

	private String getTypeDirectory(FileType type) {
		String d = "";
		if (type != FileType.NORMAL) {
			d = "/" + type.toString().toLowerCase();
		}

		return d;
	}

	public enum FileType {
		NORMAL, ITEMS, INVENTORIES, SCOREBOARDS
	}
}
