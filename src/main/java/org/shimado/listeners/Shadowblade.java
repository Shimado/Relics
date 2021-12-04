package org.shimado.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.shimado.classes.ColorText;
import org.shimado.classes.Found;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;


public class Shadowblade implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithBoltInMainHand = new HashMap<>();
	private static Map<Player, List<ItemStack>> listitems = new HashMap<Player, List<ItemStack>>();

	private static MainRelics plugin;
	public Shadowblade(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.shadowblade()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findBlade(player);
				Found.foundRelic(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Shadowblade")));
			}
			checkMainHand(player);
		}
	}
	
	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("ShadowBlade")))) {
					if(playersWithBoltInMainHand != null && playersWithBoltInMainHand.containsKey(player)) {
						Bukkit.getScheduler().cancelTask(playersWithBoltInMainHand.get(player));
						playersWithBoltInMainHand.remove(player);
					}
					playersWithBoltInMainHand.put(player, createArturSwordParticles(player));
				}else {
					if(playersWithBoltInMainHand != null && playersWithBoltInMainHand.containsKey(player)) {
						if(listitems.containsKey(player)) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {
									if(listitems.get(player) != null && listitems.get(player).size()>0) {
										ItemStack[] items = new ItemStack[listitems.get(player).size()];
										for (int i = 0; i < listitems.get(player).size(); i++) {
											items[i] = listitems.get(player).get(i);
										}
										player.getInventory().setArmorContents(items);
									}
									listitems.remove(player);
									if(playersWithBoltInMainHand != null && playersWithBoltInMainHand.containsKey(player)) {
										Bukkit.getScheduler().cancelTask(playersWithBoltInMainHand.get(player));
									}
									playersWithBoltInMainHand.remove(player);
								}
							}, 20*10);
						}
					}
				}
			}
		}, 5);
	}
	
	
	//ПРОВЕРКА НА ЛОГИН И ВЫХОД И СМЕРТЬ
	
	@EventHandler
	public void playerjoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Berserk.checkMainHand(player);
	}
	
	@EventHandler
	public void playerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		Berserk.checkMainHand(player);
		if(listitems.containsKey(player)) {
			ItemStack[] items = new ItemStack[listitems.get(player).size()];
			for (int i = 0; i < listitems.get(player).size(); i++) {
				items[i] = listitems.get(player).get(i);
			}
			player.getInventory().setArmorContents(items);
			listitems.remove(player);
		}
		if(playersWithBoltInMainHand != null && playersWithBoltInMainHand.containsKey(player)) {
			Bukkit.getScheduler().cancelTask(playersWithBoltInMainHand.get(player));
			playersWithBoltInMainHand.remove(player);
		}
	}
	
	@EventHandler
	public void playerjoin(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if(playersWithBoltInMainHand != null && playersWithBoltInMainHand.containsKey(player)) {
			Bukkit.getScheduler().cancelTask(playersWithBoltInMainHand.get(player));
			playersWithBoltInMainHand.remove(player);
		}
		if(player.getInventory().contains(RelicsItems.shadowblade()) && config.getBoolean("Curse of Vanishing")) {
			Music.lostRelic();
		}
	}
	
	//ПРОВЕРКА НА ПЕРЕЛИСТЫВАНИЕ СЛОТОВ И КЛИКИ И ВЫКИДЫВАНИЕ В ИНВЕНТАРЕ
	
	@EventHandler
	public void checkSwap(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		checkMainHand(player);
	}
	
	@EventHandler
	public void checkInv(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
			if(listitems.containsKey(player)) {
				checkMainHand(player);
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("ShadowBlade")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	public static int createArturSwordParticles(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			public void run() {
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5, 5, true, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 1, true, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 2, true, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 5, 1, true, false));
				if(!listitems.containsKey(player)) {
					ItemStack[] items = player.getInventory().getArmorContents();
					List<ItemStack> list = new ArrayList<>();
					for (int i = 0; i < items.length; i++) {
						list.add(items[i]);
					}
					listitems.put(player, list);
					player.getInventory().setArmorContents(new ItemStack[] {new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR)});
				}
			}
			
		}, 0L, 0);
		
		return id;
	}
}
