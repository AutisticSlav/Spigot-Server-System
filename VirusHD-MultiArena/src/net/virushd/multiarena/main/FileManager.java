package net.virushd.multiarena.main;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	public static File messagesF;
	public static FileConfiguration messages;
	public static File configF;
	public static FileConfiguration config;
	public static File arenasF;
	public static FileConfiguration arenas;
	
	@SuppressWarnings("static-access")
	public static void Manager() {
		
		// messages
		messagesF = new File("plugins/VirusHD-MultiArena", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("MultiArenaPrefix", "&0[&4MultiArena&0] &7");
		messages.addDefault("ChatFormat", "&0[&3Chat&0] &c{PlayerName}&0:&7 {Message}");
		messages.addDefault("Messages.Usage","&7»->--------------<-« &cUsage &7»->--------------<-«"
											+ "\n&c - /multiarena hilfe &7Die Hilfe zum Spielmodus."
											+ "\n&c - /multiarena commands &7Eine Liste der wichtigsten Commands."
											+ "\n&c - /multiarena regeln &7Die Regeln.");
		messages.addDefault("Messages.MultiArena", "{MultiArenaPrefix}Tippe &c/MultiArena&7.");
		messages.addDefault("Messages.NoJoinPerm", "{Prefix}&4Du hast keine Rechte &4MultiArena &7zu betreten.");
		messages.addDefault("Messages.Full", "{Prefix}&4MultiArena &7ist voll. Versuche es später noch einmal.");
		//messages.addDefault("Messages.NotANumber", "{MultiArenaPrefix}Die ID muss eine Nummer sein.");
		//messages.addDefault("Messages.WrongID", "{MultiArenaPrefix}Es gibt keine Arena mit dieser ID. Tippe &c/multiarenas arenas &7um alle Arenen zu sehen.");
		//messages.addDefault("Messages.Arenas", "{MultiArenaPrefix}Eine liste aller Arenen:");
		//messages.addDefault("Messages.ArenasFormat", " &c{ID}: &7{Name}");
		messages.addDefault("Messages.AlreadyStarted", "{MultiArenaPrefix}Das Spiel hat leider schon begonnen :/");
		
		// das wurde noch nicht benutzt
		messages.addDefault("Countdown.Lobby.Message", "{MultiArenaPrefix}Die Runde beginnt in &c{Time} &7Sekunden!");
		messages.addDefault("Countdown.Starting.Title", "{Time}");
		messages.addDefault("Countdown.Starting.Message", "{MultiArenaPrefix}MultiArena beginnt in &c{Time} &7Sekunden!");
		messages.addDefault("Countdown.Starting.Final", "{MultiArenaPrefix}MultiArena hat begonnen. Viel Glück!");
		messages.addDefault("Quit.Message", "{MultiArenaPrefix}&c{PlayerName}&7 hat das Spiel verlassen.");
		
		// das schon
		messages.addDefault("Join.Message", "{MultiArenaPrefix}&c{PlayerName}&7 hat das Spiel betreten.");
		messages.addDefault("Join.Title", "&4Wilkommen");
		messages.addDefault("Join.SubTitle", "&c{PlayerName} &7in &4MultiArena");
		messages.addDefault("TabTitle.Header", "&4VirusHD.net &7- &4MultiArena\n&7-----------------------------------");
		messages.addDefault("TabTitle.Footer", "&7-----------------------------------\n&cViel Spass!");
		
		// das aber nicht
		messages.addDefault("Messages.Help", "&7»->--------------<-« &cDie Hilfe &7»->--------------<-«"
											+ "\n&c - &7Spiel Einfach!");
		messages.addDefault("Messages.Commands", "&7»->--------------<-« &cDie Commands &7»->--------------<-«"
											+ "\n&c - /msg: &7Mit einem Spieler chatten."
											+ "\n&c - /coins: &7Der Coins command."
											+ "\n&c - /lobby: &7Kehre zur Lobby zurück.");
		messages.addDefault("Messages.Rules", "&7»->--------------<-« &cDie Regeln &7»->--------------<-«"
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
		configF = new File("plugins/VirusHD-MultiArena", "config.yml");
		config = new YamlConfiguration().loadConfiguration(configF);
		config.addDefault("MaxPlayers", 10);
		config.addDefault("MinPlayers", 5);
		config.addDefault("Countdown.Lobby", 10);
		config.addDefault("Countdown.Starting", 10);
		config.addDefault("Countdown.Restarting", 5);
		config.addDefault("GameStates.Lobby", "&aLobby");
		config.addDefault("GameStates.LobbyFull", "&eLobby");
		config.addDefault("GameStates.Ingame", "&4Ingame");
		config.addDefault("Sign.Lines.1", "&7- &0[&4Multiarena&0] &7-");
		config.addDefault("Sign.Lines.2", "&c{Name}");
		config.addDefault("Sign.Lines.3", "&0[&r{GameState}&0]");
		config.addDefault("Sign.Lines.4", "&c{Players} &7/ &c{MaxPlayers}");
		try {
			config.save(configF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// arenas
		arenasF = new File("plugins/VirusHD-MultiArena", "arenas.yml");
		arenas = new YamlConfiguration().loadConfiguration(arenasF);
		
	}
	
	public static void SaveArenasFile() {
		try {
			arenas.save(arenasF);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
