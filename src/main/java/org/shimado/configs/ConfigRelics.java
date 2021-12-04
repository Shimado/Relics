package org.shimado.configs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.bukkit.configuration.file.YamlConfiguration;
import org.shimado.relics.MainRelics;

public class ConfigRelics {
	
	private static YamlConfiguration config;
	
	public static YamlConfiguration getConfig() {
		return config;
	}
	
	private static MainRelics plugin;
	public ConfigRelics(MainRelics pluginA) {
		plugin = pluginA;
	}

	public static void createFiles() {
		File file = new File(plugin.getDataFolder(), "config.yml");
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
				writer.write("#Hello. This is a configuration file. Here you can change values ​​and messages\r\n");
				writer.write("#CREATOR MINESHIMADO/ DONALD ROLE PLAY PACK/ 2021\r\n");
				writer.write("#YOUTUBE CHANNEL - https://www.youtube.com/channel/UCmyRPL02-857O0QSnQJkx5Q\r\n");
				writer.write("#DISCORD - https://discord.gg/nwAzU8x\r\n");
				writer.write("#DONATIONS - https://www.donationalerts.com/r/oloxich\r\n");
				writer.write("\r\n");
				writer.write("\r\n");
				writer.write("#SPAWN CHANCES\r\n");
				writer.write("\r\n");
				writer.write("Mob Vindicator Chance Spawn: 0.1\r\n");
				writer.write("Mob Wolf Chance Spawn: 0.1\r\n");
				writer.write("Mob Spider Chance Spawn: 0.1\r\n");
				writer.write("Mob Nether Chance Spawn: 0.1\r\n");
				writer.write("Mob Evoker Chance Spawn: 0.1\r\n");
				writer.write("Mob Golem Chance Spawn: 0.1\r\n");
				writer.write("Mob Silverfish Chance Spawn: 0.1\r\n");
				writer.write("Mob Skeleton Chance Spawn: 0.1\r\n");
				writer.write("Mob Blaze Chance Spawn: 0.1\r\n");
				writer.write("Mob Phantom Chance Spawn: 0.1\r\n");
				writer.write("\r\n");
				writer.write("#DROP CHANCES\r\n");
				writer.write("\r\n");
				writer.write("Chance of Arthurs Sword: 8.0\r\n");
				writer.write("Chance of Thors Hammer: 3.0\r\n");
				writer.write("Chance of Zeus Bolt: 3.0\r\n");
				writer.write("Chance of Radiance: 5.0\r\n");
				writer.write("Chance of Rudge Hook: 12.0\r\n");
				writer.write("Chance of Staff of Arachne: 8.0\r\n");
				writer.write("Chance of Harkons Sword: 8.0\r\n");
				writer.write("Chance of Gatling Machine Gun: 3.0\r\n");
				writer.write("Chance of Moses boots: 20.0\r\n");
				writer.write("Chance of Midas: 8.0\r\n");
				writer.write("Chance of Staff of Death: 3.0\r\n");
				writer.write("Chance of Staff of Ra: 2.0\r\n");
				writer.write("Chance of Warlord axes: 3.0\r\n");
				writer.write("Chance of Neptunes: 2.0\r\n");
				writer.write("Chance of Basher: 20.0\r\n");
				writer.write("Chance of Splash: 10.0\r\n");
				writer.write("Chance of Shield: 5.0\r\n");
				writer.write("Chance of Chestplate: 5.0\r\n");
				writer.write("Chance of Shadowblade: 10.0\r\n");
				writer.write("Chance of Icarus: 2.0\r\n");
				writer.write("Chance of Bedrock: 0.1\r\n");
				writer.write("\r\n");
				writer.write("#SOME PARAMS\r\n");
				writer.write("\r\n");
				writer.write("Spawn mob and drop them: true\r\n");
				writer.write("Curse of Vanishing: true\r\n");
				writer.write("Arturs Sword Chance PickUp: 20\r\n");
				writer.write("Thors Hammer Chance PickUp: 0.01\r\n");
				writer.write("Zeus Ultimate Cooldown: 60\r\n");
				writer.write("Vampirism Precent: 50\r\n");
				writer.write("Machine Gun Damage: 8.0\r\n");
				writer.write("Rocket Launcher Damage: 20\r\n");
				writer.write("Cooldown Berserk: 120\r\n");
				writer.write("Chance of Basher Stun: 30\r\n");
				writer.write("\r\n");
				writer.write("Elytra Launch Speed: 0.5\r\n");
				writer.write("Elytra Speed: 4");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
}