package net.virushd.coins.events;

import org.bukkit.event.Listener;

@Deprecated
public class LottoEvent implements Listener {
//
//	private HashMap<Player, Boolean> CanLotto = new HashMap<Player, Boolean>();
//
//	private CoinsMain pl;
//
//	public LottoEvent(CoinsMain main) {
//		this.pl = main;
//	}
//
//	@EventHandler
//	public void onItemRightClick(PlayerInteractEvent e) {
//
//		Player p = e.getPlayer();
//
//		String LottoPrefix = ChatColor.translateAlternateColorCodes('&', FileManager.messages.getString("LottoPrefix"));
//		String GetPrice = LottoPrefix + ChatColor.translateAlternateColorCodes('&', FileManager.messages.getString("Messages.GetPrice"));
//		String NoPrice = LottoPrefix + ChatColor.translateAlternateColorCodes('&', FileManager.messages.getString("Messages.NoPrice"));
//		String LotteryTicketLore = ChatColor.translateAlternateColorCodes('&', FileManager.lotto.getString("LotteryTicketLore"));
//		String WaitMessageOpen = LottoPrefix + ChatColor.translateAlternateColorCodes('&', FileManager.messages.getString("Messages.WaitMessageOpen"));
//		double WaitTime = FileManager.lotto.getDouble("WaitTime");
//		WaitMessageOpen = WaitMessageOpen.replace("{WaitTime}", "" + WaitTime);
//
//		ArrayList<String> LosLore = new ArrayList<>();
//		LosLore.add(LotteryTicketLore);
//
//		if (CanLotto.containsKey(p)) {
//
//		} else {
//			CanLotto.put(p, true);
//		}
//
//		// lotto
//		if (p.getItemInHand().getType().equals(Material.PAPER)) {
//			if (p.getItemInHand().hasItemMeta()) {
//				if (p.getItemInHand().getItemMeta().hasLore()) {
//					if (p.getItemInHand().getItemMeta().getLore().equals(LosLore)) {
//						if (CanLotto.get(p).equals(true)) {
//
//							// remove Item
//							int amount = p.getItemInHand().getAmount();
//							if (amount > 1) {
//								p.getItemInHand().setAmount(amount - 1);
//								p.setItemInHand(p.getItemInHand());
//							} else {
//								p.setItemInHand(new ItemStack(Material.AIR));
//							}
//
//							// Zufall generiern
//							int Baum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
//							if (Baum >= 1 && 30 >= Baum) {
//
//								// Preis 1
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.1"));
//								p.sendMessage(GetPrice);
//
//								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.1"));
//
//							} else if (Baum >= 30 && 50 >= Baum) {
//
//								// kein Preis
//								p.sendMessage(NoPrice);
//
//							} else if (Baum >= 50 && 69 >= Baum) {
//
//								// Preis 2
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.2"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.2"));
//
//							} else if (Baum >= 69 && 74 >= Baum) {
//
//								// Preis 3
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.3"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.3"));
//
//							} else if (Baum >= 74 && 79 >= Baum) {
//
//								// Preis 4
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.4"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.4"));
//
//							} else if (Baum >= 79 && 84 >= Baum) {
//
//								// Preis 5
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.5"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.5"));
//
//							} else if (Baum >= 84 && 89 >= Baum) {
//
//								// Preis 6
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.6"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.6"));
//
//							} else if (Baum >= 89 && 94 >= Baum) {
//
//								// Preis 7
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.7"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.7"));
//
//							} else if (Baum >= 94 && 99 >= Baum) {
//
//								// Preis 8
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.8"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.8"));
//
//							} else if (Baum >= 99 && 100 >= Baum) {
//
//								// Preis 9
//								GetPrice = GetPrice.replace("{Price}", "" + FileManager.lotto.getInt("Prices.9"));
//								p.sendMessage(GetPrice);
//
//								Coins.add(p.getUniqueId(), FileManager.lotto.getInt("Prices.9"));
//
//							} else {
//								p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Es ist ein Fehler aufgetreten (Nr 1). Bitte mache ein Screenshot und sende ihn an VirusHD."));
//							}
//
//							CanLotto.put(p, false);
//
//							// wartezeit
//							try {
//								Bukkit.getServer().getScheduler().runTaskLater(pl, new Runnable() {
//
//									@Override
//									public void run() {
//										CanLotto.put(p, true);
//									}
//
//								}, (long) (WaitTime + 1200L));
//							} catch (Exception ex) {
//								p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Es ist ein Fehler aufgetreten (Nr 2). Bitte mache ein Screenshot und sende ihn an VirusHD."));
//							}
//						} else {
//							p.sendMessage(WaitMessageOpen);
//						}
//					}
//				}
//			}
//		}
//	}
}
