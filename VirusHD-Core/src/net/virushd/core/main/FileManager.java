package net.virushd.core.main;

import net.virushd.core.api.ConfigFile;
import net.virushd.core.api.ConfigFile.FileType;
import net.virushd.core.api.Serializer;
import net.virushd.core.api.Utils.SimpleScoreboard;
import net.virushd.inventory.main.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class FileManager {

	private static ArrayList<ConfigFile> files = new ArrayList<>();

	// create all files
	public static void initFiles() {

		// config
		ConfigFile nor_config = new ConfigFile("config", FileType.NORMAL, CoreMain.main);
		nor_config.addDefault("Spawns.Lobby", Serializer.serializeLocation(Bukkit.getWorld("world").getSpawnLocation()));
		nor_config.addDefault("DebugMode", false);
		nor_config.save();
		files.add(nor_config);

		// messages
		ConfigFile nor_messages = new ConfigFile("config", FileType.NORMAL, CoreMain.main);
		nor_messages.addDefault("Prefix", "&0[&4VirusHD.net&0] &7");
		nor_messages.addDefault("ChatFormat", "&0[&3Chat&0] {DisplayName}&0:&7 {Message}");
		nor_messages.addDefault("Messages.Modt", "{Prefix}Wilkommen &c{DisplayName} &7auf dem Server &cVirusHD.net&7."
				+ "\n&7Tippe &c/lobby regeln &7um die Regeln zu sehen."
				+ "\n&7Tippe &c/lobby commands &7um eine Liste der wichtigsten Commands zu sehen."
				+ "\n&7Tippe &clobby /hilfe &7um eine Hilfe zu sehen.");
		nor_messages.addDefault("Messages.UnknownCommand", "{Prefix}&cFehler: &4Diesen Command gibt es nicht!");
		nor_messages.addDefault("Messages.LobbyUsage", "&7»->--------------<-« &cUsage &7»->--------------<-«"
				+ "\n&c - /lobby &7Teleportiert dich in die Lobby."
				+ "\n&c - /lobby hilfe &7Die Hilfe."
				+ "\n&c - /lobby commands &7Eine Liste der wichtigsten Commands."
				+ "\n&c - /lobby regeln &7Die Regeln.");
		nor_messages.addDefault("Messages.AdminUsage", "&7»->--------------<-« &cUsage &7»->--------------<-«"
				+ "\n&c - /admin &7Aktiviert/Deaktiviert den Admin-Modus."
				+ "\n&c - /admin menu &7Öffnet ein Menu mit Einstellungen (In den Minigames: &c/<Minigame>&7.");
		nor_messages.addDefault("Messages.NoPerm", "{Prefix}&cFehler: &4Du hast keine Rechte diesen Befehl auszuführen.");
		nor_messages.addDefault("Messages.Reload", "&cDu wurdest gekickt, da der Server neu startet.");
		nor_messages.addDefault("Messages.ErrorKick", "&cDu wurdest wegen einem Fehler gekickt. Bitte joine nochmal.");
		nor_messages.addDefault("Messages.AdminMode", "{Prefix}Du bist nun im Admin-Modus.");
		nor_messages.addDefault("Messages.NotInMode", "{Prefix}&cFehler: &4Du solltest diesen Command jetzt nicht benutzen.");
		nor_messages.addDefault("Messages.ActionBar", Arrays.asList("&cWebsite&7: virushd.net", "&cYouTube&7: youtube.com/channel/UCttMxBcMULez8Ij_JtmBu7A", "&cInstagram&7: xvirushdx", "&cTeamSpeak&7: virushd.net"));
		nor_messages.addDefault("Messages.Help", "");
		nor_messages.addDefault("Messages.Help", "&7»->--------------<-« &cDie Hilfe &7»->--------------<-«"
				+ "\n&c - &7Einfach auf den Kompass klicken und dann ein Spielmodus wählen.."
				+ "\n&c - &7In den einzelnen Spielmodi gibt es auch noch eine Hilfe."
				+ "\n&c - &7Auf dem Server gibt es auch Events, Coins, Pets usw... Also viel spass auf dem Server.");
		nor_messages.addDefault("Messages.Commands", "&7»->--------------<-« &cDie Commands &7»->--------------<-«"
				+ "\n&c - /lobby &7Der Lobby command."
				+ "\n&c - /msg &7Mit einem Spieler chatten."
				+ "\n&c - /coins &7Der Coins command.");
		nor_messages.addDefault("Messages.Rules", "&7»->--------------<-« &cDie Regeln &7»->--------------<-«"
				+ "\n&c - &7Fair spielen."
				+ "\n&c - &7Spam ist untersagt."
				+ "\n&c - &7Nicht Hacken."
				+ "\n&c - &7Bei Fragen sich an den Support wenden."
				+ "\n&c - &7Bei Bugs einen Screen machen und ihn mit einer genauen Schilderung dem Admin schicken.");
		nor_messages.addDefault("Quit.Message", "{Prefix}{DisplayName}&7 hat das Spiel verlassen");
		nor_messages.addDefault("Join.Message", "{Prefix}{DisplayName}&7 hat das Spiel betreten");
		nor_messages.addDefault("Join.Title", "&4Wilkommen");
		nor_messages.addDefault("Join.SubTitle", "&c{PlayerName} &7auf dem Server &cVirusHD.net");
		nor_messages.addDefault("TabTitle.Header", "&4VirusHD.net &7- &cLobby\n&7-----------------------------------");
		nor_messages.addDefault("TabTitle.Footer", "&7-----------------------------------\n&cViel Spass!");
		nor_messages.save();
		files.add(nor_messages);

		// locations
		files.add(new ConfigFile("locations", FileType.NORMAL, CoreMain.main));

		// ranks
		ConfigFile nor_ranks = new ConfigFile("ranks", FileType.NORMAL, CoreMain.main);
		nor_ranks.addDefault("Ranks.DisplayNames", Arrays.asList("&eMember", "&fYou&4Tuber", "&4V&9I&aP", "&aSupporter", "&4Admin"));
		nor_ranks.addDefault("Ranks.Permissions", Arrays.asList("virushd.rank.member", "virushd.rank.youtuber", "virushd.rank.vip", "virushd.rank.supporter", "*"));
		nor_ranks.save();
		files.add(nor_ranks);

		// cosmetics item
		ConfigFile itm_cosmetics = new ConfigFile("cosmetics", FileType.ITEMS, CoreMain.main);
		itm_cosmetics.addDefault("Cosmetics", Serializer.serializeItem(InventoryAPI.createItem(
				"&cCosmetics", Arrays.asList("&7Hüte, Pets, Lotto..."), Material.CHEST, null, 1
		)));
		itm_cosmetics.save();
		files.add(itm_cosmetics);

		// inventory items
		ConfigFile itm_inventory = new ConfigFile("inventory", FileType.ITEMS, CoreMain.main);
		itm_inventory.addDefault("Forward", Serializer.serializeItem(InventoryAPI.createItem(
				"&cWeiter", Arrays.asList("&7Gehe weiter."), Material.SIGN, null, 1
		)));
		itm_inventory.addDefault("Backward", Serializer.serializeItem(InventoryAPI.createItem(
				"&cZurück", Arrays.asList("&7Gehe zurück."), Material.SIGN, null, 1
		)));
		itm_inventory.addDefault("Leave", Serializer.serializeItem(InventoryAPI.createItem(
				"&cMenu verlassen", Arrays.asList("&7Verlasse das Menu."), Material.IRON_DOOR, null, 1
		)));
		itm_inventory.save();
		files.add(itm_inventory);

		// hide item
		ConfigFile itm_hide = new ConfigFile("hide", FileType.ITEMS, CoreMain.main);
		itm_hide.addDefault("Hide", Serializer.serializeItem(InventoryAPI.createItem(
				"&cSpieler: {HideMode}", Arrays.asList("&7Verstecke andere Spieler."), Material.BLAZE_ROD, null, 1
		)));
		itm_hide.addDefault("HideMode.off", "&aSichtbar");
		itm_hide.addDefault("HideMode.on", "&4Unsichtbar");
		itm_hide.save();
		files.add(itm_hide);

		// teleporter item
		ConfigFile itm_teleporter = new ConfigFile("teleporter", FileType.ITEMS, CoreMain.main);
		itm_teleporter.addDefault("Teleporter", Serializer.serializeItem(InventoryAPI.createItem(
				"&cTeleporter", Arrays.asList("&7Dieses Ding beamt dich weg &cxD&7."), Material.COMPASS, null, 1
		)));
		itm_teleporter.save();
		files.add(itm_teleporter);

		// teleporter inventory
		ConfigFile inv_teleporter = new ConfigFile("telporter", FileType.INVENTORIES, CoreMain.main);
		inv_teleporter.addDefault("Inventory.DisplayName", "&cTeleporter");
		inv_teleporter.addDefault("Items.Spawn", Serializer.serializeItem(InventoryAPI.createItem(
				"&cSpawn", Arrays.asList("&7Zurück zum Spawn."), Material.ENDER_PEARL, null, 1
		)));
		inv_teleporter.addDefault("Items.Spawn.Slot", 13);
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
		inv_teleporter.save();
		files.add(inv_teleporter);

		// cosmetics inventory
		ConfigFile inv_cosmetics = new ConfigFile("cosmetics", FileType.INVENTORIES, CoreMain.main);
		inv_cosmetics.addDefault("Inventory.DisplayName", "&cCosmetics");
		inv_cosmetics.addDefault("Items.Pets", Serializer.serializeItem(InventoryAPI.createItem(
				"&cPets", Arrays.asList("&7Pets sind cute."), Material.MONSTER_EGG, null, 1
		)));
		inv_cosmetics.addDefault("Items.Hats", Serializer.serializeItem(InventoryAPI.createItem(
				"&cHüte", Arrays.asList("&7Kaufe dir Hüte."), Material.SKULL_ITEM, null, 1
		)));
		inv_cosmetics.addDefault("Items.Lottery", Serializer.serializeItem(InventoryAPI.createItem(
				"&cLotto", Arrays.asList("&7Es gibt viele Preis zu gewinnen."), Material.PAPER, null, 1
		)));
		inv_cosmetics.save();
		files.add(inv_cosmetics);

		// lobby scoreboard
		ConfigFile sco_lobby = new ConfigFile("lobby", FileType.SCOREBOARDS, CoreMain.main);
		sco_lobby.addDefault("Lobby", SimpleScoreboard.serialize(new SimpleScoreboard(
				"&4VirusHD.net &7- &cLobby",
				Arrays.asList("{Space}", "&cSpieler Online:", "&7{OnlinePlayers} &c/ &7{MaxOnlinePlayers}", "{Space}", "&cRang:", "&7{Rank}", "{Space}", "&cCoins:", "&7{Coins}")
		)));
		sco_lobby.save();
		files.add(sco_lobby);
	}

	// get a file
	public static ConfigFile getFile(String name, FileType type) {
		for (ConfigFile file : files) {
			if (file.getName().equals(name) && file.getType().equals(type)) {
				return file;
			}
		}
		return null;
	}

	// get a string in the messages file
	@SuppressWarnings("ConstantConditions")
	public static String getMessage(String path) {
		ConfigFile file = getFile("messages", FileType.NORMAL);
		return file.getConfig().getString(path);
	}
}