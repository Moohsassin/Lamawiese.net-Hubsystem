package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.Moohsassin.LamaLobbys.Methods;
import net.md_5.bungee.api.ChatColor;

public class Event_ConnectThroughSign implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			Block b = e.getClickedBlock();
			
			if(b != null && b.getState() instanceof Sign) {
					
				Sign s = (Sign) b.getState();
				Player p = e.getPlayer();
				
				if(s.getLine(1).equalsIgnoreCase("§8§lWarte auf")) {
					
					p.sendMessage("§8Ein neuer Server ist gleich verfügbar!");
					
				} else if(s.getLine(0).startsWith("§nBetrete ")) {
					
					String[] online = ChatColor.stripColor(s.getLine(1)).split("/");
					if(Integer.parseInt(online[0]) == Integer.parseInt(online[1])) {
						if(p.getScoreboard().getEntryTeam(p.getName()).getPrefix().startsWith("§9")) {
							p.sendMessage("§cLeider sind alle Slots auf diesem Server belegt.");
							p.sendMessage("§cDu willst immer auf die vollen Server? Dann kaufe dir Premium:");
							p.sendMessage("§c§nhttp://store.lamawiese.net");
							return;
						}
					}
					
					Methods.sendToServer(p, s.getLine(0).replaceAll("§nBetrete ", ""));
					
				}
				
			}
			
		}
		
	}
	
}
