package net.virushd.citybuild.events;

import java.util.HashMap;

import net.virushd.core.main.PlaceHolder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.citybuild.main.FileManager;
import net.virushd.core.main.SaveUtils;
import net.virushd.core.main.Utils;

@Deprecated
public class InvClickEvent implements Listener {
	
	private static HashMap<Player, Location> positions1 = new HashMap<>();
	private static HashMap<Player, Location> positions2 = new HashMap<>();
	
	@EventHandler
	public void onInvClick (InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		
		String Pos1 = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Pos1"), p);
		String Pos2 = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Pos2"), p);
		String Delete = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Delete"), p);
		String Create = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Create"),  p);
		String AlreadyAPlot = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.AlreadyAPlot"), p);
		String NoPlot = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NoPlot"), p);
		String InventoryDisplayName = PlaceHolder.Normal(FileManager.inv_plot.getString("InventoryDisplayName"));
		String PlotCollides = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.PlotCollides"), p);
		String PlotTooBig = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.PlotTooBig"), p);
		World CityBuildWorld = SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.CityBuild").getWorld();
		
		if (CityBuildMain.players.contains(p)) {
			if (e.getInventory().getName().equals(InventoryDisplayName)) {
				
				if (p.getWorld().equals(CityBuildWorld)) {

					// Pos1
					if (Utils.ItemEquals(e.getCurrentItem(), SaveUtils.GetItemFromFile(FileManager.inv_plot, "Items.Pos1"))) {
						positions1.put(p, p.getLocation());
						p.sendMessage(Pos1);
					}

					// Pos2
					if (Utils.ItemEquals(e.getCurrentItem(), SaveUtils.GetItemFromFile(FileManager.inv_plot, "Items.Pos2"))) {
						positions2.put(p, p.getLocation());
						p.sendMessage(Pos2);
					}

					// Mittleres Item
					//...

					// Delete
					if (Utils.ItemEquals(e.getCurrentItem(), SaveUtils.GetItemFromFile(FileManager.inv_plot, "Items.Delete"))) {
						if (CityBuildMain.getWorldGuard().getRegionManager(p.getWorld()).hasRegion("Plot_" + p.getUniqueId())) {
							CityBuildMain.getWorldGuard().getRegionManager(CityBuildWorld).removeRegion("Plot_" + p.getUniqueId());
							p.sendMessage(Delete);
						} else {
							p.sendMessage(NoPlot);
						}
					}

					// Create
					if (Utils.ItemEquals(e.getCurrentItem(), SaveUtils.GetItemFromFile(FileManager.inv_plot, "Items.Create"))) {
						if (CityBuildMain.getWorldGuard().getRegionManager(p.getWorld())
								.hasRegion("Plot_" + p.getUniqueId())) {
							p.sendMessage(AlreadyAPlot);
						} else {
							if (positions1.containsKey(p) && positions2.containsKey(p)) {

								Vector vecMin = new Vector(positions1.get(p).getX(), 0, positions1.get(p).getZ());
								BlockVector Min = new BlockVector(vecMin);

								Vector vecMax = new Vector(positions2.get(p).getX(), CityBuildWorld.getMaxHeight(), positions2.get(p).getZ());
								BlockVector Max = new BlockVector(vecMax);

								ProtectedCuboidRegion region = new ProtectedCuboidRegion("Plot_" + p.getUniqueId(), Min, Max);

								DefaultDomain owners = new DefaultDomain();
								owners.addPlayer(p.getUniqueId());
								
								region.setOwners(owners);
								
								if (difference(positions1.get(p), positions2.get(p)) > FileManager.config.getInt("MaxPlotSize") && !p.hasPermission("virushd.citybuild.ignoreplotsize")) {
									p.sendMessage(PlotTooBig);
								} else if (CityBuildMain.getWorldGuard().getRegionManager(CityBuildWorld).getApplicableRegions(region).size() != 0) {
									p.sendMessage(PlotCollides);
								} else {
									CityBuildMain.getWorldGuard().getRegionManager(CityBuildWorld).addRegion(region);
									p.sendMessage(Create);
								}
							}
						}
					}
				}
				e.setCancelled(true);
			}
		}
	}
	
	private double difference(Location pos1, Location pos2) {
		double x = (pos2.getX() - pos1.getX());
		double z = (pos2.getZ() - pos1.getZ());
		double size = Math.abs(x * z);
		return size;
	}
}
