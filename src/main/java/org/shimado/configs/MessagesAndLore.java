package org.shimado.configs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.bukkit.configuration.file.YamlConfiguration;
import org.shimado.relics.MainRelics;

public class MessagesAndLore {
	
	private static YamlConfiguration config;
	
	private static MainRelics plugin;
	public MessagesAndLore(MainRelics pluginA) {
		plugin = pluginA;
	}
	
	public static YamlConfiguration getConfig() {
		return config;
	}
	
	public static void createConfig() {
		File file = new File(plugin.getDataFolder(), "messages.yml");
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
				writer.write("Relics shop title: '&4All Relics'\r\n");
				writer.write("You are not the player: '&6Found a relic! <relic>!'\r\n");
				writer.write("Rare mob name: '&6Ancient Guardian'\r\n");
				writer.write("\r\n");
				writer.write("Relic found: '&6Found a relic! <relic>!'\r\n");
				writer.write("Relic lost: '&6&lRelic lost'\r\n");
				writer.write("First Pickup: '&6Founded by <player>'\r\n");
				writer.write("Arthur Sword pickup fail: '&6You are not worthy to wear this sword!'\r\n");
				writer.write("\r\n");
				writer.write("#ITEMS - NAMES AND LORES\r\n");
				writer.write("\r\n");
				writer.write("Artur Sword: '&6♣ King Arthur Sword ♣'\r\n");
				writer.write("Artur Lore:\r\n");
				writer.write("- '&6☼ &8Lost sword of the English king.'\r\n");
				writer.write("- '&6☼ &8Only the chosen one can pick it up.'\r\n");
				writer.write("- '&6☼ &8All the unworthy will fall when they try'\r\n");
				writer.write("- '  &8to raise it.'\r\n");
				writer.write("- '&6&k► &r&5Spawns a wolf on hits &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Thors Hammer: '&6♣ Thors Hammer ♣'\r\n");
				writer.write("Thors Lore:\r\n");
				writer.write("- '&6☼ &8Hammer of the Norse God.'\r\n");
				writer.write("- '&6☼ &8Only the chosen one can pick it up.'\r\n");
				writer.write("- '&6☼ &8All the unworthy will not be able'\r\n");
				writer.write("- '  &8to raise it.'\r\n");
				writer.write("- '&6&k► &r&5Spawns a thunder on hits &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Zeus Bolt: '&6♣ Zeus Bolt ♣'\r\n");
				writer.write("Zeus Lore:\r\n");
				writer.write("- '&6☼ &8Lightning Weapon of the Gods.'\r\n");
				writer.write("- '&6☼ &8Only Zeus owns it perfectly.'\r\n");
				writer.write("- '&6☼ &8Found on Olympus.'\r\n");
				writer.write("- '&6&k► &r&5Spawns a thunder around and on hits &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Radiance: '&6♣ Radiance ♣'\r\n");
				writer.write("Radiance Lore:\r\n");
				writer.write("- '&6☼ &8A sword that burns out everything in its path.'\r\n");
				writer.write("- '&6☼ &8All creatures light up beside you.'\r\n");
				writer.write("- '&6☼ &8Found in Caves.'\r\n");
				writer.write("- '&6&k► &r&5Ignites all creatures in the radius &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Rudge Hook: '&6♣ Rudge Hook ♣'\r\n");
				writer.write("Rudge Hook Lore:\r\n");
				writer.write("- '&6☼ &8Launches a harpoon that attracts you.'\r\n");
				writer.write("- '&6☼ &8All creatures get stun.'\r\n");
				writer.write("- '&6☼ &8Found in Caves.'\r\n");
				writer.write("- '&6&k► &r&5Attracts target to you &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Staff of Arachne: '&6♣ Staff of Arachne ♣'\r\n");
				writer.write("Staff of Arachne Lore:\r\n");
				writer.write("- '&6☼ &8Staff of the spider queen.'\r\n");
				writer.write("- '&6☼ &8Summons Minions.'\r\n");
				writer.write("- '&6☼ &8Found in a spiders nest.'\r\n");
				writer.write("- '&6&k► &r&5Summons spiders and web &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Harkons Sword: '&6♣ Harkons sword ♣'\r\n");
				writer.write("Harkons Sword Lore:\r\n");
				writer.write("- '&6☼ &8Lone Kings Lost Sword.'\r\n");
				writer.write("- '&6☼ &8Has a higher power.'\r\n");
				writer.write("- '&6☼ &8Takes health away from the victim.'\r\n");
				writer.write("- '&6&k► &r&5Vampirism &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Gatling Machine: '&6♣ Gatling machine gun ♣'\r\n");
				writer.write("Gatling Machine Lore:\r\n");
				writer.write("- '&6☼ &8The fastest weapon in the game.'\r\n");
				writer.write("- '&6☼ &8Has great firepower.'\r\n");
				writer.write("- '&6☼ &8No ammo required.'\r\n");
				writer.write("- '&6&k► &r&5Shoots snowballs with damage &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Moses Boots: '&6♣ Moses boots ♣'\r\n");
				writer.write("Moses Boots Lore:\r\n");
				writer.write("- '&6☼ &8The very shoes of Moses.'\r\n");
				writer.write("- '&6☼ &8Can walk on water.'\r\n");
				writer.write("- '&6☼ &8Can walk on lava.'\r\n");
				writer.write("- '&6&k► &r&5You can walk on water and lava &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Midas: '&6♣ Midas ♣'\r\n");
				writer.write("Midas Lore:\r\n");
				writer.write("- '&6☼ &8A tool of greed and vanity.'\r\n");
				writer.write("- '&6☼ &8Turns everything into gold.'\r\n");
				writer.write("- '&6☼ &8Found in the desert.'\r\n");
				writer.write("- '&6&k► &r&5Converts creatures to gold &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Scythe of Death: '&6♣ Scythe of Death ♣'\r\n");
				writer.write("Scythe of Death Lore:\r\n");
				writer.write("- '&6☼ &8The scythe that devastates everyone.'\r\n");
				writer.write("- '&6☼ &8Blinds everyone and poisons everyone.'\r\n");
				writer.write("- '&6☼ &8Found in the swamp.'\r\n");
				writer.write("- '&6&k► &r&5Inflicts a debuff on everyone &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Staff of Ra: '&6♣ Staff of Ra ♣'\r\n");
				writer.write("Staff of Ra Lore:\r\n");
				writer.write("- '&6☼ &8Staff of the Egyptian God.'\r\n");
				writer.write("- '&6☼ &8Summons a solar ball.'\r\n");
				writer.write("- '&6☼ &8Found in the desert.'\r\n");
				writer.write("- '&6&k► &r&5Summons huge solar ball &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Warlord's axes: '&6♣ Warlords axes ♣'\r\n");
				writer.write("Warlord's axes Lore:\r\n");
				writer.write("- '&6☼ &8Alone - nothing, paired - everything.'\r\n");
				writer.write("- '&6☼ &8Two axes giving you tremendous power.'\r\n");
				writer.write("- '&6☼ &8Found in the forest.'\r\n");
				writer.write("- '&6&k► &r&5Give you effects &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Neptune's trident: '&6♣ Neptunes trident ♣'\r\n");
				writer.write("Neptune's trident Lore:\r\n");
				writer.write("- '&6☼ &8Trident of the Sea King.'\r\n");
				writer.write("- '&6☼ &8Gives flight from the ground.'\r\n");
				writer.write("- '&6☼ &8And also a lightning strike.'\r\n");
				writer.write("- '&6&k► &r&5Lets you fly &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Basher: '&6♣ Basher ♣'\r\n");
				writer.write("Basher Lore:\r\n");
				writer.write("- '&6☼ &8Powerful weapon of control.'\r\n");
				writer.write("- '&6☼ &8Will stun the player and give him a daze.'\r\n");
				writer.write("- '&6☼ &8Found in caves.'\r\n");
				writer.write("- '&6&k► &r&5Stuns the player &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Sword of the Waves: '&6♣ Sword of the Waves ♣'\r\n");
				writer.write("Sword of the Waves Lore:\r\n");
				writer.write("- '&6☼ &8Powerful weapon with a big splash.'\r\n");
				writer.write("- '&6☼ &8Deals damage to everyone in the area.'\r\n");
				writer.write("- '&6☼ &8Splash targets take a lot of damage.'\r\n");
				writer.write("- '&6&k► &r&5Splash &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Shield: '&6♣ Captain america shield ♣'\r\n");
				writer.write("Shield Lore:\r\n");
				writer.write("- '&6☼ &8Powerful shield that blocks all damage.'\r\n");
				writer.write("- '&6☼ &8Spawn a wolf on an attacker.'\r\n");
				writer.write("- '&6&k► &r&5Blocks all damage &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Porcupine back: '&6♣ Porcupine back ♣'\r\n");
				writer.write("Porcupine back Lore:\r\n");
				writer.write("- '&6☼ &8Powerful chestplate that blocks damage.'\r\n");
				writer.write("- '&6☼ &8Blocks 2/3 damage from back.'\r\n");
				writer.write("- '&6☼ &8When attacking in the face - like a normal chestplate.'\r\n");
				writer.write("- '&6&k► &r&5Blocks 2/3 damage from back &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("ShadowBlade: '&6♣ ShadowBlade ♣'\r\n");
				writer.write("ShadowBlade Lore:\r\n");
				writer.write("- '&6☼ &8Gives you invisibility.'\r\n");
				writer.write("- '&6☼ &8At the same time, different buffs are given.'\r\n");
				writer.write("- '&6☼ &8Found in the woods.'\r\n");
				writer.write("- '&6&k► &r&5Gives you invisibility &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Icarus: '&6♣ Icarus wings ♣'\r\n");
				writer.write("Icarus Lore:\r\n");
				writer.write("- '&6☼ &8Gives you the ability to start from the ground.'\r\n");
				writer.write("- '&6☼ &8And also keep flying.'\r\n");
				writer.write("- '&6☼ &8Found on Olympus.'\r\n");
				writer.write("- '&6☼ &8Shift + Left Click On Sky'\r\n");
				writer.write("- '&6☼ &8or Left Click when flying!'\r\n");
				writer.write("- '&6&k► &r&5Gives endless flight on left click &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Thanos: '&6♣ Glove of Thanos ♣'\r\n");
				writer.write("Thanos Lore:\r\n");
				writer.write("- '&6☼ &8The most powerful weapon in the game.'\r\n");
				writer.write("- '&6☼ &8Gives complete protection.'\r\n");
				writer.write("- '&6☼ &8And the maximum damage.'\r\n");
				writer.write("- '&6&k► &r&5Protection and damage &6&k◄'\r\n");
				writer.write("\r\n");
				writer.write("Arachne spider: '&4Cave spider'\r\n");
				writer.write("Arthur wolf: '&4Alfa Wolf'\r\n");
				writer.write("Weapon Keeper: '&6Weapon keeper'");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);	
	}

}
