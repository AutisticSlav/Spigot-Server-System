package net.virushd.citybuild.inventories;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.citybuild.main.FileManager;
import net.virushd.core.api.PlaceHolder;
import net.virushd.core.api.SaveUtils;
import net.virushd.core.api.Utils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Plot {

	private static HashMap<Player, Location> positions1 = new HashMap<>();
	private static HashMap<Player, Location> positions2 = new HashMap<>();

	public static void open(Player p) {

		String InventoryDisplayName = PlaceHolder.normal(FileManager.inv_plot.getString("Inventory.DisplayName"));
		ItemStack Pos1Item = SaveUtils.getItemFromFile(FileManager.inv_plot, "Items.Pos1");
		ItemStack Pos2Item = SaveUtils.getItemFromFile(FileManager.inv_plot, "Items.Pos2");
		ItemStack DeleteItem = SaveUtils.getItemFromFile(FileManager.inv_plot, "Items.Delete");
		ItemStack CreateItem = SaveUtils.getItemFromFile(FileManager.inv_plot, "Items.Create");
		String Pos1 = PlaceHolder.normal(FileManager.messages.getString("Messages.Pos1"));
		String Pos2 = PlaceHolder.normal(FileManager.messages.getString("Messages.Pos2"));
		String Delete = PlaceHolder.normal(FileManager.messages.getString("Messages.Delete"));
		String Create = PlaceHolder.normal(FileManager.messages.getString("Messages.Create"));
		String AlreadyAPlot = PlaceHolder.normal(FileManager.messages.getString("Messages.AlreadyAPlot"));
		String NoPlot = PlaceHolder.normal(FileManager.messages.getString("Messages.NoPlot"));
		String PlotCollides = PlaceHolder.normal(FileManager.messages.getString("Messages.PlotCollides"));
		String PlotTooBig = PlaceHolder.normal(FileManager.messages.getString("Messages.PlotTooBig"));
		World CityBuildWorld = SaveUtils.getLocationFromFile(FileManager.config, "Spawns.CityBuild").getWorld();

		Inventory inv = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.HOPPER);

		// set the first position for a new plot
		inv.setSlot(0, Pos1Item, new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				positions1.put(p, p.getLocation());
				p.sendMessage(Pos1);
			}
		});

		// set the second position
		inv.setSlot(1, Pos2Item, new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				positions2.put(p, p.getLocation());
				p.sendMessage(Pos2);
			}
		});

		// delete it
		inv.setSlot(3, DeleteItem, new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				if (CityBuildMain.getWorldGuard().getRegionManager(p.getWorld()).hasRegion("Plot_" + p.getUniqueId())) {
					CityBuildMain.getWorldGuard().getRegionManager(CityBuildWorld).removeRegion("Plot_" + p.getUniqueId());
					p.sendMessage(Delete);
				} else {
					p.sendMessage(NoPlot);
				}
			}
		});

		// create a plot
		inv.setSlot(4, CreateItem, new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				if (CityBuildMain.getWorldGuard().getRegionManager(p.getWorld()).hasRegion("Plot_" + p.getUniqueId())) {
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

						if (Utils.locationArea(positions1.get(p), positions2.get(p)) > FileManager.config.getInt("MaxPlotSize") && !p.hasPermission("virushd.citybuild.ignoreplotsize")) {
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
		});

		inv.open(p);
	}
}
