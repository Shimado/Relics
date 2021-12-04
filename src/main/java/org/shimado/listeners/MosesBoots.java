package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.shimado.classes.ColorText;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;

public class MosesBoots implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithHammerInMainHand = new HashMap<>();

	private static MainRelics plugin;
	public MosesBoots(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.waterboots()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName()))))) {
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName()))));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findBoots(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Moses boots")));
			}
			checkMainHand(player);
		}
	}
	
	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Moses Boots")))) {
					if(playersWithHammerInMainHand != null && playersWithHammerInMainHand.containsKey(player)) {
						Bukkit.getScheduler().cancelTask(playersWithHammerInMainHand.get(player));
						playersWithHammerInMainHand.remove(player);
					}
					playersWithHammerInMainHand.put(player, createThorsEffects(player));
				}else {
					if(playersWithHammerInMainHand != null && playersWithHammerInMainHand.containsKey(player)) {
						Bukkit.getScheduler().cancelTask(playersWithHammerInMainHand.get(player));
						playersWithHammerInMainHand.remove(player);
					}
				}
			}
		}, 5);
	}
	
	
	@EventHandler
	public void spawnThunder(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if(player.getInventory().getBoots() != null 
				&& player.getInventory().getBoots().getItemMeta() != null 
				&& player.getInventory().getBoots().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Moses Boots")))){
			if(e.getTo().getBlock().getType().equals(Material.WATER) || e.getTo().getBlock().getType().equals(Material.LAVA)
					&& (player.getLocation().getBlock().getType().equals(Material.LAVA)  || player.getLocation().getBlock().getType().equals(Material.WATER))) {
				player.setAllowFlight(true);
				player.setFlying(true);
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60, 5));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20*10, 2));
			}else {
				player.setFlying(false);
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
		if(playersWithHammerInMainHand != null && playersWithHammerInMainHand.containsKey(player)) {
			Bukkit.getScheduler().cancelTask(playersWithHammerInMainHand.get(player));
			playersWithHammerInMainHand.remove(player);
		}
	}
	
	@EventHandler
	public void playerjoin(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if(playersWithHammerInMainHand != null && playersWithHammerInMainHand.containsKey(player)) {
			Bukkit.getScheduler().cancelTask(playersWithHammerInMainHand.get(player));
			playersWithHammerInMainHand.remove(player);
		}
		if(player.getInventory().contains(RelicsItems.waterboots()) && config.getBoolean("Curse of Vanishing")) {
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
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Moses Boots")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	public static int createThorsEffects(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			Particle particle = Particle.WATER_SPLASH;
			
			public void run() {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 6; i++) {
					double angle = Math.toRadians(((double) i / 6) * 360d);
					player.getWorld().spawnParticle(particle, x+Math.cos(angle) * 0.4, y+0.2, z+Math.sin(angle) * 0.4, 0, 0.0, 0.0, 0.0);
				}
			}
			
		}, 0L, 0);
		
		return id;
	}
}
