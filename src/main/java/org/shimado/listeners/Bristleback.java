package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.shimado.classes.ColorText;
import org.shimado.classes.Found;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;


public class Bristleback implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithBoltInMainHand = new HashMap<>();

	private static MainRelics plugin;
	public Bristleback(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.bristleback()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName()))))) {
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findCaptains(player);
				Found.foundRelic(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Porcupine back")));
			}
			checkMainHand(player);
		}
	}
	

	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getChestplate() != null
						&& player.getInventory().getChestplate().getItemMeta() != null
						&& player.getInventory().getChestplate().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Porcupine back")))) {
					if(playersWithBoltInMainHand != null && playersWithBoltInMainHand.containsKey(player)) {
						Bukkit.getScheduler().cancelTask(playersWithBoltInMainHand.get(player));
						playersWithBoltInMainHand.remove(player);
					}
					playersWithBoltInMainHand.put(player, createArturSwordParticles(player));
				}else {
					if(playersWithBoltInMainHand != null && playersWithBoltInMainHand.containsKey(player)) {
						Bukkit.getScheduler().cancelTask(playersWithBoltInMainHand.get(player));
						playersWithBoltInMainHand.remove(player);
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
		if(player.getInventory().contains(RelicsItems.bristleback()) && config.getBoolean("Curse of Vanishing")) {
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
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Porcupine back")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		if(e.getDamager() instanceof Entity && !(e.getDamager() instanceof Fireball) && entity instanceof Player) {
			Entity damager = (Entity) e.getDamager();
			Player player = (Player) entity;
			if(player.getInventory().getChestplate() != null
					&& player.getInventory().getChestplate().getItemMeta() != null
					&& player.getInventory().getChestplate().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Porcupine back")))) {
				if(!(damager instanceof Arrow || damager instanceof Trident || damager instanceof Snowball) 
						&& (player.getFacing().getDirection().getX() == damager.getFacing().getDirection().getX() && player.getFacing().getDirection().getZ() == damager.getFacing().getDirection().getZ())) {
					setFireball(damager, player, e);
					return;
				}
				if(damager instanceof Arrow && player.getFacing().getDirection().getX() == ((Entity) ((Arrow) damager).getShooter()).getFacing().getDirection().getX() 
						&& player.getFacing().getDirection().getZ() == ((Entity) ((Arrow) damager).getShooter()).getFacing().getDirection().getZ()) {
					Entity shooter = (Entity) ((Arrow) damager).getShooter();
					setFireball(shooter, player, e);
					return;
				}
				if(damager instanceof Trident && player.getFacing().getDirection().getX() == ((Entity) ((Trident) damager).getShooter()).getFacing().getDirection().getX() 
						&& player.getFacing().getDirection().getZ() == ((Entity) ((Trident) damager).getShooter()).getFacing().getDirection().getZ()) {
					Entity shooter = (Entity) ((Trident) damager).getShooter();
					setFireball(shooter, player, e);
					return;
				}
				if(damager instanceof Snowball && player.getFacing().getDirection().getX() == ((Entity) ((Snowball) damager).getShooter()).getFacing().getDirection().getX() 
						&& player.getFacing().getDirection().getZ() == ((Entity) ((Snowball) damager).getShooter()).getFacing().getDirection().getZ()) {
					Entity shooter = (Entity) ((Snowball) damager).getShooter();
					setFireball(shooter, player, e);
					return;
				}
			}
		}
	}
	
	private static void setFireball(Entity entity, Player player, EntityDamageByEntityEvent e) {
		e.setDamage((e.getDamage()*2)/3);
		for (int i = 0; i < 4; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					SmallFireball arrow = (SmallFireball) player.getWorld().spawnEntity(player.getLocation(), EntityType.SMALL_FIREBALL);
					Vector p1 = player.getLocation().toVector();
					Vector p2 = entity.getLocation().add(0, 2, 0).toVector();
					Vector p = p2.clone().subtract(p1).normalize().multiply(3);
					arrow.setVelocity(p);
					arrow.setShooter(player);
				}
			}, 0+i);
		}
	}
	
	public static int createArturSwordParticles(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			Particle particle = Particle.WHITE_ASH;
			
			public void run() {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 12; i++) {
					double angle = Math.toRadians(((double) i / 12) * 360d);
					player.getWorld().spawnParticle(particle, x+Math.cos(angle) * 0.3, y+0.3, z+Math.sin(angle) * 0.3, 0, 0.0, 0.0, 0.0);
				}
			}
			
		}, 0L, 0);
		
		return id;
	}
}
