package net.virushd.troll.main;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	public static File messagesF;
	public static FileConfiguration messages;
	public static File inv_trollF;
	public static FileConfiguration inv_troll;
	
	@SuppressWarnings("static-access")
	public static void Manager() {
		
		// messages
		messagesF = new File("plugins/VirusHD-Troll", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("TrollPrefix", "&0[&4Troll&0] &7");
		messages.options().copyDefaults(true);
		try {
			messages.save(messagesF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// inv_troll
		inv_trollF = new File("plugins/VirusHD-Troll/inventories", "inventory.yml");
		inv_troll = new YamlConfiguration().loadConfiguration(inv_trollF);
		inv_troll.addDefault("Inventory.DisplayName", "&cTroll Menu");
		inv_troll.options().copyDefaults(true);
		try {
			inv_troll.save(inv_trollF);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
