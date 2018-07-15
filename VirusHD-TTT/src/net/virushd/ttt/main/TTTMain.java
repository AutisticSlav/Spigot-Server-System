package net.virushd.ttt.main;

import net.virushd.core.api.Minigame.ArenaMinigame;
import net.virushd.inventory.main.InventoryAPI;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.GameState;
import net.virushd.ttt.commands.TTT;
import net.virushd.ttt.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import net.virushd.ttt.arena.ArenaManager;

import net.virushd.core.api.PlaceHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TTTMain extends ArenaMinigame {

	public static TTTMain main;

	public void onEnable() {

		// instance
		main = this;

		// files
		FileManager.manager();

		// load arenas
		ArenaManager.loadArenas();
		for (Arena arena : ArenaManager.getCompletedArenas()) {
			arena.start();
		}

		// commands
		getCommand("ttt").setExecutor(new TTT());

		// events
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);

		// updater
		Updater.signUpdater();
		Updater.scoreboardUpdater();
		Updater.playerVisibility();

		// register in core
		register();

		// load message
		getLogger().info("Plugin enabled!");
	}

	public void onDisable() {

		// anti sign bug
		for (Sign sign : Updater.updateSigns.keySet()) {
			for (int i = 0; i < 4; i++) {
				try {
					sign.setLine(i, PlaceHolder.sign(FileManager.config.getString("Sign.Lines." + (i)).replace("{Players}", "" + 0), this, Updater.updateSigns.get(sign)));
					sign.update();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void leave(Player p) {
		PlayerManager.leave(p);
	}

	@Override
	public void join(Player p, int id) {
		PlayerManager.join(p, id);
	}

	@Override
	public String normalPlaceholder(String s) {
		s = s.replace("{TTTPrefix}", FileManager.messages.getString("TTTPrefix"));
		return s;
	}

	@Override
	public String signPlaceholder(String s, int id) {
		Arena arena = ArenaManager.getArenaByID(id);
		if (arena != null && arena.isComplete()) {
			int MaxPlayers = FileManager.config.getInt("MaxPlayers");
			String Lobby = FileManager.config.getString("GameStates.Lobby");
			String LobbyFull = FileManager.config.getString("GameStates.LobbyFull");
			String Ingame = FileManager.config.getString("GameStates.Ingame");
			s = s.replace("{Name}", arena.getName());
			if (arena.getGameState() == GameState.LOBBY && arena.getPlayers().size() < MaxPlayers)
				s = s.replace("{GameState}", Lobby);
			if (arena.getGameState() == GameState.LOBBY && arena.getPlayers().size() == MaxPlayers)
				s = s.replace("{GameState}", LobbyFull);
			if (arena.getGameState() == GameState.STARTING || arena.getGameState() == GameState.GAME || arena.getGameState() == GameState.RESTARTING)
				s = s.replace("{GameState}", Ingame);
			s = s.replace("{MaxPlayers}", "" + MaxPlayers);
			s = s.replace("{Players}", "" + arena.getPlayers().size());
			s = PlaceHolder.normal(s);
		}
		return s;
	}

	@Override
	public ItemStack getDefaultItem() {
		return InventoryAPI.createItem("&4TTT", Arrays.asList("&7Finde den Mörder und töte ihn!"), Material.STICK, null, 1);
	}

	@Override
	public int getDefaultSlot() {
		return 14;
	}

	@Override
	public Location getDefaultLocation() {
		return Bukkit.getWorld("world").getSpawnLocation();
	}
}
