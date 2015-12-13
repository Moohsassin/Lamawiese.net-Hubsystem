package de.Moohsassin.LamaLobbys.Events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.Moohsassin.LamaLobbys.Items;
import de.Moohsassin.LamaLobbys.LamaLobbys;

public class Event_Hider implements Listener {

	public static HashMap<String, Long> timeLeft = new HashMap<>();
	public static ArrayList<String> hiddenEnabled = new ArrayList<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand() != null) {
				
				if(timeLeft.containsKey(p.getName())) {
					Long left = timeLeft.get(p.getName());
					if(left > System.currentTimeMillis() / 1000) {
						p.sendMessage(LamaLobbys.pr + "§cBitte warte bevor du dieses Item wieder benutzt!");
						return;
					}
				}
				
				if(p.getItemInHand().equals(Items.HIDER_ENABLE.stack)) {
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						p.hidePlayer(players);
					}
					hiddenEnabled.add(p.getName());
					p.setItemInHand(Items.HIDER_DISABLE.stack);
					p.sendMessage(LamaLobbys.pr + "Alle Lamas wurden §cversteckt!");
					p.updateInventory();
					timeLeft.put(p.getName(), (System.currentTimeMillis() / 1000) + 5); 
					
				} else if(p.getItemInHand().equals(Items.HIDER_DISABLE.stack)) {

					for(Player players : Bukkit.getOnlinePlayers()) {
						p.showPlayer(players);
					}
					hiddenEnabled.remove(p.getName());
					p.setItemInHand(Items.HIDER_ENABLE.stack);
					p.sendMessage(LamaLobbys.pr + "§eAlle Lamas sind nun wieder sichtbar!");
					p.updateInventory();
					timeLeft.put(p.getName(), (System.currentTimeMillis() / 1000) + 5);
					
				}
				
			}
		}
		
	}
	
}
