package net.virushd.core.main;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import net.virushd.inventory.main.InventoryAPI;

public class FileManager {

	public static File configF;
	public static FileConfiguration config;
	public static File messagesF;
	public static FileConfiguration messages;
	public static File ranksF;
	public static FileConfiguration ranks;
	public static File locationsF;
	public static FileConfiguration locations;
	public static File inv_teleporterF;
	public static FileConfiguration inv_teleporter;
	public static File inv_cosmeticsF;
	public static FileConfiguration inv_cosmetics;
	public static File itm_teleporterF;
	public static FileConfiguration itm_teleporter;
	public static File itm_hideF;
	public static FileConfiguration itm_hide;
	public static File itm_cosmeticsF;
	public static FileConfiguration itm_cosmetics;
	public static File sco_lobbyF;
	public static FileConfiguration sco_lobby;
	public static File itm_inventoryF;
	public static FileConfiguration itm_inventory;

	@SuppressWarnings("static-access")
	public static void manager() {

		// messages
		messagesF = new File("plugins/VirusHD-Core", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("Prefix", "&0[&4VirusHD.net&0] &7");
		messages.addDefault("ChatFormat", "&0[&3Chat&0] {DisplayName}&0:&7 {Message}");
		messages.addDefault("Messages.Modt", "{Prefix}Wilkommen &c{DisplayName} &7auf dem Server &cVirusHD.net&7."
				+ "\n&7Tippe &c/lobby regeln &7um die Regeln zu sehen."
				+ "\n&7Tippe &c/lobby commands &7um eine Liste der wichtigsten Commands zu sehen."
				+ "\n&7Tippe &clobby /hilfe &7um eine Hilfe zu sehen.");
		messages.addDefault("Messages.UnknownCommand", "{Prefix}&cFehler: &4Diesen Command gibt es nicht!");
		messages.addDefault("Messages.LobbyUsage", "&7»->--------------<-« &cUsage &7»->--------------<-«"
				+ "\n&c - /lobby &7Teleportiert dich in die Lobby."
				+ "\n&c - /lobby hilfe &7Die Hilfe."
				+ "\n&c - /lobby commands &7Eine Liste der wichtigsten Commands."
				+ "\n&c - /lobby regeln &7Die Regeln.");
		messages.addDefault("Messages.AdminUsage", "&7»->--------------<-« &cUsage &7»->--------------<-«"
				+ "\n&c - /admin &7Aktiviert/Deaktiviert den Admin-Modus."
				+ "\n&c - /admin menu &7Öffnet ein Menu mit Einstellungen (In den Minigames: &c/<Minigame>&7.");
		messages.addDefault("Messages.NoPerm", "{Prefix}&cFehler: &4Du hast keine Rechte diesen Befehl auszuführen.");
		messages.addDefault("Messages.Reload", "&cDu wurdest gekickt, da der Server neu startet.");
		messages.addDefault("Messages.ErrorKick", "&cDu wurdest wegen einem Fehler gekickt. Bitte joine nochmal.");
		messages.addDefault("Messages.AdminMode", "{Prefix}Du bist nun im Admin-Modus.");
		messages.addDefault("Messages.NotInMode", "{Prefix}&cFehler: &4Du solltest diesen Command jetzt nicht benutzen.");
		messages.addDefault("Messages.ActionBar", Arrays.asList("&cWebsite&7: virushd.net", "&cYouTube&7: youtube.com/channel/UCttMxBcMULez8Ij_JtmBu7A", "&cInstagram&7: xvirushdx", "&cTeamSpeak&7: virushd.net"));
		messages.addDefault("Messages.Help", "");
		messages.addDefault("Messages.Help", "&7»->--------------<-« &cDie Hilfe &7»->--------------<-«"
				+ "\n&c - &7Einfach auf den Kompass klicken und dann ein Spielmodus wählen.."
				+ "\n&c - &7In den einzelnen Spielmodi gibt es auch noch eine Hilfe."
				+ "\n&c - &7Auf dem Server gibt es auch Events, Coins, Pets usw... Also viel spass auf dem Server.");
		messages.addDefault("Messages.Commands", "&7»->--------------<-« &cDie Commands &7»->--------------<-«"
				+ "\n&c - /lobby &7Der Lobby command."
				+ "\n&c - /msg &7Mit einem Spieler chatten."
				+ "\n&c - /coins &7Der Coins command.");
		messages.addDefault("Messages.Rules", "&7»->--------------<-« &cDie Regeln &7»->--------------<-«"
				+ "\n&c - &7Fair spielen."
				+ "\n&c - &7Spam ist untersagt."
				+ "\n&c - &7Nicht Hacken."
				+ "\n&c - &7Bei Fragen sich an den Support wenden."
				+ "\n&c - &7Bei Bugs einen Screen machen und ihn mit einer genauen Schilderung dem Admin schicken.");
		messages.addDefault("Quit.Message", "{Prefix}{DisplayName}&7 hat das Spiel verlassen");
		messages.addDefault("Join.Message", "{Prefix}{DisplayName}&7 hat das Spiel betreten");
		messages.addDefault("Join.Title", "&4Wilkommen");
		messages.addDefault("Join.SubTitle", "&c{PlayerName} &7auf dem Server &cVirusHD.net");
		messages.addDefault("TabTitle.Header", "&4VirusHD.net &7- &cLobby\n&7-----------------------------------");
		messages.addDefault("TabTitle.Footer", "&7-----------------------------------\n&cViel Spass!");
		messages.options().copyDefaults(true);
		SaveUtils.saveFile(messagesF, messages);

		// itm_cosmetics
		itm_cosmeticsF = new File("plugins/VirusHD-Core/items", "cosmetics.yml");
		itm_cosmetics = new YamlConfiguration().loadConfiguration(itm_cosmeticsF);
		SaveUtils.defaultItemToFile(itm_cosmetics, "Cosmetics", InventoryAPI.createItem("&cCosmetics", Arrays.asList("&7Hüte, Pets, Lotto..."), Material.CHEST, null, 1));
		itm_cosmetics.options().copyDefaults(true);
		SaveUtils.saveFile(itm_cosmeticsF, itm_cosmetics);

		// itm_hide
		itm_hideF = new File("plugins/VirusHD-Core/items", "hide.yml");
		itm_hide = new YamlConfiguration().loadConfiguration(itm_hideF);
		SaveUtils.defaultItemToFile(itm_cosmetics, "Hide", InventoryAPI.createItem("&cSpieler: {HideMode}", Arrays.asList("&7Verstecke andere Spieler."), Material.BLAZE_ROD, null, 1));
		itm_hide.addDefault("HideMode.off", "&aSichtbar");
		itm_hide.addDefault("HideMode.on", "&4Unsichtbar");
		itm_hide.options().copyDefaults(true);
		SaveUtils.saveFile(itm_hideF, itm_hide);

		// itm_teleporter
		itm_teleporterF = new File("plugins/VirusHD-Core/items", "teleporter.yml");
		itm_teleporter = new YamlConfiguration().loadConfiguration(itm_teleporterF);
		SaveUtils.defaultItemToFile(itm_teleporter, "Teleporter", InventoryAPI.createItem("&cTeleporter", Arrays.asList("&7Dieses Ding beamt dich weg &cxD&7."), Material.COMPASS, null, 1));
		itm_teleporter.options().copyDefaults(true);
		SaveUtils.saveFile(itm_teleporterF, itm_teleporter);

		// inv_teleporter
		inv_teleporterF = new File("plugins/VirusHD-Core/inventories", "teleporter.yml");
		inv_teleporter = new YamlConfiguration().loadConfiguration(inv_teleporterF);
		inv_teleporter.addDefault("Inventory.DisplayName", "&cTeleporter");
		SaveUtils.defaultItemToFile(inv_teleporter, "Items.Spawn", InventoryAPI.createItem("&cSpawn", Arrays.asList("&7Zurück zum Spawn."), Material.ENDER_PEARL, null, 1));
		SaveUtils.defaultItemToFile(inv_teleporter, "Items.CityBuild", InventoryAPI.createItem("&6CityBuild", Arrays.asList("&7CityBuild, wer kennts nicht?"), Material.BRICK, null, 1));
		SaveUtils.defaultItemToFile(inv_teleporter, "Items.Creative", InventoryAPI.createItem("&6Creative", Arrays.asList("&7Unentlich Ressourcen &c:O&7!"), Material.DIAMOND_BLOCK, null, 1));
		SaveUtils.defaultItemToFile(inv_teleporter, "Items.TTT", InventoryAPI.createItem("&4TTT", Arrays.asList("&7Finde den Mörder und töte ihn!"), Material.STICK, null, 1));
		inv_teleporter.addDefault("Items.Spawn.Slot", 13);
		inv_teleporter.addDefault("Items.CityBuild.Slot", 22);
		inv_teleporter.addDefault("Items.Creative.Slot", 4);
		inv_teleporter.addDefault("Items.TTT.Slot", 14);
		inv_teleporter.addDefault("Background.0", 15);
		inv_teleporter.addDefault("Background.1", 14);
		inv_teleporter.addDefault("Background.2", 15);
		inv_teleporter.addDefault("Background.3", 14);
		inv_teleporter.addDefault("Background.4", 14);
		inv_teleporter.addDefault("Background.5", 14);
		inv_teleporter.addDefault("Background.6", 15);
		inv_teleporter.addDefault("Background.7", 14);
		inv_teleporter.addDefault("Background.8", 15);
		inv_teleporter.addDefault("Background.9", 14);
		inv_teleporter.addDefault("Background.10", 15);
		inv_teleporter.addDefault("Background.11", 15);
		inv_teleporter.addDefault("Background.12", 14);
		inv_teleporter.addDefault("Background.13", 15);
		inv_teleporter.addDefault("Background.14", 14);
		inv_teleporter.addDefault("Background.15", 15);
		inv_teleporter.addDefault("Background.16", 15);
		inv_teleporter.addDefault("Background.17", 14);
		inv_teleporter.addDefault("Background.18", 15);
		inv_teleporter.addDefault("Background.19", 14);
		inv_teleporter.addDefault("Background.20", 15);
		inv_teleporter.addDefault("Background.21", 14);
		inv_teleporter.addDefault("Background.22", 14);
		inv_teleporter.addDefault("Background.23", 14);
		inv_teleporter.addDefault("Background.24", 15);
		inv_teleporter.addDefault("Background.25", 14);
		inv_teleporter.addDefault("Background.26", 15);
		inv_teleporter.options().copyDefaults(true);
		SaveUtils.saveFile(inv_teleporterF, inv_teleporter);

		// inv_cosmetics
		inv_cosmeticsF = new File("plugins/VirusHD-Core/inventories", "cosmetics.yml");
		inv_cosmetics = new YamlConfiguration().loadConfiguration(inv_cosmeticsF);
		inv_cosmetics.addDefault("Inventory.DisplayName", "&cCosmetics");
		SaveUtils.defaultItemToFile(inv_cosmetics, "Items.Pets", InventoryAPI.createItem("&cPets", Arrays.asList("&7Pets sind cute."), Material.MONSTER_EGG, null, 1));
		SaveUtils.defaultItemToFile(inv_cosmetics, "Items.Hats", InventoryAPI.createItem("&cHüte", Arrays.asList("&7Kaufe dir Hüte."), Material.SKULL_ITEM, null, 1));
		SaveUtils.defaultItemToFile(inv_cosmetics, "Items.Lottery", InventoryAPI.createItem("&cLotto", Arrays.asList("&7Es gibt viele Preis zu gewinnen."), Material.PAPER, null, 1));
		inv_cosmetics.options().copyDefaults(true);
		SaveUtils.saveFile(inv_cosmeticsF, inv_cosmetics);

		// locations
		locationsF = new File("plugins/VirusHD-Core", "locations.yml");
		locations = new YamlConfiguration().loadConfiguration(locationsF);
		SaveUtils.defaultLocationToFile(locations, "CityBuild", Bukkit.getWorld("world").getSpawnLocation());
		SaveUtils.defaultLocationToFile(locations, "Creative", Bukkit.getWorld("world").getSpawnLocation());
		SaveUtils.defaultLocationToFile(locations, "TTT", Bukkit.getWorld("world").getSpawnLocation());
		locations.options().copyDefaults(true);
		SaveUtils.saveFile(locationsF, locations);

		// config 
		configF = new File("plugins/VirusHD-Core", "config.yml");
		config = new YamlConfiguration().loadConfiguration(configF);
		SaveUtils.defaultLocationToFile(config, "Spawns.Lobby", Bukkit.getWorld("world").getSpawnLocation());
		config.addDefault("DebugMode", false);
		config.options().copyDefaults(true);
		SaveUtils.saveFile(configF, config);

		// ranks
		ranksF = new File("plugins/VirusHD-Core", "ranks.yml");
		ranks = new YamlConfiguration().loadConfiguration(ranksF);
		ranks.addDefault("Ranks.DisplayNames", Arrays.asList("&eMember", "&fYou&4Tuber", "&4V&9I&aP", "&aSupporter", "&4Admin"));
		ranks.addDefault("Ranks.Permissions", Arrays.asList("virushd.rank.member", "virushd.rank.youtuber", "virushd.rank.vip", "virushd.rank.supporter", "*"));
		ranks.options().copyDefaults(true);
		SaveUtils.saveFile(ranksF, ranks);

		// sco_lobby
		sco_lobbyF = new File("plugins/VirusHD-Core/scoreboards", "lobby.yml");
		sco_lobby = new YamlConfiguration().loadConfiguration(sco_lobbyF);
		SaveUtils.defaultScoreboardToFile(sco_lobby, "Lobby", "&4VirusHD.net &7- &cLobby", Arrays.asList("{Space}", "&cSpieler Online:", "&7{OnlinePlayers} &c/ &7{MaxOnlinePlayers}", "{Space}", "&cRang:", "&7{Rank}", "{Space}", "&cCoins:", "&7{Coins}"));
		sco_lobby.options().copyDefaults(true);
		SaveUtils.saveFile(sco_lobbyF, sco_lobby);

		// itm_inventory
		itm_inventoryF = new File("plugins/VirusHD-Core/items", "inventory.yml");
		itm_inventory = new YamlConfiguration().loadConfiguration(itm_inventoryF);
		SaveUtils.defaultItemToFile(itm_inventory, "Forward", InventoryAPI.createItem("&cWeiter", Arrays.asList("&7Gehe weiter."), Material.SIGN, null, 1));
		SaveUtils.defaultItemToFile(itm_inventory, "Backward", InventoryAPI.createItem("&cZurück", Arrays.asList("&7Gehe zurück."), Material.SIGN, null, 1));
		SaveUtils.defaultItemToFile(itm_inventory, "Leave", InventoryAPI.createItem("&cMenu verlassen", Arrays.asList("&7Verlasse das Menu."), Material.IRON_DOOR, null, 1));
		itm_inventory.options().copyDefaults(true);
		SaveUtils.saveFile(itm_inventoryF, itm_inventory);
	}
}