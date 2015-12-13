package de.Moohsassin.LamaLobbys.SignManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.Moohsassin.LamaLobbys.LamaLobbys;
import de.Moohsassin.LamaLobbys.ServerManager;
import de.Moohsassin.LamaLobbys.ServerManager.GameStatus;

public class SignManager {

	static String game;
	
	static ArrayList<Location> notInUse = new ArrayList<>();
	static HashMap<String, Location> serverLocation = new HashMap<>();
	
	static BukkitTask run = null;
	
	public static void stop() {
		run.cancel();
	}
	
	public static void run() {
		
		prepare();
		update();
		
	}
	
	public static void prepare() {
		
		game = Bukkit.getMotd().split("-")[0];
		
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("plugins", "signs.yml"));
		
		int x1 = cfg.getInt("X1"),
			y1 = cfg.getInt("Y1"),
			z1 = cfg.getInt("Z1"),
			x2 = cfg.getInt("X2"),
			y2 = cfg.getInt("Y2"),
			z2 = cfg.getInt("Z2");
		
		int highX, highY, highZ;
		int lowX, lowY, lowZ;
		
		highX = Math.max(x1, x2);
		highY = Math.max(y1, y2);
		highZ = Math.max(z1, z2);
		
		lowX = Math.min(x1, x2);
		lowY = Math.min(y1, y2);
		lowZ = Math.min(z1, z2);
		
		World w = Bukkit.getWorld("world");
		
		for(int x = lowX; x <= highX; x ++) {
			for(int y = lowY; y <= highY; y ++) {
				for(int z = lowZ; z <= highZ; z ++) {
					
					Location loc = new Location(w, x, y, z);
					if(loc.getBlock() != null && loc.getBlock().getState() instanceof Sign) {
						notInUse.add(loc);
					}
					
				}
			}
		}
		
	}
	
	public static void update() {
		
		if(notInUse.isEmpty()) return;
		
		for(Location locs : notInUse) {
			Sign s = (Sign) locs.getBlock().getState();
			s.setLine(0, ""); s.setLine(1, "§8§lWarte auf"); s.setLine(2, "§8§lServer..."); s.setLine(3, "");
			s.update();
		}
		
		run = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(Server server : ServerManager.getAllServerFromGame(game)) {
					
					// Wenn der Server schon auf einem Schild ist, ...
					if(serverLocation.containsKey(server.name)) {
						
						Sign s = (Sign) serverLocation.get(server.name).getBlock().getState();
						// Darf an der Wand bleiben
						if(server.gStatus == GameStatus.LOBBY) {
							s.setLine(0, "§nBetrete " + server.name); s.setLine(1, "§a§l" + server.online + "/" + server.max); s.setLine(2, server.motd); s.setLine(3, "§8§l● Lobby ●");
						} else {
							s.setLine(0, ""); s.setLine(1, "§8§lWarte auf"); s.setLine(2, "§8§lServer..."); s.setLine(3, "");
							serverLocation.remove(server.name);
							notInUse.add(s.getLocation());
						}
						s.update();
						
						continue;
						
					}
					
					// Server kann an die Wand
					if(server.gStatus == GameStatus.LOBBY) {
						
						// Kein Schild an Wand frei...
						if(notInUse.isEmpty()) continue;
						
						Location loc = notInUse.get(0);
						notInUse.remove(0);
						serverLocation.put(server.name, loc);
						
						Sign s = (Sign) loc.getBlock().getState();
						
						s.setLine(0, "§2▀▀▀▀▀▀▀▀▀▀"); s.setLine(1, "§8§l§n- " + server.name + " -"); s.setLine(2, "§8§lstartet"); s.setLine(3, "§2▄▄▄▄▄▄▄▄▄▄");
						s.update();
						
						continue;
						
					}
					
				}
				
			}
		}.runTaskTimer(LamaLobbys.instance, 30, 30);
		
	}
	
}
