package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shimado.classes.ColorText;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;

public class RocketLauncher implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithBoltInMainHand = new HashMap<>();

	private static MainRelics plugin;
	public RocketLauncher(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.rocket()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				for (int i = 0; i < 4; i++) {
					Creature creature = (Creature) player.getWorld().spawnEntity(player.getLocation(), EntityType.BLAZE);
					creature.setCustomName(ColorText.colortext(messages.getString("Weapon Keeper")));
					creature.setCustomNameVisible(true);
					creature.setTarget(player);
				}
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findRa(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Staff of Ra")));
			}
			checkMainHand(player);
		}
	}
	
	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Staff of Ra")))) {
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
	
	//???????????????? ???? ?????????? ?? ?????????? ?? ????????????
	
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
		if(player.getInventory().contains(RelicsItems.rocket()) && config.getBoolean("Curse of Vanishing")) {
			Music.lostRelic();
		}
	}
	
	//???????????????? ???? ???????????????????????????? ???????????? ?? ?????????? ?? ?????????????????????? ?? ??????????????????
	
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
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Staff of Ra")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	
	
	@EventHandler
	public void spawnWolf(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Staff of Ra")))) {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && e.getHand().equals(EquipmentSlot.HAND)) {
				Location l = player.getLocation().add(player.getLocation().getDirection().multiply(3));
				Fireball ball = (Fireball) player.getWorld().spawnEntity(l, EntityType.FIREBALL);
				ball.setShooter(player);
				ball.setVelocity(l.getDirection().multiply(1));
				Particle particle = Particle.CLOUD;
				Music.rocket(player);
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 6; i++) {
					double angle = Math.toRadians(((double) i / 6) * 360d);
					player.spawnParticle(particle, x+Math.cos(angle) * 0.4, y+1, z+Math.sin(angle) * 0.4, 0, 0.0, 0.0, 0.0);
				}
			}
		}
	}
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent e) {
		Entity target = e.getEntity();
		if(e.getDamager() instanceof Fireball) {
			Fireball ball = (Fireball) e.getDamager();
			if(ball.getShooter() instanceof Player) {
				Player player = (Player) ball.getShooter();
				if(player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Staff of Ra")))) {
					if(target instanceof LivingEntity && !(target instanceof ArmorStand)) {
						Music.explosion(player);
						((LivingEntity) target).setHealth(((LivingEntity) target).getHealth() <= config.getDouble("Rocket Launcher Damage") ? 0.0 : ((LivingEntity) target).getHealth() - config.getDouble("Rocket Launcher Damage"));
					}
				}
			}
		}
	}
	
	
	public static int createArturSwordParticles(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			Particle particle = Particle.FLAME;
			
			public void run() {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 20; i++) {
					double angle = Math.toRadians(((double) i / 20) * 360d);
					player.getWorld().spawnParticle(particle, x+Math.cos(angle) * 0.4, y+2.1, z+Math.sin(angle) * 0.4, 0, 0.0, 0.0, 0.0);
				}
			}
			
		}, 0L, 0);
		
		return id;
	}
	
	
}
