package net.virushd.creative.main;

import net.virushd.inventory.main.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import net.virushd.creative.commands.Creative;
import net.virushd.creative.events.ChatEvent;
import net.virushd.creative.events.CommandEvent;
import net.virushd.creative.events.PlayerDeathEvent;
import net.virushd.creative.events.QuitEvent;
import net.virushd.creative.events.SignEvent;

import net.virushd.core.api.PlaceHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static net.virushd.core.api.Minigame.*;

public class CreativeMain extends NormalMinigame {

	public static CreativeMain main;

	public void onEnable() {

		// instance
		main = this;

		// files
		FileManager.manager();

		// commands
		getCommand("creative").setExecutor(new Creative());

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
		s = s.replace("{CreativePrefix}", FileManager.messages.getString("CreativePrefix"));
		if (s.contains("{Ideas}")) {
			List<String> ideas = FileManager.sco_creative.getStringList("Ideas");
			s = s.replace("{Ideas}", ideas.get(new Random().nextInt(ideas.size())));
		}
		return s;
	}

	@Override
	public String signPlaceholder(String s) {
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String Lobby = FileManager.config.getString("GameStates.Lobby");
		String LobbyFull = FileManager.config.getString("GameStates.LobbyFull");
		s = s.replace("{Name}", "Creative");
		if (PlayerManager.getPlayers().size() < MaxPlayers) s = s.replace("{GameState}", Lobby);
		if (PlayerManager.getPlayers().size() == MaxPlayers) s = s.replace("{GameState}", LobbyFull);
		s = s.replace("{MaxPlayers}", "" + MaxPlayers);
		s = s.replace("{Players}", "" + PlayerManager.getPlayers().size());
		s = PlaceHolder.normal(s);
		return s;
	}

	@Override
	public ItemStack getDefaultItem() {
		return InventoryAPI.createItem("&6Creative", Arrays.asList("&7Unentlich Ressourcen &c:O&7!"), Material.DIAMOND_BLOCK, null, 1);
	}

	@Override
	public int getDefaultSlot() {
		return 4;
	}

	@Override
	public Location getDefaultLocation() {
		return Bukkit.getWorld("world").getSpawnLocation();
	}
}
