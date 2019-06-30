package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Zombie extends Pet {

	public Zombie() {
		super(EntityType.ZOMBIE, FileManager.messages.getString("PetNames.Zombie"));

		addOption(new Option("Baby", new ArrayList<>(Arrays.asList(false, true)), new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Zombie) ent).setBaby((boolean) theCase);
			}
		}, Material.EGG));

		addOption(new Option("ZombieVillager", new ArrayList<>(Arrays.asList(false, true)), new Option.Action() {
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Zombie) ent).setVillager((boolean) theCase);
			}
		}, Material.EMERALD));
	}
}
