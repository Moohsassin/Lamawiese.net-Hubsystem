package de.Moohsassin.LamaLobbys.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.Moohsassin.LamaLobbys.Items;
import de.Moohsassin.LamaLobbys.LamaLobbys;
import de.Moohsassin.LamaLobbys.PlayerDatas;
import de.Moohsassin.LamaLobbys.ScoreboardManager;

public class Event_Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		final Player p = e.getPlayer();
		
		e.setJoinMessage(null);
		
		p.teleport(Bukkit.getWorld("world").getSpawnLocation());
		p.setGameMode(GameMode.ADVENTURE);
		
		if(LamaLobbys.isMainHub) {
			p.setWalkSpeed(1f);
			PlayerDatas.upDatePlayer(p);
		}
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			p.showPlayer(players);
		}
		
		ScoreboardManager.setScoreboard(p);
		ScoreboardManager.setColor(p);
		ScoreboardManager.setSidebar(p);
		ScoreboardManager.update();
		
		p.getInventory().clear();
		
		p.getInventory().setItem(0, Items.COMPASS.stack);
		p.getInventory().setItem(8, Items.HIDER_ENABLE.stack);
		
		p.updateInventory();
		
		new BukkitRunnable() {@Override public void run() {
			for(String s : Event_Hider.hiddenEnabled) {
				Bukkit.getPlayer(s).hidePlayer(p);
			}
		}}.runTaskLater(LamaLobbys.instance, 5);
		
	}
	
}
