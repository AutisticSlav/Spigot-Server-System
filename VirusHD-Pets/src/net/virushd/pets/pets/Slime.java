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

public class Slime extends Pet {
	public Slime() {
		super(EntityType.SLIME, FileManager.messages.getString("PetNames.Slime"));

		addOption(new Option("Size", new ArrayList<>(Arrays.asList("small", "medium", "big")), new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				org.bukkit.entity.Slime slime = (org.bukkit.entity.Slime) ent;

				if (theCase.equals("small")) {
					slime.setSize(1);
				} else if (theCase.equals("medium")) {
					slime.setSize(2);
				} else if (theCase.equals("big")) {
					slime.setSize(3);
				}
			}
		}, Material.MAGMA_CREAM));
	}
}
