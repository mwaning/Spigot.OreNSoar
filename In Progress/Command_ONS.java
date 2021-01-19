package com.draconequus.orensoar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command_ONS implements CommandExecutor {
	private final OreNSoar plugin;
	
	public Command_ONS(OreNSoar plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<String> Errors = new ArrayList<String>();
		Errors.addAll(plugin.getConfig().getStringList("Errors"));
		List<String> Alerts = new ArrayList<String>();
		Alerts.addAll(plugin.getConfig().getStringList("Alerts"));		
		List<String> formatError0 = new ArrayList<String>();
		List<String> formatError1 = new ArrayList<String>();
		List<String> formatError2 = new ArrayList<String>();
		List<String> formatAlert0 = new ArrayList<String>();
		List<String> formatAlert1 = new ArrayList<String>();
		List<String> formatAlert2 = new ArrayList<String>();

		for (int i = 0; i < 9; i++) {
			formatError0.add(Errors.get(i).replace("&&", "¤"));
			formatError1.add(formatError0.get(i).replace("&", "§"));
			formatError2.add(formatError1.get(i).replace("¤", "&"));
		}
		for (int i = 0; i < 5; i++) {
			formatAlert0.add(Alerts.get(i).replace("&&", "¤"));
			formatAlert1.add(formatAlert0.get(i).replace("&", "§"));
			formatAlert2.add(formatAlert1.get(i).replace("¤", "&"));
		}
		
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(formatError2.get(0));
			return true;
		}
			
		Player player = (Player) sender;
		Player target = player;
		List<String> Currencies = new ArrayList<String>();
		Currencies.addAll(plugin.getConfig().getStringList("Currencies"));
		ItemStack is;
		Material m;
		int price = 0;
		int minutes = 0;
		String item;
		
		if (args.length > 3) { 
			player.sendMessage((formatError2.get(2)));
			return true;
		}
		
		if (args.length < 2) { 
			player.sendMessage(formatError2.get(1));
			return true;
		}
		
		try { 
			Integer.parseInt(args[1]);
		}
		catch (Exception exception) {
			player.sendMessage((formatError2.get(3)));
			return true;
		}
			
		if (Integer.parseInt(args[1]) < 1) {
			player.sendMessage((formatError2.get(4)));
			return true;
		}
		
		if (args.length > 2 && Bukkit.getPlayer(args[2]) != null) {
				target = Bukkit.getPlayer(args[2]);
		}
		else if (args.length > 2 && Bukkit.getPlayer(args[2]) == null) {
				String formatSplit0[] = formatError2.get(6).split("%target%");
				String formatSplit1 = formatSplit0[0] + args[2] + formatSplit0[1];
				player.sendMessage((formatSplit1));
				return true;
		}
		
		for (int i = 0; i < Currencies.size(); i++) {
			item = Currencies.get(i).split(" ")[0].toLowerCase();
			price = Integer.parseInt(Currencies.get(i).split(" ")[1]);
			m = Material.matchMaterial(item);
			minutes = Integer.parseInt(args[1]);
			is = new ItemStack(m, price * minutes);

			if (args[0].toLowerCase().equals(item) && player.getInventory().contains(m) && player == target) {
				String[] formatSplit0 = formatAlert2.get(2).split("%minutes%");
				String formatSplit1 = formatSplit0[0] + minutes + formatSplit0[1];
				player.sendMessage(formatSplit1);
				
				player.getInventory().removeItem(is);
				player.setAllowFlight(true);
				TimeTask.timeRemaining(player, minutes, plugin);
				return true;
			}
			else if (args[0].toLowerCase().equals(item) && player.getInventory().contains(m) && player != target) {
				String sPlayer = String.valueOf(player).replace("CraftPlayer{name=", "").replace("}", "");
				String sTarget = String.valueOf(target).replace("CraftPlayer{name=", "").replace("}", "");;
				String[] formatSplit0 = formatAlert2.get(3).split("%user%");
				String formatSplit1 = formatSplit0[0] + sPlayer + formatSplit0[1];
				String[] formatSplit2 = formatSplit1.split("%minutes%");
				String formatSplit3 = formatSplit2[0] + minutes + formatSplit2[1];
				target.sendMessage(formatSplit3);
				
				String[] formatSplit4 = formatAlert2.get(4).split("%target%");
				String formatSplit5 = formatSplit4[0] + sTarget + formatSplit4[1];
				String[] formatSplit6 = formatSplit5.split("%minutes%");
				String formatSplit7 = formatSplit6[0] + minutes + formatSplit6[1];
				player.sendMessage(formatSplit7);
				
				player.getInventory().removeItem(is);
				target.setAllowFlight(true);
				TimeTask.timeRemaining(target, minutes, plugin);
				return true;
			}
			else if (args[0].toLowerCase().equals(item) && !(player.getInventory().contains(m))) {
				String[] formatSplit0 = formatError2.get(8).split("%amount%");
				String formatSplit1 = formatSplit0[0] + price * minutes + formatSplit0[1];
				String[] formatSplit2 = formatSplit1.split("%currency%");
				String formatSplit3 = formatSplit2[0] + item + formatSplit2[1];
				String[] formatSplit4 = formatSplit3.split("%minutes%");
				String formatSplit5 = formatSplit4[0] + minutes + formatSplit4[1];
				player.sendMessage(formatSplit5);
				return true;
			}
			else if (i == Currencies.size() - 1) {
				String[] formatSplit0 = formatError2.get(7).split("%currency%");
				String formatSplit1 = formatSplit0[0] + args[0] + formatSplit0[1];
				player.sendMessage(formatSplit1);
				return true;
			}
		}
		return true;
	}
}
