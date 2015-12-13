package de.Moohsassin.LamaLobbys;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Methods {

	public static void sendToServer(Player p, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		p.sendPluginMessage(LamaLobbys.instance, "BungeeCord", out.toByteArray());
	}
	
	public static ItemStack getItem(Material mat, int amount, int data, String name) {
		
		ItemStack stack = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		stack.setItemMeta(meta);
		
		return stack;
		
	}
	
	public static ItemStack getItem(Material mat, int amount, int data, String name, String... lore) {
		
		ItemStack stack = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		List<String> lore_ = new ArrayList<>();
		for(String s : lore) lore_.add(s);
		meta.setLore(lore_);
		stack.setItemMeta(meta);
		
		return stack;
		
	}
	
}
