package org.shimado.classes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;

public class Inventory {
	
	private static YamlConfiguration messages = MessagesAndLore.getConfig();

	public static void createInventory(Player player) {
		org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 45, ColorText.title(messages.getString("Relics shop title")));
		inv.setItem(10, RelicsItems.arthurSword());
		inv.setItem(11, RelicsItems.thorHammer());
		inv.setItem(12, RelicsItems.zeusBolt());
		inv.setItem(13, RelicsItems.radiance());
		inv.setItem(14, RelicsItems.hook());
		inv.setItem(15, RelicsItems.arachne());
		inv.setItem(16, RelicsItems.vampirism());
		inv.setItem(19, RelicsItems.machinegun());
		inv.setItem(20, RelicsItems.waterboots());
		inv.setItem(21, RelicsItems.midas());
		inv.setItem(22, RelicsItems.scythe());
		inv.setItem(23, RelicsItems.rocket());
		inv.setItem(24, RelicsItems.berserk());
		inv.setItem(25, RelicsItems.neptune());
		inv.setItem(28, RelicsItems.basher());
		inv.setItem(29, RelicsItems.splash());
		inv.setItem(30, RelicsItems.shield());
		inv.setItem(31, RelicsItems.bristleback());
		inv.setItem(32, RelicsItems.shadowblade());
		inv.setItem(33, RelicsItems.icarus());
		inv.setItem(34, RelicsItems.bedrock());
		player.openInventory(inv);
	}
	
}
