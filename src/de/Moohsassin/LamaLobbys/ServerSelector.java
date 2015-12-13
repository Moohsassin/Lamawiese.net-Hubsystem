package de.Moohsassin.LamaLobbys;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ServerSelector {
	
	private FileConfiguration cfg;
	private File file;
	
	public Inventory inv;
	
	public HashMap<String, ServerItem> items = new HashMap<>();
	public HashMap<String, Integer> online = new HashMap<>();
	
	public String title;
	
	public int size;
	
	public ArrayList<Integer> soon = new ArrayList<>();
	
	public ServerSelector() {
		file = new File("/minecraft", "hub-compass.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
		loadContent();
		
		new BukkitRunnable() {@Override public void run() {
			
			
			updateOnlineCounts();
			
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(players.getLocation().getBlock().isLiquid()) {
					
					if(players.hasPotionEffect(PotionEffectType.WATER_BREATHING)) continue;
					
					for(int i = 1; i < 6; i ++) {
						if(players.getLocation().add(new Vector(0, -i, 0)).getBlock().getType() == Material.SEA_LANTERN) {
							players.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 1));
							open(players);
						}	
					}
					
				} else {
					players.removePotionEffect(PotionEffectType.WATER_BREATHING);
				}
			}
			
		}}.runTaskTimer(LamaLobbys.instance, 40, 40);
		
	}
	
	public void updateOnlineCounts() {
		Executors.newCachedThreadPool().execute(new Runnable() { public void run() {
			for(String slots : items.keySet()) {
				
				ServerItem sItem = items.get(slots);
				String targetServer = sItem.targetServer;
				
				if(targetServer.contains("-")) {

					String server = targetServer.split("-")[0];
					
					int on = ServerManager.sql.getInt("OnlineCounts", "Name", server, "Count");
					if(on != -1) {
						online.put(server, on);
					}
				}
			}
		}});
	}
	
	@SuppressWarnings("deprecation")
	public void loadContent() {
		
		size = cfg.getInt("Inv_Rows") * 9;
		title = cfg.getString("Inv_Name");
		
		for(String slot : cfg.getString("Soon").split(";")) {
			soon.add(Integer.parseInt(slot));
		}
		
		for(String slots : cfg.getConfigurationSection("Slot").getKeys(false)) {
			
			int slot = Integer.parseInt(slots);
			String subPath = "Slot." + slots;
			items.put(slot + "", new ServerItem(cfg.getString(subPath + ".Name"), Material.getMaterial(cfg.getInt(subPath + ".ItemID")), (short) cfg.getInt(subPath + ".ItemSID"), cfg.getStringList(subPath + ".Lore"), cfg.getString(subPath + ".Server")));
			
		}
		
		updateOnlineCounts();
		
	}
	
	public void open(Player p) {
		
		Inventory inv = Bukkit.createInventory(p, size, title);
		
		for(String slots : items.keySet()) {
			
			int slot = Integer.parseInt(slots)-1;
			ServerItem sItem = items.get(slots);
			ItemStack stack = sItem.getRawItemStack();
			List<String> lore = new ArrayList<>();
			for(String s : sItem.lore) lore.add("§7" + s);
			String targetServer = sItem.targetServer;
			
			lore.add("  ");
			lore.add("§6▶ Klicke um zu spielen!");
			
			if(targetServer.contains("-")) {
				int on = online.get(targetServer.split("-")[0]);
				lore.add("§7Momentan spiel" + (on == 1 ? "t" : "en") + " §e" + on + " Lama" + (on != 1 ? "s" : ""));
			}
			
			ItemMeta meta = stack.getItemMeta();
			meta.setLore(lore);
			stack.setItemMeta(meta);
			
			inv.setItem(slot, stack);
			
		}
		
		ItemStack stack = Methods.getItem(Material.STAINED_GLASS_PANE, 1, 0, "§e§lBald...", "  ", "§7Wir arbeiten schon an neuen Spielen!");
		for(int slots : soon) {
			inv.setItem(slots-1, stack);
		}
		
		p.openInventory(inv);
	}
	
	private class ServerItem {
		
		String name;
		Material mat;
		int sid;
		public List<String> lore;
		public String targetServer;
		
		public ServerItem(String name, Material mat, int sid, List<String> lore, String server) {
			this.name = name;
			this.mat = mat;
			this.sid = sid;
			this.lore = lore;
			this.targetServer = server;
		}
		
		public ItemStack getRawItemStack() {
			ItemStack stack = new ItemStack(mat, 1, (short) sid);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(name);
			stack.setItemMeta(meta);
			return stack;
		}
		
	}
	
}
