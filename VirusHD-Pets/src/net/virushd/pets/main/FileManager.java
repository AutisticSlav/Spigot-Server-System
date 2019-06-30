package net.virushd.pets.main;

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
	public static File petsF;
	public static FileConfiguration pets;
	public static File inv_petsF;
	public static FileConfiguration inv_pets;
	public static File optionsF;
	public static FileConfiguration options;

	@SuppressWarnings("static-access")
	public static void manager() {

		// messages
		messagesF = new File("plugins/VirusHD-Pets", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("PetsPrefix", "&0[&5Pets&0] &7");
		messages.addDefault("PetPrice", 1000);
		messages.addDefault("Messages.Usage", "&7»->--------------<-« &cUsage &7»->--------------<-«"
				+ "\n&c - /pet buy <Name> <ID> &7Kaufe dir ein Pet."
				+ "\n&c - /pet sell &7Verkaufe dein Pet wieder."
				+ "\n&c - /pet hide &7Verstecke dein Pet."
				+ "\n&c - /pet show &7Zeige dein Pet."
				+ "\n&c - /pet name <Name> &7Gib deinem Pet einen neuen Namen.");
		messages.addDefault("Messages.Sell", "{PetsPrefix}Dein Pet wurde erfolgreich &cverkauft&7.");
		messages.addDefault("Messages.Buy", "{PetsPrefix}Du hast erfoglreich ein Pet &cgekauft&7.");
		messages.addDefault("Messages.NoPet", "{PetsPrefix}Du besitzt kein Pet.");
		messages.addDefault("Messages.Name", "{PetsPrefix}Dein Pet wurde erfolgreich &cumbenannt&7.");
		messages.addDefault("Messages.Hide", "{PetsPrefix}Dein Pet ist nun &cunsichtbar&7.");
		messages.addDefault("Messages.Show", "{PetsPrefix}Dein Pet ist nun wieder &csichtbar&7.");
		messages.addDefault("Messages.AlreadyHide", "{PetsPrefix}Dein Pet ist bereits &cunsichtbar&7.");
		messages.addDefault("Messages.AlreadyShown", "{PetsPrefix}Dein Pet ist bereits &csichtbar&7.");
		messages.addDefault("Messages.NotANumber", "{PetsPrefix}Die ID muss eine Nummer sein.");
		messages.addDefault("Messages.AlreadyAPet", "{PetsPrefix}Du hast bereits ein Pet.");
		messages.addDefault("Messages.WrongID", "{PetsPrefix}Bitte gebe eine gültige ID an.");
		messages.addDefault("Messages.IDs", "{PetsPrefix}Alle IDs sind:");
		messages.addDefault("Messages.IDFormat", " &c{ID}: &7{Name}");
		messages.addDefault("Messages.NotEnoughCoins", "{PetsPrefix}Du hast nicht genügend Coins. Ein Pet kostet &c{PetPrice} &7Coins.");

		// PetNames
		messages.addDefault("PetNames.Bat", "Fledermaus");
		messages.addDefault("PetNames.Blaze", "Lohe");
		messages.addDefault("PetNames.CaveSpider", "Höhlenspinne");
		messages.addDefault("PetNames.Chicken", "Huhn");
		messages.addDefault("PetNames.Cow", "Kuh");
		messages.addDefault("PetNames.Creeper", "Creeper");
		messages.addDefault("PetNames.Enderman", "Enderman");
		messages.addDefault("PetNames.Horse", "Pferd");
		messages.addDefault("PetNames.IronGolem", "Eisengolem");
		messages.addDefault("PetNames.MagmaCube", "Magmawürfel");
		messages.addDefault("PetNames.MushroomCow", "Pilzkuh");
		messages.addDefault("PetNames.Ocelot", "Ozelot");
		messages.addDefault("PetNames.Pig", "Schwein");
		messages.addDefault("PetNames.Rabbit", "Hase");
		messages.addDefault("PetNames.Sheep", "Schaf");
		messages.addDefault("PetNames.Skeleton", "Skelett");
		messages.addDefault("PetNames.Slime", "Schliem");
		messages.addDefault("PetNames.Spider", "Spinne");
		messages.addDefault("PetNames.Villager", "Dorfbewohner");
		messages.addDefault("PetNames.Witch", "Hexe");
		messages.addDefault("PetNames.Wolf", "Wolf");
		messages.addDefault("PetNames.Zombie", "Zombie");
		messages.addDefault("PetNames.PigZombie", "Schweinezombie");
		messages.options().copyDefaults(true);
		SaveUtils.saveFile(messagesF, messages);

		// pets
		petsF = new File("plugins/VirusHD-Pets", "pets.yml");
		pets = new YamlConfiguration().loadConfiguration(petsF);

		// inv_pets
		inv_petsF = new File("plugins/VirusHD-Pets/inventories", "pets.yml");
		inv_pets = new YamlConfiguration().loadConfiguration(inv_petsF);
		inv_pets.addDefault("Inventory.DisplayName", "&cPets");
		SaveUtils.defaultItemToFile(inv_pets, "Items.Buy", InventoryAPI.createItem("&cKaufen", Arrays.asList("&7Kaufe dir ein Pet für &c{PetPrice} &7Coins."), Material.GOLD_INGOT, null, 1));
		SaveUtils.defaultItemToFile(inv_pets, "Items.Sell", InventoryAPI.createItem("&cVerkaufen", Arrays.asList("&7Verkaufe dein Pet."), Material.BARRIER, null, 1));
		SaveUtils.defaultItemToFile(inv_pets, "Items.Name", InventoryAPI.createItem("&cUmbenennen", Arrays.asList("&7Gib dienem Pet einen anderern Namen."), Material.NAME_TAG, null, 1));
		SaveUtils.defaultItemToFile(inv_pets, "Items.Hide", InventoryAPI.createItem("&cVerstecken", Arrays.asList("&7Verstecke dein Pet."), Material.ENDER_PEARL, null, 1));
		SaveUtils.defaultItemToFile(inv_pets, "Items.Show", InventoryAPI.createItem("&cZeigen", Arrays.asList("&7Zeige dein Pet."), Material.ENDER_PEARL, null, 1));
		inv_pets.options().copyDefaults(true);
		SaveUtils.saveFile(inv_petsF, inv_pets);

		// options
		optionsF = new File("plugins/VirusHD-Pets", "options.yml");
		options = new YamlConfiguration().loadConfiguration(optionsF);
//		options.addDefault("Options.Ride", "&cReiten");
		options.addDefault("Options.Baby", "&cBaby");
		options.addDefault("Options.Size", "&cGrösse");
		options.addDefault("Options.Color", "&cFarbe");
		options.addDefault("Options.HorseType", "&cTyp");
		options.addDefault("Options.HorseColor", "&cFarbe");
		options.addDefault("Options.HorseStyle", "&cStil");
		options.addDefault("Options.CatType", "&cTyp");
		options.addDefault("Options.Sitting", "&cSitzen");
		options.addDefault("Options.CollarColor", "&cHalsband Farbe");
		options.addDefault("Options.SkeletonType", "&cTyp");
		options.addDefault("Options.Profession", "&cArbeit");
		options.addDefault("Options.ZombieVillager", "&cDorfbewohnerzombie");
		options.addDefault("Size.Small", "&7Klein");
		options.addDefault("Size.Medium", "&7Mittel");
		options.addDefault("Size.Big", "&7Gross");
		options.addDefault("HorseType.DONKEY", "&7Esel");
		options.addDefault("HorseType.HORSE", "&7Pferd");
		options.addDefault("HorseType.MULE", "&7Maultier");
		options.addDefault("HorseType.SKELETON_HORSE", "&7Skelettpferd");
		options.addDefault("HorseType.UNDEAD_HORSE", "&7Zombiepferd");
		options.addDefault("CatType.BLACK_CAT", "&7Schwarz");
		options.addDefault("CatType.RED_CAT", "&7Rot");
		options.addDefault("CatType.SIAMESE_CAT", "&7Siamkatze");
		options.addDefault("CatType.WILD_OCELOT", "&7Ozelot");
		options.addDefault("Color.WHITE", "&fWeiss");
		options.addDefault("Color.ORANGE", "&6Orange");
		options.addDefault("Color.MAGENTA", "&5Magenta");
		options.addDefault("Color.LIGHT_BLUE", "&bHellblau");
		options.addDefault("Color.YELLOW", "&eGelb");
		options.addDefault("Color.LIME", "&aLimette");
		options.addDefault("Color.PINK", "&dPink");
		options.addDefault("Color.GRAY", "&8Grau");
		options.addDefault("Color.SILVER", "&7Silber");
		options.addDefault("Color.CYAN", "&bCyan");
		options.addDefault("Color.PURPLE", "&5Violett");
		options.addDefault("Color.BLUE", "&1Blau");
		options.addDefault("Color.BROWN", "&6Braun");
		options.addDefault("Color.GREEN", "&2Grün");
		options.addDefault("Color.RED", "&4Rot");
		options.addDefault("Color.BLACK", "&0Schwarz");
		options.addDefault("HorseColor.WHITE", "&fWeiss");
		options.addDefault("HorseColor.CREAMY", "&6Cremig");
		options.addDefault("HorseColor.CHESTNUT", "&6Kastanie");
		options.addDefault("HorseColor.BROWN", "&6Braun");
		options.addDefault("HorseColor.BLACK", "&0Schwarz");
		options.addDefault("HorseColor.GRAY", "&8Grau");
		options.addDefault("HorseColor.DARK_BROWN", "&6Dunkelbraun");
		options.addDefault("HorseStyle.NONE", "&7Kein Style");
		options.addDefault("HorseStyle.WHITE", "&7Weisse Streifen");
		options.addDefault("HorseStyle.WHITEFIELD", "&7Weisslich");
		options.addDefault("HorseStyle.WHITE_DOTS", "&7Weisse Punkte");
		options.addDefault("HorseStyle.BLACK_DOTS", "&7Schwarze Punkte");
		options.addDefault("SkeletonType.NORMAL", "&7Normal");
		options.addDefault("SkeletonType.WITHER", "&7Wither");
		options.addDefault("Profession.FARMER", "&7Farmer");
		options.addDefault("Profession.LIBRARIAN", "&7Bibliothekar");
		options.addDefault("Profession.PRIEST", "&7Priester");
		options.addDefault("Profession.BLACKSMITH", "&7Schmied");
		options.addDefault("Profession.BUTCHER", "&7Metzger");
		options.addDefault("RabbitType.BROWN", "&6Braun");
		options.addDefault("RabbitType.WHITE", "&fWeiss");
		options.addDefault("RabbitType.BLACK", "&0Schwarz");
		options.addDefault("RabbitType.BLACK_AND_WHITE", "&7Schwarz und Weiss");
		options.addDefault("RabbitType.GOLD", "&6Gold");
		options.addDefault("RabbitType.SALT_AND_PEPPER", "&7Salz und Pfeffer");
		options.addDefault("RabbitType.THE_KILLER_BUNNY", "&4The Killer Bunny");
		options.addDefault("True", "&aAn");
		options.addDefault("False", "&4Aus");
		options.options().copyDefaults(true);
		SaveUtils.saveFile(optionsF, options);
	}
}
