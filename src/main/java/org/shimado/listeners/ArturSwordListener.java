package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shimado.classes.ColorText;
import org.shimado.classes.Found;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;

public class ArturSwordListener implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithSwordInMainHand = new HashMap<>();

	private static MainRelics plugin;
	public ArturSwordListener(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.arthurSword()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				if(config.getInt("Arturs Sword Chance PickUp") >= ((int) (Math.random()*100.0))) {
					ItemStack item = e.getItem().getItemStack();
					ItemMeta meta = item.getItemMeta();
					List<String> list = meta.getLore();
					list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
					meta.setLore(list);
					item.setItemMeta(meta);
					Music.findRelic();
					Music.findArthur(player);
					Found.foundRelic(player);
					Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "King Arthur's sword")));
				}else {
					e.setCancelled(true);
					player.setHealth(0.0);
					player.sendMessage(ColorText.colortext(messages.getString("Arthur Sword pickup fail")));
				}
			}
			checkMainHand(player);
		}
	}
	
	@EventHandler
	public void spawnWolf(EntityDamageByEntityEvent e) {
		Entity target = e.getEntity();
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getItemMeta() != null 
					&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Artur Sword"))) 
					&& !e.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)) {
				if(target instanceof LivingEntity) {
					Creature creature = (Creature) target.getWorld().spawnEntity(target.getLocation(), EntityType.WOLF);
					creature.setCustomName(ColorText.colortext(messages.getString("Arthur wolf")));
					creature.setTarget((LivingEntity) target);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							creature.remove();
						}
					}, 20*5);
				}
			}
		}
	}
	
	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Artur Sword")))) {
					if(playersWithSwordInMainHand != null && playersWithSwordInMainHand.containsKey(player)) {
						Bukkit.getScheduler().cancelTask(playersWithSwordInMainHand.get(player));
						playersWithSwordInMainHand.remove(player);
					}
					playersWithSwordInMainHand.put(player, createArturSwordParticles(player));
				}else {
					if(playersWithSwordInMainHand != null && playersWithSwordInMainHand.containsKey(player)) {
						Bukkit.getScheduler().cancelTask(playersWithSwordInMainHand.get(player));
						playersWithSwordInMainHand.remove(player);
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
		if(playersWithSwordInMainHand != null && playersWithSwordInMainHand.containsKey(player)) {
			Bukkit.getScheduler().cancelTask(playersWithSwordInMainHand.get(player));
			playersWithSwordInMainHand.remove(player);
		}
	}
	
	@EventHandler
	public void playerjoin(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if(playersWithSwordInMainHand != null && playersWithSwordInMainHand.containsKey(player)) {
			Bukkit.getScheduler().cancelTask(playersWithSwordInMainHand.get(player));
			playersWithSwordInMainHand.remove(player);
		}
		if(player.getInventory().contains(RelicsItems.arthurSword()) && config.getBoolean("Curse of Vanishing")) {
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
			checkMainHand(player);
		}
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Artur Sword")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	public static int createArturSwordParticles(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			Particle particle = Particle.FLAME;
			
			public void run() {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 6; i++) {
					double angle = Math.toRadians(((double) i / 6) * 360d);
					player.getWorld().spawnParticle(particle, x+Math.cos(angle) * 0.4, y+2.2, z+Math.sin(angle) * 0.4, 0, 0.0, 0.0, 0.0);
				}
			}
			
		}, 0L, 0);
		
		return id;
	}
}
