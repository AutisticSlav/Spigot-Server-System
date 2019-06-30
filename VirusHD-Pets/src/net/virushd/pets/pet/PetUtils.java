package net.virushd.pets.pet;

import java.util.UUID;
import java.util.logging.Level;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.virushd.core.api.SaveUtils;
import net.virushd.core.api.Utils;
import net.virushd.pets.main.PetsMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import net.virushd.pets.main.FileManager;

public class PetUtils {

	// buy a pet (and spawn it)
	public static void buyPet(Player owner, Pet pet, String petName) {

		FileManager.pets.set(owner.getUniqueId().toString() + ".ID", pet.getId());
		FileManager.pets.set(owner.getUniqueId().toString() + ".Name", petName);
		FileManager.pets.set(owner.getUniqueId().toString() + ".Hide", false);
		SaveUtils.saveFile(FileManager.petsF, FileManager.pets);
		if (pet.getOptions().size() != 0) {
			for (Option option : pet.getOptions()) {
				option.save(owner);
			}
		}
		spawnPet(owner, owner.getWorld());
	}

	// sell a pet (and despawn it)
	public static void sellPet(Player owner) {

		despawnPet(owner, owner.getWorld());

		FileManager.pets.set(owner.getUniqueId().toString(), null);
		SaveUtils.saveFile(FileManager.petsF, FileManager.pets);
	}

	// spawn a pet
	public static void spawnPet(Player owner, World world) {
		if (FileManager.pets.contains(owner.getUniqueId().toString())) {
			Pet pet = Pet.getPet(FileManager.pets.getInt(owner.getUniqueId().toString() + ".ID"));
			String petName = FileManager.pets.getString(owner.getUniqueId().toString() + ".Name");

			LivingEntity ent = (LivingEntity) world.spawnEntity(owner.getLocation().add(1d, 0d, 1d), pet.getType());

			if (pet.getOptions().size() != 0) {
				for (Option option : pet.getOptions()) {
					option.run(owner, ent);
				}
			}

			if (ent instanceof Tameable) {
				Tameable t = (Tameable) ent;
				t.setTamed(true);
				t.setOwner(owner);
			}

			ent.getEquipment().clear();

			if (owner.hasPermission("virushd.pets.color") || owner.hasPermission("*")) {
				ent.setCustomName(ChatColor.translateAlternateColorCodes('&', petName));
			} else {
				ent.setCustomName(petName);
			}
			ent.setCustomNameVisible(true);

			FileManager.pets.set(owner.getUniqueId().toString() + ".PetUUID", "" + ent.getUniqueId());
			SaveUtils.saveFile(FileManager.petsF, FileManager.pets);
		}
	}

	// despawn a pet
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

	// check if a player has a pet
	public static boolean hasPet(Player owner) {
		return FileManager.pets.contains(owner.getUniqueId().toString());
	}

	// get the pet by its owner
	public static Entity getPet(Player owner) {
		if (hasPet(owner)) {
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

	// follow the player
	public static void walkToLoc(Entity pet, Location loc) {
		if (!(pet instanceof Ocelot && ((Ocelot) pet).isSitting() || pet instanceof Wolf && ((Wolf) pet).isSitting())) {
			/* if (Math.round(Utils.locationDistance(pet.getLocation(), loc)) >= 15) {
				pet.teleport(loc);
			}

			((CraftCreature) pet).getHandle().getNavigation().a(loc.getX(), loc.getY(), loc.getZ(), 1.4); */

			EntityInsentient handle = (EntityInsentient) ((CraftEntity) pet).getHandle();
			PathEntity path = (handle).getNavigation().a(loc.getX() + 1, loc.getY(), loc.getZ() +1);

			if(path != null) {
				(handle).getNavigation().a(path, 1.0D);
				(handle).getNavigation().a(2.0D);
			}

			if(loc.distance(pet.getLocation()) > 10) {
				pet.teleport(loc);
			}
		}
	}
}
