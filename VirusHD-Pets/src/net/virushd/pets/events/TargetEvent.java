package net.virushd.pets.events;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import net.virushd.pets.main.FileManager;

public class TargetEvent implements Listener {

	@EventHandler
	public void onPetTarget(EntityTargetEvent e) {

		// if config contains entity uuid
		Collection<? extends Player> onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
		int i = 0;
		String[] petuuids = new String[Bukkit.getServer().getOnlinePlayers().size()];
		for (Player player : onlinePlayerList) {
			if (FileManager.pets.contains(player.getUniqueId().toString())) {
				petuuids[i] = FileManager.pets.getString(player.getUniqueId().toString() + ".PetUUID");
				if (petuuids[i].equals(e.getEntity().getUniqueId().toString())) {
					e.setCancelled(true);
				} else {
					i++;
				}
			} else {
				i++;
			}
		}
	}
}
