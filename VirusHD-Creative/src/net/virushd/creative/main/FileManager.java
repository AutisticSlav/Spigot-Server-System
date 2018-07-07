package net.virushd.creative.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.virushd.core.main.SaveUtils;

public class FileManager {

	public static File messagesF;
	public static FileConfiguration messages;
	public static File configF;
	public static FileConfiguration config;
	public static File sco_creativeF;
	public static FileConfiguration sco_creative;
	
	@SuppressWarnings("static-access")
	public static void Manager() {
		
		// messages
		messagesF = new File("plugins/VirusHD-Creative", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("CreativePrefix", "&0[&6Creative&0] &7");
		messages.addDefault("ChatFormat", "&0[&3Chat&0] &c{PlayerName}&0:&7 {Message}");
		messages.addDefault("Messages.Usage","&7»->--------------<-« &cUsage &7»->--------------<-«"
											+ "\n&c - /creative hilfe &7Die Hilfe zum Spielmodus."
											+ "\n&c - /creative commands &7Eine Liste der wichtigsten Commands."
											+ "\n&c - /creative regeln &7Die Regeln.");
		messages.addDefault("Messages.Creative", "{CreativePrefix}Tippe &c/creative&7.");
		messages.addDefault("Messages.NoJoinPerm", "{Prefix}Nur gute Builder dürfen &6Creative &7betreten.\n&7Kontaktire den Admin wenn du gut bauen kannst.");
		messages.addDefault("Messages.Full", "{Prefix}&6Creative &7ist voll. Versuche es später noch einmal.");
		messages.addDefault("Messages.NotInCreative", "{CreativePrefix}&cFehler: &4Du darfst diesen Command nicht in &6Creative &4ausführen.");
		messages.addDefault("Quit.Message", "{CreativePrefix}&c{PlayerName}&7 hat &6Creative &7verlassen.");
		messages.addDefault("Join.Message", "{CreativePrefix}&c{PlayerName}&7 hat &6Creative &7betreten.");
		messages.addDefault("Join.Title", "&4Wilkommen");
		messages.addDefault("Join.SubTitle", "&c{PlayerName} &7in &6Creative");
		messages.addDefault("TabTitle.Header", "&4VirusHD.net &7- &6Creative\n&7-----------------------------------");
		messages.addDefault("TabTitle.Footer", "&7-----------------------------------\n&cViel Spass!");
		messages.addDefault("Messages.Help", "&7»->--------------<-« &cDie Hilfe &7»->--------------<-«"
											+ "\n&c - &7Baue dir ein schönes Haus oder was du auch immer willst."
											+ "\n&c - &7Viel spass in Creative.");
		messages.addDefault("Messages.Commands", "&7»->--------------<-« &cDie Commands &7»->--------------<-«"
											+ "\n&c - /msg &7Mit einem Spieler chatten."
											+ "\n&c - /coins &7Der Coins command."
											+ "\n&c - /lobby &7Kehre zur Lobby zurück."
											+ "\n&c Nur für &4V&9I&aP &c/ &fYou&4Tuber&c:"
											+ "\n&c - /skull &7Gibt dir einen kopf von dir.");
		messages.addDefault("Messages.Rules", "&7»->--------------<-« &cDie Regeln &7»->--------------<-«"
											+ "\n&c - &7Häuser von anderen nicht zerstören."
											+ "\n&c - &7Fair spielen."
											+ "\n&c - &7Spam ist untersagt."
											+ "\n&c - &7Nicht Hacken."
											+ "\n&c - &7Bei Fragen sich an den Support wenden."
											+ "\n&c - &7Bei Bugs einen Screen machen und ihn mit einer genauen Schilderung dem Admin schicken.");
		messages.options().copyDefaults(true);
		try {
			messages.save(messagesF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// config
		configF = new File("plugins/VirusHD-Creative", "config.yml");
		config =  new YamlConfiguration().loadConfiguration(configF);
		SaveUtils.SaveLocationToFile(configF, config, "Spawns.Creative", Bukkit.getWorld("Creative").getSpawnLocation());
		config.addDefault("GameStates.Lobby", "&aLobby");
		config.addDefault("GameStates.LobbyFull", "&eLobby");
		config.addDefault("MaxPlayers", 10);
		config.addDefault("Sign.Lines.1", "&7- &0[&6Creative&0] &7-");
		config.addDefault("Sign.Lines.2", "&c{Name}");
		config.addDefault("Sign.Lines.3", "&0[&r{GameState}&0]");
		config.addDefault("Sign.Lines.4", "&c{Players} &7/ &c{MaxPlayers}");
		config.options().copyDefaults(true);
		try {
			config.save(configF);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// sco_creative
		sco_creativeF = new File("plugins/VirusHD-Creative/scoreboards", "creative.yml");
		sco_creative = new YamlConfiguration().loadConfiguration(sco_creativeF);
		SaveUtils.DefaultScoreboardToFile(sco_creative, "Creative", "&4VirusHD.net &7- &6Creative", Arrays.asList("", "&cBauideen:", "&7{Ideas}", " ", "&cRang:", "&7{Rank}", "  ", "&cCoins:", "&7{Coins}"));
		sco_creative.addDefault("#the idead: https://minecraftbuildinginc.com/minecraft-building-ideas-100/", "");
		sco_creative.addDefault("Ideas", Arrays.asList("Zoo", "School", "Farm", "Space Station", "Dump Truck",
				"Highway", "House", "Police Station", "Theme Park", "College Campus", "Gas Station", "Castle", "Mine",
				"Bridge", "Apartment", "Skyscraper", "Airplane", "Cruise Ship", "Pool / Water Park", "Roller Coaster",
				"Floating Mountain", "Garden", "Bar", "Best Buy", "Igloo", "National Park", "Waterfall", "Computer",
				"Connect 4", "Bus", "Mansion", "Your neighborhood", "Tent / Campsite", "White House", "Giant Mushroom",
				"Avatar Tree", "Eiffel Tower", "Sawmill", "Fountain", "Basketball Court", "Lawn Mower", "Tower of Pisa",
				"Mario", "Colosseum", "Your House", "iPhone / iPad", "Tree house", "Underwater Home", "Spaceship",
				"Taj Mahal", "Pyramid", "Stonehenge", "Hot Dog Stand", "WWII Bunkers", "Habitat 67", "Cubic Houses",
				"Train Station", "Giant Robot", "The Basket Building", "The Crooked House", "Kettle House", "Library",
				"Horse Stables", "Chapel in the Rock", "Traffic Lights", "Mount Everest", "Fort", "Windmill", "Prison",
				"Fire Station", "Supermarket", "Beach", "Church", "Airport", "Hagia Sophia", "Football Stadium",
				"Tennis court", "Submarine", "Nord LB building", "Pirate Ship", "Catacombs", "Graveyard", "Village",
				"your hero", "Library", "Pokemon Stadium", "Cluny Abbey", "Laptop", "Death Star", "Gas Station",
				"Air Force", "Restaurant", "Itaipu Dam", "Target", "Six Flags", "Washington Monument", "Pyramid",
				"Cairo Citadel", "Ely Cathedral", "Panama Canal", "Grand Canyon", "CN Tower", "Ryugyong Hotel",
				"Mario Level", "Helicopter"));
		sco_creative.options().copyDefaults(true);
		try {
			sco_creative.save(sco_creativeF);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
