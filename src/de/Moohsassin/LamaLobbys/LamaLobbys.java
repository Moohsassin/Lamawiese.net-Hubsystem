package de.Moohsassin.LamaLobbys;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

import de.Moohsassin.LamaLobbys.SignManager.SignManager;

public class LamaLobbys extends JavaPlugin {

	public static Plugin instance;
	public static boolean isMainHub = false;
	public static String pr = "§6Lamawiese §8┃ §7";
	public static ServerSelector serverSelector;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		isMainHub = Bukkit.getMotd().contains("MAIN");
		
		registerEvents();
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		PlayerDatas.openConnection();
		ServerManager.openConnection();
		ScoreboardManager.startToUpdate();
		
		if(!isMainHub) {
			SignManager.run();
		}
		
		serverSelector = new ServerSelector();
		
	}
	
	@Override
	public void onDisable() {
		if(!isMainHub) {
			SignManager.stop();
		}
		ScoreboardManager.stopToUpdate();
		ServerManager.closeConnection();
		PlayerDatas.closeConnection();
	}
	
	private void registerEvents() {
		try {
			PluginManager pm = Bukkit.getPluginManager();
			for(ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader()).getTopLevelClasses("de.Moohsassin.LamaLobbys.Events")) {
				Class<?> clazz = Class.forName(classInfo.getName());
				if(Listener.class.isAssignableFrom(clazz)) {
					pm.registerEvents((Listener) clazz.newInstance(), this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
