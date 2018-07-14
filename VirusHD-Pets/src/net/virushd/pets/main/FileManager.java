package net.virushd.pets.main;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import net.virushd.core.main.SaveUtils;
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
		messages.addDefault("PetNames.Bat", "Fledermaus");                        // 1
		messages.addDefault("PetNames.Blaze", "Lohe");                            // 2
		messages.addDefault("PetNames.CaveSpider", "Höhlenspinne");                // 3
		messages.addDefault("PetNames.Chicken", "Huhn");                        // 4
		messages.addDefault("PetNames.Cow", "Kuh");                                // 5
		messages.addDefault("PetNames.Creeper", "Creeper");                        // 6
		messages.addDefault("PetNames.Enderman", "Enderman");                    // 7
		messages.addDefault("PetNames.Horse", "Pferd");                            // 8
		messages.addDefault("PetNames.IronGolem", "Eisengolem");                // 9
		messages.addDefault("PetNames.MagmaCube", "Magmawürfel");                // 10
		messages.addDefault("PetNames.MushroomCow", "Pilzkuh");                    // 11
		messages.addDefault("PetNames.Ocelot", "Ozelot");                        // 12

		// hier weiter machen
		messages.addDefault("PetNames.Pig", "Schwein");                            // 13
		messages.addDefault("PetNames.ZombiePigman", "Zombie Pigman");            // 14
		messages.addDefault("PetNames.Rabbit", "Hase");                            // 15
		messages.addDefault("PetNames.Sheep", "Schaf");                            // 16
		messages.addDefault("PetNames.Skeleton", "Selett");                        // 17
		messages.addDefault("PetNames.Slime", "Schliem");                        // 18
		messages.addDefault("PetNames.Spider", "Spinne");                        // 19
		messages.addDefault("PetNames.Villager", "Dorfbewohner");                // 20
		messages.addDefault("PetNames.Witch", "Hexe");                            // 21
		messages.addDefault("PetNames.WitherSkeleton", "Witherskelett");        // 22
		messages.addDefault("PetNames.Wolf", "Wolf");                            // 23
		messages.addDefault("PetNames.Zombie", "Zombie");                        // 24
		messages.addDefault("PetNames.ZombieVillager", "Dorfbewohnerzombie");    // 25
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
		options.addDefault("Options.Tamed", "&cGezähmt");
		options.addDefault("Options.HorseType", "&cTyp");
		options.addDefault("Options.CatType", "&cTyp");
		options.addDefault("Options.Sitting", "&cSitzen");
		options.addDefault("Options.CollarColor", "&cHalsband Farbe");
		options.addDefault("Names.Size.Small", "&7Klein");
		options.addDefault("Names.Size.Medium", "&7Mittel");
		options.addDefault("Names.Size.Big", "&7Gross");
		options.addDefault("Names.HorseType.DONKEY", "&7Esel");
		options.addDefault("Names.HorseType.HORSE", "&7Pferd");
		options.addDefault("Names.HorseType.MULE", "&7Maultier");
		options.addDefault("Names.HorseType.SKELETON_HORSE", "&7Skelettpferd");
		options.addDefault("Names.HorseType.UNDEAD_HORSE", "&7Zombiepferd");
		options.addDefault("Names.CatType.BLACK_CAT", "&7Schwarz");
		options.addDefault("Names.CatType.RED_CAT", "&7Rot");
		options.addDefault("Names.CatType.SIAMESE_CAT", "&7Siamkatze");
		options.addDefault("Names.CatType.WILD_OCELOT", "&7Ozelot");
		options.addDefault("Names.Color.WHITE", "&fWeiss");
		options.addDefault("Names.Color.ORANGE", "&6Orange");
		options.addDefault("Names.Color.MAGENTA", "&5Magenta");
		options.addDefault("Names.Color.LIGHT_BLUE", "&bHellblau");
		options.addDefault("Names.Color.YELLOW", "&eGelb");
		options.addDefault("Names.Color.LIME", "&aLimette");
		options.addDefault("Names.Color.PINK", "&dPink");
		options.addDefault("Names.Color.GRAY", "&8Grau");
		options.addDefault("Names.Color.SILVER", "&7Silber");
		options.addDefault("Names.Color.CYAN", "&bCyan");
		options.addDefault("Names.Color.PURPLE", "&5Violett");
		options.addDefault("Names.Color.BROWN", "&6Braun");
		options.addDefault("Names.Color.GREEN", "&2Grün");
		options.addDefault("Names.Color.RED", "&4Rot");
		options.addDefault("Names.Color.BLACK", "&0Schwarz");
//		options.addDefault("Names.HorseColor.", ""); // TODO HorseColor
//		options.addDefault("Names.HorseStyle.", ""); // TODO HorseStyle
		options.addDefault("True", "&aAn");
		options.addDefault("False", "&4Aus");
		options.options().copyDefaults(true);
		SaveUtils.saveFile(optionsF, options);
	}
}
