package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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


public class Shield implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithBoltInMainHand = new HashMap<>();

	private static MainRelics plugin;
	public Shield(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.shield()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findCaptains(player);
				Found.foundRelic(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Captain america's shield")));
			}
			checkMainHand(player);
		}
	}
	

	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Shield")))) {
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
	
	@EventHandler
	public void checkActive(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getInventory().getItemInMainHand() != null
				&& player.getInventory().getItemInOffHand().getItemMeta() != null
				&& player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Shield")))) {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && player.isBlocking()) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 25, 5));
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 25, 5));
				addPotions(player);
			}
		}
	}
	
	//ПРОСТО КИДАЕТ ЭФФЕКТЫ
	private static void addPotions(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.isBlocking() && player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInOffHand().getItemMeta() != null
						&& player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Shield")))) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 25, 5));
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 25, 5));
						addPotions(player);
					}
			}
		}, 20);
	}
	
	@EventHandler
	public void checkActive(EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(player.getInventory().getItemInMainHand() != null
					&& player.getInventory().getItemInOffHand().getItemMeta() != null
					&& player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Shield")))
					&& player.isBlocking()) {
				if(e.getDamager() instanceof LivingEntity && !(e.getDamager() instanceof Wither || e.getDamager() instanceof EnderDragon)) {
					spawnWolf((LivingEntity) e.getDamager());
				}else if(e.getDamager() instanceof Arrow){
					Arrow arrow = (Arrow) e.getDamager();
					if(arrow.getShooter() != null && arrow.getShooter() instanceof LivingEntity) {
						spawnWolf((LivingEntity) arrow.getShooter());
					}
				}else if(e.getDamager() instanceof Snowball){
					Snowball snowball = (Snowball) e.getDamager();
					if(snowball.getShooter() != null && snowball.getShooter() instanceof LivingEntity) {
						spawnWolf((LivingEntity) snowball.getShooter());
					}
				}else if(e.getDamager() instanceof Trident){
					Trident trident = (Trident) e.getDamager();
					if(trident.getShooter() != null && trident.getShooter() instanceof LivingEntity) {
						spawnWolf((LivingEntity) trident.getShooter());
					}
				}
			}
		}
	}
	
	private static void spawnWolf(LivingEntity entity) {
		Creature creature = (Creature) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.WOLF);
		creature.setTarget(entity);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				creature.remove();
			}
		}, 20*6);
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
		if(player.getInventory().contains(RelicsItems.shield()) && config.getBoolean("Curse of Vanishing")) {
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
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Shield")))){
			Player player = e.getPlayer();
			checkMainHand(player);
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
