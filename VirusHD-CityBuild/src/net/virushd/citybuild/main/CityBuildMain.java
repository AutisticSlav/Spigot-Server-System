package net.virushd.citybuild.main;

import net.virushd.core.api.Minigame.NormalMinigame;
import net.virushd.inventory.main.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.virushd.citybuild.commands.CityBuild;
import net.virushd.citybuild.events.ChatEvent;
import net.virushd.citybuild.events.CommandEvent;
import net.virushd.citybuild.events.PlayerDeathEvent;
import net.virushd.citybuild.events.QuitEvent;
import net.virushd.citybuild.events.SignEvent;
import net.virushd.core.api.PlaceHolder;

import java.util.Arrays;

public class CityBuildMain extends NormalMinigame {

	public static CityBuildMain main;

	public void onEnable() {

		// instance
		main = this;

		// files
		FileManager.manager();

		// commands
		getCommand("citybuild").setExecutor(new CityBuild());

		// events
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);

		// updater
		Updater.scoreboardUpdater();
		Updater.playerVisibility();
		Updater.signUpdater();

		// register in core
		register();

		// load message
		getLogger().info("Plugin enabled!");
	}

	public void onDisable() {

		// anti sign bug
		for (Sign signs : Updater.updateSigns) {
			for (int i = 0; i < 4; i++) {
				signs.setLine(i, PlaceHolder.sign(FileManager.config.getString("Sign.Lines." + (i)).replace("{Players}", "" + 0), this));
				signs.update();
			}
		}
	}

	// get the worldguard plugin
	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (!(plugin instanceof WorldGuardPlugin)) {
			System.out.println(PlaceHolder.normal("{CityBuildPrefix}&4Please install WorldGuard!"));
			return null;
		}

		return (WorldGuardPlugin) plugin;
	}

	@Override
	public void leave(Player p) {
		PlayerManager.leave(p);
	}

	@Override
	public void join(Player p) {
		PlayerManager.join(p);
	}

	@Override
	public String normalPlaceholder(String s) {
		s = s.replace("{CityBuildPrefix}", FileManager.messages.getString("CityBuildPrefix"));
		return s;
	}

	@Override
	public String signPlaceholder(String s) {
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String Lobby = FileManager.config.getString("GameStates.Lobby");
		String LobbyFull = FileManager.config.getString("GameStates.LobbyFull");
		s = s.replace("{Name}", "CityBuild");
		if (PlayerManager.getPlayers().size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
		if (PlayerManager.getPlayers().size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
		s = s.replace("{MaxPlayers}", "" + MaxPlayers);
		s = s.replace("{Players}", "" + PlayerManager.getPlayers().size());
		s = PlaceHolder.normal(s);
		return s;
	}

	@Override
	public ItemStack getDefaultItem() {
		return InventoryAPI.createItem("&6CityBuild", Arrays.asList("&7CityBuild, wer kennts nicht?"), Material.BRICK, null, 1);
	}

	@Override
	public int getDefaultSlot() {
		return 22;
	}

	@Override
	public Location getDefaultLocation() {
		return Bukkit.getWorld("world").getSpawnLocation();
	}
}
