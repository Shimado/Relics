package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.shimado.classes.ColorText;
import org.shimado.classes.Found;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;


public class Berserk implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithBoltInMainHand = new HashMap<>();
	private static Map<Player, Integer> cooldown = new HashMap<>();

	private static MainRelics plugin;
	public Berserk(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.berserk()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findAxes(player);
				Found.foundRelic(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Warlord's axes")));
			}
			checkMainHand(player);
		}
	}
	
	
	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInOffHand() != null 
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInOffHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Warlord's axes")))
						&& player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Warlord's axes")))) {
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
		if(player.getInventory().contains(RelicsItems.berserk()) && config.getBoolean("Curse of Vanishing")) {
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
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Warlord's axes")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	//ОСНОВНАЯ ФУНКЦИЯ
	
	@EventHandler
	public void playerDamaged(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInOffHand() != null 
					&& player.getInventory().getItemInMainHand().getItemMeta() != null
					&& player.getInventory().getItemInOffHand().getItemMeta() != null
					&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Warlord's axes")))
					&& player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Warlord's axes")))) {
				if(e.getDamage() >= player.getHealth()) {
					if(cooldown.get(player) == null) {
						player.setHealth(20);
						/*
						 * PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus((Entity) player, (byte) 35);
							PlayerConnection c = new PlayerConnection(null, status, player);
						 * */
						cooldown.put(player, config.getInt("Cooldown Berserk"));
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	public void cooldownTimer(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				cooldown.remove(player);
			}
		}, 20*config.getInt("Cooldown Berserk"));
	}
	
	//ПАРТИКЛЫ И БАФФ
	
	public static int createArturSwordParticles(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			Particle particle = Particle.CRIT;
			
			public void run() {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 12; i++) {
					double angle = Math.toRadians(((double) i / 12) * 360d);
					player.getWorld().spawnParticle(particle, x+Math.cos(angle) * 0.3, y+(i*0.1), z+Math.sin(angle) * 0.3, 0, 0.0, 0.0, 0.0);
					player.getWorld().spawnParticle(particle, x-Math.cos(angle) * 0.3, y+(i*0.1), z-Math.sin(angle) * 0.3, 0, 0.0, 0.0, 0.0);
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 3));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 3));
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5, 3));
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 5, 1));
			}
			
		}, 0L, 0);
		
		return id;
	}
}
