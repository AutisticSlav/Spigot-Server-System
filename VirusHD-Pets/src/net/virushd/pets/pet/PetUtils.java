package net.virushd.pets.pet;

import java.util.UUID;

import net.virushd.core.main.SaveUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import net.virushd.pets.main.FileManager;

public class PetUtils {

	// buyPet
	public static void buyPet(Player owner, Pet pet, String petName) {

		FileManager.pets.set(owner.getUniqueId().toString() + ".ID", pet.getId());
		FileManager.pets.set(owner.getUniqueId().toString() + ".Name", petName);
		FileManager.pets.set(owner.getUniqueId().toString() + ".Hide", false);
		SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
		if (pet.getOptions().size() != 0) {
			for (Option option : pet.getOptions()) {
				option.save(owner);
			}
		}
		spawnPet(owner, owner.getWorld());
	}

	// sellPet
	public static void sellPet(Player owner) {

		despawnPet(owner, owner.getWorld());

		FileManager.pets.set(owner.getUniqueId().toString(), null);
		SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
	}

	// spawnPet
	public static void spawnPet(Player owner, World world) {
		if (FileManager.pets.contains(owner.getUniqueId().toString())) {
			Pet pet = Pet.getPet(FileManager.pets.getInt(owner.getUniqueId().toString() + ".ID"));
			String petName = FileManager.pets.getString(owner.getUniqueId().toString() + ".Name");

			LivingEntity ent = (LivingEntity) world.spawnEntity(owner.getLocation(), pet.getType());
			
			if (pet.getOptions().size() != 0) {
				for (Option option : pet.getOptions()) {
					option.run(owner, ent);
				}
			}
			
//			// Zombie
//			if (ent.getType().equals(EntityType.ZOMBIE)) {
//				Zombie zombie = (Zombie) ent;
//				zombie.setBaby(false);
//			}
//
//			// Cat
//			if (ent.getType().equals(EntityType.OCELOT)) {
//				Ocelot ocelot = (Ocelot) ent;
//				ocelot.setAdult();
//				ocelot.setTamed(true);
//				ocelot.setCatType(org.bukkit.entity.Ocelot.Type.BLACK_CAT);
//			}
//
//			// Dog
//			if (ent.getType().equals(EntityType.WOLF)) {
//				Wolf wolf = (Wolf) ent;
//				wolf.setAdult();
//				wolf.setTamed(true);
//			}
//
//			// Rabbit
//			if (ent.getType().equals(EntityType.RABBIT)) {
//				Rabbit rabbit = (Rabbit) ent;
//				rabbit.setAdult();
//				rabbit.setRabbitType(org.bukkit.entity.Rabbit.Type.BROWN);
//			}
//
//			// Horse
//			if (ent.getType().equals(EntityType.HORSE)) {
//				Horse horse = (Horse) ent;
//				horse.setAdult();
//				horse.setTamed(true);
//				horse.setStyle(org.bukkit.entity.Horse.Style.NONE);
//				horse.setColor(org.bukkit.entity.Horse.Color.BLACK);
//			}
//
//			// Sheep
//			if (ent.getType().equals(EntityType.SHEEP)) {
//				Sheep sheep = (Sheep) ent;
//				sheep.setAdult();
//				sheep.setColor(DyeColor.WHITE);
//			}
//
//			// Pig
//			if (ent.getType().equals(EntityType.PIG)) {
//				Pig pig = (Pig) ent;
//				pig.setAdult();
//			}
//
//			// Chicken
//			if (ent.getType().equals(EntityType.CHICKEN)) {
//				Chicken chicken = (Chicken) ent;
//				chicken.setAdult();
//			}

			ent.getEquipment().clear();

			if (owner.hasPermission("virushd.pets.color") || owner.hasPermission("*")) {
				ent.setCustomName(ChatColor.translateAlternateColorCodes('&', petName));
			} else {
				ent.setCustomName(petName);
			}
			ent.setCustomNameVisible(true);

			FileManager.pets.set(owner.getUniqueId().toString() + ".PetUUID", "" + ent.getUniqueId());
			SaveUtils.SaveFile(FileManager.petsF, FileManager.pets);
		}
	}

	// despawnPet
	public static void despawnPet(Player owner, World world) {
		if (FileManager.pets.contains(owner.getUniqueId().toString())) {
			String PetUUID = FileManager.pets.getString(owner.getUniqueId().toString() + ".PetUUID");
			for (Entity ent : world.getEntities()) {
				if (ent instanceof LivingEntity) {
					if (ent.getUniqueId().toString().equals(PetUUID)) {
						ent.remove();
					}
				}
			}
		}
	}

	// hasPet
	public static boolean hasPet(Player owner) {
		if (FileManager.pets.contains(owner.getUniqueId().toString())) {
			return true;
		}
		return false;
	}

	// getPet
	public static Entity getPet(Player owner) {
		if (FileManager.pets.contains(owner.getUniqueId().toString())) {
			String PetUUID = FileManager.pets.getString(owner.getUniqueId().toString() + ".PetUUID");
			for (Entity ent : Bukkit.getPlayer(UUID.fromString(owner.getUniqueId().toString())).getWorld()
					.getEntities()) {
				if (ent instanceof LivingEntity) {
					if (ent.getUniqueId().toString().equals(PetUUID)) {
						return ent;
					}
				}
			}

		}
		return null;
	}

	// follow player
	public static void walkToLoc(Entity pet, Location loc, double speed) {
		if (pet.getLocation().distanceSquared(loc) >= 15) {
			pet.teleport(loc);
		}
		((CraftCreature) pet).getHandle().getNavigation().a(loc.getX(), loc.getY(), loc.getZ(), speed);
	}
}
