package com.draconequus.orensoar;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class OreNSoar extends JavaPlugin {
	Plugin plugin = this;
	private File file;
	private FileConfiguration config;

    @Override
    public void onEnable() {
    	//this.saveDefaultConfig();
    	this.getCommand("ons").setExecutor(new Command_ONS(this));
    	this.getCommand("onsl").setExecutor(new Command_ONSL(this));

    		createConfig();
    }
    
    @Override
    public void onDisable() {

    }
    
    private void createConfig() {
    	file = new File(getDataFolder(), "config.yml");
    	if (!file.exists()) {
    		file.getParentFile().mkdirs();
    		saveResource("config.yml", false);
    	}
    	
    	config = new YamlConfiguration();
    	try {
    		config.load(file);
    	} catch (IOException | InvalidConfigurationException exception) {
    		exception.printStackTrace();
    	}
    }
    
    

}
 