package net.virushd.title.main;

import org.bukkit.plugin.java.JavaPlugin;

public class TitleMain extends JavaPlugin {

	String prefix = "§0[§bTitle§0] §7";
	
	public void onEnable() {

		// load message
		getLogger().info("Plugin enabled!");
	}
}
