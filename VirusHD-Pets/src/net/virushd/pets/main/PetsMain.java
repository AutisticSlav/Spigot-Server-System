package net.virushd.pets.main;

import net.virushd.core.api.SaveUtils;
import net.virushd.pets.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.pets.pet.PetManager;

public class PetsMain extends JavaPlugin {

	public static PetsMain main;

	public void onEnable() {

		// instance
		main = this;

		//files
		FileManager.manager();
		SaveUtils.saveFile(FileManager.petsF, FileManager.pets);

		//Pets
		PetManager.registerPets();

		//events
		Bukkit.getPluginManager().registerEvents(new TargetEvent(), this);
		Bukkit.getPluginManager().registerEvents(new WalkEvent(), this);
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetDamageEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetCombustEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetShootEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetExplodeEvent(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEvent(), this);

		//updater
		Updater.petHearts();
		Updater.petVisibility();

		//load message
		getLogger().info("Plugin enabled!");
	}
}
