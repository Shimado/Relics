package org.shimado.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.shimado.classes.ColorText;
import org.shimado.classes.Music;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;

public class MachineGun implements Listener{
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();

	private static MainRelics plugin;
	public MachineGun(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void checkPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			List<String> listlore = e.getItem().getItemStack().getItemMeta().getLore();
			if(e.getItem().getItemStack().equals(RelicsItems.machinegun()) && !listlore.get(listlore.size()-1).equals(ColorText.colortext(messages.getString("First Pickup").replace("<player>", player.getName())))) {
				for (int i = 0; i < 4; i++) {
					Creature creature = (Creature) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
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
				Music.findGun(player);
				Bukkit.broadcastMessage(ColorText.colortext(ColorText.colortext(messages.getString("Relic found").replace("<relic>", "Gatling machine gun"))));
			}
		}
	}
	
	
	@EventHandler
	public void spawnWolf(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Gatling Machine")))) {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				Snowball ball = (Snowball) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SNOWBALL);
				ball.setShooter(player);
				ball.setVelocity(player.getLocation().getDirection().multiply(3.5));
				Location targetLoc = player.getTargetBlock(null, 100).getLocation();
				Location playerLoc = player.getLocation();
				Vector p1 = playerLoc.toVector();
				Vector p2 = targetLoc.toVector();
				Vector vector = p2.clone().subtract(p1).normalize().multiply(0.8);
				for (int i = 0; i < 5; i++) {
					Particle particle = Particle.CLOUD;
					Particle particle1 = Particle.FLAME;
					player.getWorld().spawnParticle(particle, p1.getX(), p1.getY()+1, p1.getZ(), 0, 0.0, 0.0, 0.0);
					player.getWorld().spawnParticle(particle1, p1.getX(), p1.getY()+1, p1.getZ(), 0, 0.0, 0.0, 0.0);
					p1 = p1.add(vector);
				}
			}
		}
	}
	
	@EventHandler
	public void damageEvent(EntityDamageByEntityEvent e) {
		Entity target = e.getEntity();
		if(e.getDamager() instanceof Snowball) {
			Snowball ball = (Snowball) e.getDamager();
			Player player = (Player) ball.getShooter();
			if(player.getInventory().getItemInMainHand() != null 
					&& player.getInventory().getItemInMainHand().getItemMeta() != null 
					&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ColorText.colortext(messages.getString("Gatling Machine")))) {
				if(target instanceof LivingEntity && !(target instanceof ArmorStand)) {
					Music.explosion(player);
					((Damageable) target).damage(config.getDouble("Machine Gun Damage"));
				}
			}
		}
	}
	
	@EventHandler
	public void playerjoin(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if(player.getInventory().contains(RelicsItems.machinegun()) && config.getBoolean("Curse of Vanishing")) {
			Music.lostRelic();
		}
	}
	
	
	
}
