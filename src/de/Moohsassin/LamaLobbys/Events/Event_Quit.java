package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.Moohsassin.LamaLobbys.LamaLobbys;
import de.Moohsassin.LamaLobbys.ScoreboardManager;

public class Event_Quit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		e.setQuitMessage(null);
		Event_Hider.timeLeft.remove(e.getPlayer().getName());
		Event_Hider.hiddenEnabled.remove(e.getPlayer().getName());
		
		new BukkitRunnable() {@Override public void run() {
			
			ScoreboardManager.update();
			
		}}.runTaskLater(LamaLobbys.instance, 2);
	}
	
}
