package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.ArrayList;
import java.util.Arrays;

public class PigZombie extends Pet {

	public PigZombie() {
		super(EntityType.PIG_ZOMBIE, FileManager.messages.getString("PetNames.PigZombie"));

		addOption(new Option("Baby", new ArrayList<>(Arrays.asList(false, true)), new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((Zombie) ent).setBaby((boolean) theCase);
			}
		}, Material.EGG));
	}
}
