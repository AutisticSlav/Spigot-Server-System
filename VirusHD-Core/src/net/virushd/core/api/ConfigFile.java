package net.virushd.core.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

	private String name;
	private File file;
	private FileConfiguration config;
	private FileType type;

	// constructor
	@SuppressWarnings("static-access")
	public ConfigFile(String name, FileType type, Plugin pl) {
		this.name = name;
		this.type = type;
		file = new File("plugins/" + pl.getName() + getTypeDirectory(type), name + ".yml");
		config = new YamlConfiguration().loadConfiguration(file);
		config.options().copyDefaults(true);
		save();
	}

	// save the file
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// add a default directly
	public void addDefault(String path, Object value) {
		config.addDefault(path, value);
	}

	// set a value directly
	public void set(String path, Object value) {
		config.set(path, value);
	}

	// get the name
	public String getName() {
		return name;
	}

	// get the file
	public File getFile() {
		return file;
	}

	// get the configuration
	public FileConfiguration getConfig() {
		return config;
	}

	// get the type
	public FileType getType() {
		return type;
	}

	// get the directory that depens on the type
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
