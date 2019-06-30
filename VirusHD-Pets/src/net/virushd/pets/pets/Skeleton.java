package net.virushd.pets.pets;

import net.virushd.pets.main.FileManager;
import net.virushd.pets.pet.Option;
import net.virushd.pets.pet.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton.SkeletonType;

import java.util.ArrayList;

public class Skeleton extends Pet {

	public Skeleton() {
		super(EntityType.SKELETON, FileManager.messages.getString("PetNames.Skeleton"));

		ArrayList<Object> types = new ArrayList<>();
		for (SkeletonType type : SkeletonType.values()) {
			types.add("SkeletonType." + type.toString());
		}
		addOption(new Option("SkeletonType", types, new Option.Action() {

			@Override
			public void run(Player p, Object theCase, Entity ent) {
				((org.bukkit.entity.Skeleton) ent).setSkeletonType(SkeletonType.valueOf(theCase.toString().replaceFirst("SkeletonType.", "")));
			}
		}, Material.BOOK));
	}
}
