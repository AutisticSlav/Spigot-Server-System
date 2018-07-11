package net.virushd.pets.main;

import net.virushd.core.main.SaveUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.pets.events.PetCombustEvent;
import net.virushd.pets.events.PetShootEvent;
import net.virushd.pets.events.JoinEvent;
import net.virushd.pets.events.PetDamageEvent;
import net.virushd.pets.events.PetExplodeEvent;
import net.virushd.pets.events.QuitEvent;
import net.virushd.pets.events.TargetEvent;
import net.virushd.pets.events.WalkEvent;
import net.virushd.pets.pet.PetManager;

public class PetsMain extends JavaPlugin{
	
	public static PetsMain main;
	
	public void onEnable() {
		
		// instance
		main = this;
		
		//files
		FileManager.Manager();
		SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
		
		//Pets
		PetManager.registerPets();
		
		//commands
//		getCommand("pet").setExecutor(new Pets());
		
		//events
		Bukkit.getPluginManager().registerEvents(new TargetEvent(), this);
		Bukkit.getPluginManager().registerEvents(new WalkEvent(), this);
		Bukkit.getPluginManager().registerEvents(new JoinEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetDamageEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetCombustEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetShootEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PetExplodeEvent(), this);
		
		//updater
		Updater.PetHearts();
		Updater.PetVisibility();
		
		//load message
		getLogger().info("Plugin enabled!");
	}
}
