package net.virushd.coins.main;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.virushd.core.api.SaveUtils;
import net.virushd.inventory.main.InventoryAPI;

public class FileManager {

	public static File messagesF;
	public static FileConfiguration messages;
	public static File inv_lotteryF;
	public static FileConfiguration inv_lottery;

	@SuppressWarnings("static-access")
	public static void manager() {

		// messages
		messagesF = new File("plugins/VirusHD-Coins", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("CoinsPrefix", "&0[&2Coins&0] &7");
		messages.addDefault("LotteryPrefix", "&0[&2Lotto&0] &7");
		messages.addDefault("Messages.NotANumber", "{CoinsPrefix}Die Anzahl an Coins muss eine Nummer sein.");
		messages.addDefault("Messages.Usage", "&7»->--------------<-« &cUsage &7»->--------------<-«"
				+ "\n&c - /coins add <Spieler> <Menge> &7Einem Spieler Coins dazu addieren."
				+ "\n&c - /coins remove <Spieler> <Menge> &7Einem Spieler Coins entfernen."
				+ "\n&c - /coins set <Spieler> <Menge> &7Einem Spieler eine bestimmte menge an Coins setzen"
				+ "\n&c - /coins check <Spieler> &7Hiermit erfährst du wie viele Coins ein Spieler hat.");
		messages.addDefault("Messages.PlayerNotFound", "{CoinsPrefix}&cFehler: &4Spieler nicht gefunden.");
		messages.addDefault("Messages.CheckSelf", "{CoinsPrefix}Deine Coins:&c {Coins}&7.");
		messages.addDefault("Messages.CheckOthers", "{CoinsPrefix}Coins von {DisplayName}:&c {Coins}&7.");
		messages.addDefault("Messages.Add1", "{CoinsPrefix}&c{Amount}&7 Coins zu {DisplayName}&7 hinzugefügt.");
		messages.addDefault("Messages.Add2", "{CoinsPrefix}&c{Amount}&7 Coins zu deinem Konto hinzugefügt.");
		messages.addDefault("Messages.Remove1", "{CoinsPrefix}&c{Amount}&7 Coins von {DisplayName}&7 entfernt.");
		messages.addDefault("Messages.Remove2", "{CoinsPrefix}&c{Amount}&7 Coins von deinem Konto entfernt.");
		messages.addDefault("Messages.Set1", "{CoinsPrefix}{DisplayName}&7 hat jetzt &c{Coins}&7 Coins.");
		messages.addDefault("Messages.Set2", "{CoinsPrefix}Dein Kontostand wurde geändert. Du hast jetzt &c{Coins}&7 Coins.");
		messages.addDefault("Messages.CoinsEarned", "{CoinsPrefix}&c+{Amount} &7Coins!");
		messages.addDefault("Messages.Lost", "{LotteryPrefix}Leider kein Preis. Vielleicht hast du mehr Glück beim nächsten mal.");
		messages.addDefault("Messages.Won", "{LotteryPrefix}Glückwunsch du hast &c{Won} &7Coins gewonnen.");
		messages.addDefault("Messages.NotEnoughCoins", "{LotteryPrefix}Du hast zu wenig Coins.");
		messages.addDefault("Messages.Wait", "{LotteryPrefix}Du kannst nur alle &c{WaitTime} &7Minuten ein Los kaufen.");
		messages.options().copyDefaults(true);
		SaveUtils.saveFile(messagesF, messages);

		// inv_lottery
		inv_lotteryF = new File("plugins/VirusHD-Coins/inventories", "lottery.yml");
		inv_lottery = new YamlConfiguration().loadConfiguration(inv_lotteryF);
		inv_lottery.addDefault("Inventory.DisplayName", "&cLotto");
		SaveUtils.defaultItemToFile(inv_lottery, "Items.Info", InventoryAPI.createItem("&cInfo", Arrays.asList("&7Du kannst ein Los nur alle &c{WaitTime} &7minuten kaufen."), Material.TORCH, null, 1));
		SaveUtils.defaultItemToFile(inv_lottery, "Items.NormalTicket", InventoryAPI.createItem("&cNormales Los", Arrays.asList("&7Normales Los kaufen für &c{NormalPrice} &7Coins."), Material.PAPER, null, 1));
		SaveUtils.defaultItemToFile(inv_lottery, "Items.HighChanceTicket", InventoryAPI.createItem("&cHohe Chance", Arrays.asList("&7Los mit höherer Chance für &c{HighChancePrice} &7Coins kaufen."), Material.PAPER, null, 1));
		inv_lottery.addDefault("NormalPrice", 100);
		inv_lottery.addDefault("HighChancePrice", 300);
		inv_lottery.addDefault("WaitTime", 10);
		inv_lottery.options().copyDefaults(true);
		SaveUtils.saveFile(inv_lotteryF, inv_lottery);
	}
}
