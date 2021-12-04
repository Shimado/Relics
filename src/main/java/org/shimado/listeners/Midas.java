package org.shimado.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
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
import org.shimado.classes.ColorText;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;

public class Midas implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	private static int id = 0;
	
	private static Map<Player, Integer> playersWithHammerInMainHand = new HashMap<>();

	private static MainRelics plugin;
	public Midas(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.midas()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				for (int i = 0; i < 32; i++) {
					player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_INGOT));
					player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_NUGGET));
				}
				ItemStack item = e.getItem().getItemStack();
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				list.add(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())));
				meta.setLore(list);
				item.setItemMeta(meta);
				Music.findRelic();
				Music.findMidas(player);
				Bukkit.broadcastMessage(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Midas")));
			}
			checkMainHand(player);
		}
	}
	
	public static void checkMainHand(Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(player.getInventory().getItemInMainHand() != null
						&& player.getInventory().getItemInMainHand().getItemMeta() != null
						&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Midas")))) {
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
	public void spawnThunder(EntityDamageByEntityEvent e) {
		Entity target = e.getEntity();
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getItemMeta() != null 
					&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Midas")))) {
				if(target instanceof LivingEntity && !(target instanceof Player && target instanceof EnderDragon && target instanceof Wither && target instanceof ArmorStand)) {
					int toGive = (int) (((LivingEntity) target).getHealth()/4);
					target.getWorld().dropItemNaturally(target.getLocation(), new ItemStack(Material.GOLD_INGOT, toGive));
					((LivingEntity) target).setHealth(0.0);
					Music.midas(player);
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
		if(player.getInventory().contains(RelicsItems.midas()) && config.getBoolean("Curse of Vanishing")) {
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
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Midas")))){
			Player player = e.getPlayer();
			checkMainHand(player);
		}
	}
	
	public static int createThorsEffects(Player player) {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			Particle particle = Particle.END_ROD;
			
			public void run() {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				for (int i = 0; i < 6; i++) {
					double angle = Math.toRadians(((double) i / 6) * 360d);
					player.getWorld().spawnParticle(particle, x+Math.cos(angle) * 0.4, y+0.4, z+Math.sin(angle) * 0.4, 0, 0.0, 0.0, 0.0);
				}
			}
			
		}, 0L, 0);
		
		return id;
	}
}
