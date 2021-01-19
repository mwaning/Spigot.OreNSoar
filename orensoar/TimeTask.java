package com.draconequus.orensoar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class TimeTask {
	private final OreNSoar plugin;
	
	public TimeTask(OreNSoar plugin) {
		this.plugin = plugin;
	}


	public static void timeRemaining(Player player, int minutes, OreNSoar plugin) {
		List<String> Alerts = new ArrayList<String>();
		Alerts.addAll(plugin.getConfig().getStringList("Alerts"));		
		List<String> formatAlert0 = new ArrayList<String>();
		List<String> formatAlert1 = new ArrayList<String>();
		List<String> formatAlert2 = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			formatAlert0.add(Alerts.get(i).replace("&&", "¤"));
			formatAlert1.add(formatAlert0.get(i).replace("&", "§"));
			formatAlert2.add(formatAlert1.get(i).replace("¤", "&"));
		}
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				player.sendMessage((formatAlert2.get(0)));
				cancelFlight(player, plugin);
			}
			
		}; timer.schedule(task, minutes * 60000 - 10000);
		
	}
	
	public static void cancelFlight(Player player, OreNSoar plugin) {
		List<String> Alerts = new ArrayList<String>();
		Alerts.addAll(plugin.getConfig().getStringList("Alerts"));		
		List<String> formatAlert0 = new ArrayList<String>();
		List<String> formatAlert1 = new ArrayList<String>();
		List<String> formatAlert2 = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			formatAlert0.add(Alerts.get(i).replace("&&", "¤"));
			formatAlert1.add(formatAlert0.get(i).replace("&", "§"));
			formatAlert2.add(formatAlert1.get(i).replace("¤", "&"));
		}
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				player.sendMessage((formatAlert2.get(1)));
				player.setAllowFlight(false);
				
				ItemStack[] armor = player.getInventory().getArmorContents();
				boolean hasBoots = false;
				boolean hasEnchantment = false;

				if (armor[0] != null && armor[0].containsEnchantment(Enchantment.PROTECTION_FALL)) {
					hasBoots = true;
					hasEnchantment = true;
				}
				else if (armor[0] != null && !(armor[0].containsEnchantment(Enchantment.PROTECTION_FALL))) {
					hasBoots = true;
					hasEnchantment = false;
				}
				else {
					hasBoots = false;
					hasEnchantment = false;
				}
				
				if (hasBoots && !(hasEnchantment)) {
					armor[0].addEnchantment(Enchantment.PROTECTION_FALL, 4);
					Material bootsMaterial = armor[0].getType();
					removeEnchantment(player, armor, bootsMaterial);
				}
			}
		}; timer.schedule(task, 10000);
	}
	
	public static void removeEnchantment(Player player, ItemStack[] armor, Material bootsMaterial) {
		Timer timer = new Timer();
		
			TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				if (armor[0] != null && armor[0].containsEnchantment(Enchantment.PROTECTION_FALL)) {
					armor[0].removeEnchantment(Enchantment.PROTECTION_FALL);
				}
				else {
					ItemStack[] items = player.getInventory().getContents();

					for (int i = 0; i < 36; i++) {
						if (items[i].getType().equals(bootsMaterial)) {
							if (items[i].containsEnchantment(Enchantment.PROTECTION_FALL)) {
								items[i].removeEnchantment(Enchantment.PROTECTION_FALL);
								break;
							}
						}
					}
				}
			}
		}; timer.schedule(task, 3500);
	}

}