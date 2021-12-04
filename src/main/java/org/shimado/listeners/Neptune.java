package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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


public class Neptune implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithBoltInMainHand = new HashMap<>();

	private static MainRelics plugin;
	public Neptune(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.neptune()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				player.getWorld().setThunderDuration(20*10);
				for (int i = 0; i < 10; i++) {
					double x = ((int) (Math.random()*2) == 1 ? player.getLocation().getX() + ((int) (Math.random()*30)) : player.getLocation().getX() - ((int) (Math.random()*30)));
					double z = ((int) (Math.random()*2) == 1 ? player.getLocation().getZ() + ((int) (Math.random()*30)) : player.getLocation().getZ() - ((int) (Math.random()*30)));
					double y = player.getWorld().getHighestBlockYAt((int) x, (int) z);
					Location loc = new Location(player.getWorld(), x, y, z);
					player.getWorld().strikeLightning(loc);
				}
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findBoots(player);
				Found.foundRelic(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Neptune's trident")));
			}
			checkMainHand(player);
		}
	}
	
	
	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Neptune's trident")))) {
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
	public void riptude(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Trident) {
			Trident trident = (Trident) e.getEntity();
			if(trident.getShooter() instanceof Player) {
				Player player = (Player) trident.getShooter();
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Neptune's trident")))) {
					trident.addPassenger(player);
					trident.setCustomName(ColorText.colortext(messages.getString("Neptune's trident")));
				}
			}
		}
	}
	
	@EventHandler
	public void riptudeLand(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Trident) {
			Trident trident = (Trident) e.getEntity();
			if(trident.getShooter() instanceof Player && e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equals(ColorText.colortext(messages.getString("Neptune's trident")))) {
				Player player = (Player) trident.getShooter();
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 5));
				if(e.getHitEntity() == null) {
					e.getHitBlock().getWorld().strikeLightning(e.getHitBlock().getLocation());
				}else {
					e.getHitEntity().getWorld().strikeLightning(e.getHitEntity().getLocation());
				}
			}
		}
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
		if(player.getInventory().contains(RelicsItems.neptune()) && config.getBoolean("Curse of Vanishing")) {
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
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Neptune's trident")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	//ПАРТИКЛЫ И БАФФ
	
	public static int createArturSwordParticles(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			Particle particle = Particle.CLOUD;
			
			public void run() {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 12; i++) {
					double angle = Math.toRadians(((double) i / 12) * 360d);
					player.getWorld().spawnParticle(particle, x+Math.cos(angle) * 0.3, y+(i*0.1), z+Math.sin(angle) * 0.3, 0, 0.0, 0.0, 0.0);
					player.getWorld().spawnParticle(particle, x-Math.cos(angle) * 0.3, y+(i*0.1), z-Math.sin(angle) * 0.3, 0, 0.0, 0.0, 0.0);
				}
			}
			
		}, 0L, 0);
		
		return id;
	}
}
