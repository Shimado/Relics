package org.shimado.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.shimado.classes.ColorText;
import org.shimado.classes.Inventory;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;

public class Command implements CommandExecutor{
	
	private static YamlConfiguration messages = MessagesAndLore.getConfig();

	private MainRelics plugin;
	public Command(MainRelics plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("relics").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ColorText.colortext(messages.getString("You are not the player")));
			return true;
		}
		Player player = (Player) sender;
		if(args.length == 0 && player.hasPermission("relics.shop")) {
			Inventory.createInventory(player);
			return true;
		}
		if(args.length == 2 && args[0].equals("give") && NumberUtils.isNumber(args[1]) && player.hasPermission("relics.shop")) {
			player.getInventory().addItem(giveRelic(Integer.parseInt(args[1]) - 1));
			return true;
		}
		if(args.length == 3 && args[0].equals("give") && NumberUtils.isNumber(args[2]) && player.hasPermission("relics.shop")) {
			Player p = Bukkit.getPlayer(args[1]);
			if(p != null && p.isOnline()) {
				p.getInventory().addItem(giveRelic(Integer.parseInt(args[2]) - 1));
			}
			return true;
		}
		return false;
	}
	
	private static ItemStack giveRelic(int index) {
		
		if(index < 0 || index > 20) {
			return new ItemStack(Material.AIR);
		}
		
		List<ItemStack> list = new ArrayList<>();
		list.add(RelicsItems.arthurSword());
		list.add(RelicsItems.thorHammer());
		list.add(RelicsItems.zeusBolt());
		list.add(RelicsItems.radiance());
		list.add(RelicsItems.hook());
		list.add(RelicsItems.arachne());
		list.add(RelicsItems.vampirism());
		
		list.add(RelicsItems.machinegun());
		list.add(RelicsItems.waterboots());
		list.add(RelicsItems.radiance());
		list.add(RelicsItems.scythe());
		list.add(RelicsItems.rocket());
		list.add(RelicsItems.berserk());
		list.add(RelicsItems.neptune());
		
		list.add(RelicsItems.basher());
		list.add(RelicsItems.splash());
		list.add(RelicsItems.shield());
		list.add(RelicsItems.bristleback());
		list.add(RelicsItems.shadowblade());
		list.add(RelicsItems.icarus());
		list.add(RelicsItems.bedrock());
		
		return list.get(index);
	}

}
