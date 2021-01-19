package com.draconequus.orensoar;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_ONSL implements CommandExecutor {
	private final OreNSoar plugin;

	public Command_ONSL(OreNSoar plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<String> Errors = new ArrayList<String>();
		Errors.addAll(plugin.getConfig().getStringList("Errors"));
		List<String> formatError0 = new ArrayList<String>();
		List<String> formatError1 = new ArrayList<String>();
		List<String> formatError2 = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			formatError0.add(Errors.get(i).replace("&&", "¤"));
			formatError1.add(formatError0.get(i).replace("&", "§"));
			formatError2.add(formatError1.get(i).replace("¤", "&"));
		}
		
		boolean playerSender;
		Player player = null;
		
		if (sender instanceof Player) {
			playerSender = true;
			player = (Player) sender;
		}
		else {
			playerSender = false;
		}

		if (args.length > 0 && playerSender) {
			player = (Player) sender;
			player.sendMessage(formatError2.get(5));
			return true;
		}
		else if (args.length > 0 && !(playerSender)) {
			Bukkit.getConsoleSender().sendMessage(formatError2.get(5));
			return true;
		}
		// loop through the arraylist to get the longest length, then loop again to print the results, but add extra spacing to shorter lines so it all evens out
		List<String> Currencies = new ArrayList<String>();
		Currencies.addAll(plugin.getConfig().getStringList("Currencies"));
		int length = 0;
		int spacing = 0;
		String space = " ";
		
		for (int i = 0; i < Currencies.size(); i++) {
			int maxLength = Currencies.get(i).split(" ")[0].length();
			if (maxLength > length) {
				length = maxLength;
			}
		}
		
		if (playerSender) {
			player.sendMessage("Currency" + StringUtils.repeat(" ", length - 8) + "| Amount\n" + StringUtils.repeat("-", (int) (length * 1.71)));
			for (int i = 0; i < Currencies.size(); i++) {
				int maxLength = Currencies.get(i).split(" ")[0].length();
	
				if (maxLength < length) {
					spacing = length - maxLength;
					space = StringUtils.repeat(" ", spacing);
					player.sendMessage(Currencies.get(i).replace(" ", space + " | "));
				}
				else {
					player.sendMessage(Currencies.get(i).replace(" ", " | "));
				}
			}
		}
		else {
			Bukkit.getConsoleSender().sendMessage("Currency" + StringUtils.repeat(" ", length - 7) + "| Amount\n" + StringUtils.repeat("-", (int) (length * 1.6)));
			for (int i = 0; i < Currencies.size(); i++) {
				int maxLength = Currencies.get(i).split(" ")[0].length();
	
				if (maxLength < length) {
					spacing = length - maxLength;
					space = StringUtils.repeat(" ", spacing);
					Bukkit.getConsoleSender().sendMessage(Currencies.get(i).replace(" ", space + " | "));
				}
				else {
					Bukkit.getConsoleSender().sendMessage(Currencies.get(i).replace(" ", " | "));
				}
			}
		}

		return true;
	}

}
