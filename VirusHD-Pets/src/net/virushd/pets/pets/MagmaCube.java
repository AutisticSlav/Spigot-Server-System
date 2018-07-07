package net.virushd.pets.pets;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;

public class MagmaCube extends Pet {

	public MagmaCube() {
		super(10, EntityType.MAGMA_CUBE, FileManager.messages.getString("PetNames.MagmaCube"));
		
		addOption(new Option("Size", new ArrayList<>(Arrays.asList("small", "medium", "big")), new Option.Action() {
			
			@Override
			public void run(Player p, Object theCase, Entity ent) {
				org.bukkit.entity.MagmaCube cube = (org.bukkit.entity.MagmaCube) ent;
				
				if (theCase.equals("small")) {
					cube.setSize(1);
				} else if (theCase.equals("medium")) {
					cube.setSize(2);
				} else if (theCase.equals("big")) {
					cube.setSize(3);
				}
			}
		}, Material.MAGMA_CREAM));
	}
}