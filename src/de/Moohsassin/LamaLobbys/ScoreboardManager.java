package de.Moohsassin.LamaLobbys;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

	static BukkitTask task;
	public static int online = 1;
	
	public static void update() {
		int n = ServerManager.sql.getInt("OnlineCounts", "Name", "GLOBAL", "Count");
		if(n != -1) online = n; 
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.getScoreboard().getTeam("on").setSuffix("" + online);
		}
	}
	
	public static void startToUpdate() {
		task = new BukkitRunnable() {@Override public void run() {
			update();
		}}.runTaskTimer(LamaLobbys.instance, 20 * 5, 20 * 5);
	}
	
	public static void stopToUpdate() {
		task.cancel();
	}
	
	public static void setScoreboard(Player p) {
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		p.setScoreboard(board);
		
	}
	
	public static void setColor(Player p) {
		
		Scoreboard board = p.getScoreboard();
		String color = PlayerDatas.getChatColor(p.getUniqueId());
		String rank = PlayerDatas.getRank(p.getName());
		String teamName = PlayerDatas.getRankValueForTab(rank) + rank;
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			
			Team team = players.getScoreboard().getTeam(teamName);
			if(team == null) team = players.getScoreboard().registerNewTeam(teamName);
			team.addEntry(p.getName());
			team.setPrefix(color);
			
			Team pTeam = players.getScoreboard().getEntryTeam(players.getName());
			Team bTeam = board.getTeam(pTeam.getName());
			if(bTeam == null) bTeam = board.registerNewTeam(pTeam.getName());
			bTeam.addEntry(players.getName());
			bTeam.setPrefix(pTeam.getPrefix());
			
		}
	}
	
	public static void setSidebar(Player p) {
		
		Scoreboard board = p.getScoreboard();
		Objective score = board.registerNewObjective("side", "dummy");
		
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		score.setDisplayName("§6§lLamawiese");
		
		Team on = board.registerNewTeam("on");
		on.addEntry("§e§l§e"); on.setSuffix("" + online);
		
		Team coins = board.registerNewTeam("coins");
		coins.addEntry("§5§l§e"); coins.setSuffix(PlayerDatas.getCoins(p.getName()) + "");
		
		score.getScore("§1 ").setScore(13);
		score.getScore("§7Server:").setScore(12);
		score.getScore("§e" + Bukkit.getMotd()).setScore(11);
		score.getScore("§2 ").setScore(10);
		score.getScore("§7Lamacoins:").setScore(9);
		score.getScore("§5§l§e").setScore(8);
		score.getScore("§3 ").setScore(7);
		score.getScore("§7Rang:").setScore(6);
		score.getScore("§e" + PlayerDatas.getRank(p.getName())).setScore(5);
		score.getScore("§4 ").setScore(4);
		score.getScore("§7Online Lamas:").setScore(3);
		score.getScore("§e§l§e").setScore(2);
		score.getScore("§5 ").setScore(1);
		score.getScore("§6play.lamawiese.net").setScore(0);
		
	}	
}