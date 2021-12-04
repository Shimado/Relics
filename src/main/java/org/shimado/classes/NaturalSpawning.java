package org.shimado.classes;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.items.RelicsItems;
import org.shimado.relics.MainRelics;

public class NaturalSpawning implements Listener {
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();
	
	private static MainRelics plugin;
	public NaturalSpawning(MainRelics pluginA) {
		plugin = pluginA;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void spawnRating(CreatureSpawnEvent e) {
		if(e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equals(ColorText.colortext(messages.getString("Arachne spider")))
												 && e.getEntity().getCustomName().equals(ColorText.colortext(messages.getString("Arthur wolf")))
												 && e.getEntity().getCustomName().equals(ColorText.colortext(messages.getString("Weapon Keeper")))) {
			return;
		}
		Entity entity = e.getEntity();
		if(!(e.getEntity() instanceof Animals) && !e.getLocation().getBlock().isLiquid() && e.getSpawnReason().equals(SpawnReason.NATURAL) && config.getBoolean("Spawn mob and drop them")){
			if(entity.getCustomName() == null) {
				if(config.getDouble("Mob Vindicator Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.VINDICATOR);
					creature.setCustomName(ColorText.colortext(messages.getString(messages.getString("Rare mob name"))));
					creature.setCustomNameVisible(true);
					creature.setHealth(24);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Spider Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.SPIDER);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Nether Zombie Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.ZOMBIFIED_PIGLIN);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Wolf Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.WOLF);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Evoker Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.EVOKER);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Golem Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.IRON_GOLEM);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Silverfish Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.SILVERFISH);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Skeleton Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.SILVERFISH);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
				if(config.getDouble("Mob Phantom Chance Spawn") >= (Math.random()*100)) {
					e.setCancelled(true);
					World world = e.getLocation().getWorld();
					LivingEntity creature = (LivingEntity) world.spawnEntity(e.getLocation(), EntityType.PHANTOM);
					creature.setCustomName(ColorText.colortext(messages.getString("Rare mob name")));
					creature.setCustomNameVisible(true);
					creature.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
					creature.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(2);
					creature.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(2);
				}
			}
		}
	}
	
	@EventHandler
	public void loot(EntityDeathEvent e) {
		Entity entity = e.getEntity();
		if(entity instanceof LivingEntity && entity.getCustomName() != null 
				&& entity.getCustomName().equals(ColorText.colortext(messages.getString("Rare mob name"))) && config.getBoolean("Spawn mob and drop them")) {
			if(e.getEntity().getType().equals(EntityType.WOLF) && config.getDouble("Chance of Arthurs Sword") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.arthurSword());
			}
			if(e.getEntity().getType().equals(EntityType.VINDICATOR) && config.getDouble("Chance of Thors Hammer") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.thorHammer());
			}
			if(e.getEntity().getType().equals(EntityType.EVOKER) && config.getDouble("Chance of Zeus Bolt") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.zeusBolt());
			}
			if(e.getEntity().getType().equals(EntityType.ZOMBIFIED_PIGLIN) && config.getDouble("Chance of Radiance") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.radiance());
			}
			if(e.getEntity().getType().equals(EntityType.SKELETON) && config.getDouble("Chance of Rudge Hook") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.hook());
			}
			if(e.getEntity().getType().equals(EntityType.SPIDER) && config.getDouble("Chance of Staff of Arachne") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.arachne());
			}
			if(e.getEntity().getType().equals(EntityType.ZOMBIFIED_PIGLIN) && config.getDouble("Chance of Harkons Sword") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.vampirism());
			}
			if(e.getEntity().getType().equals(EntityType.SKELETON) && config.getDouble("Chance of Gatling Machine Gun") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.machinegun());
			}
			if(e.getEntity().getType().equals(EntityType.IRON_GOLEM) && config.getDouble("Chance of Moses boots") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.waterboots());
			}
			if(e.getEntity().getType().equals(EntityType.ZOMBIFIED_PIGLIN) && config.getDouble("Chance of Midas") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.midas());
			}
			if(e.getEntity().getType().equals(EntityType.SPIDER) && config.getDouble("Chance of Staff of Death") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.scythe());
			}
			if(e.getEntity().getType().equals(EntityType.BLAZE) && config.getDouble("Chance of Staff of Ra") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.rocket());
			}
			if(e.getEntity().getType().equals(EntityType.VINDICATOR) && config.getDouble("Chance of Warlord axes") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.berserk());
				e.getDrops().add(RelicsItems.berserk());
			}
			if(e.getEntity().getType().equals(EntityType.EVOKER) && config.getDouble("Chance of Neptunes") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.neptune());
			}
			if(e.getEntity().getType().equals(EntityType.VINDICATOR) && config.getDouble("Chance of Basher") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.basher());
			}
			if(e.getEntity().getType().equals(EntityType.IRON_GOLEM) && config.getDouble("Chance of Splash") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.splash());
			}
			if(e.getEntity().getType().equals(EntityType.WOLF) && config.getDouble("Chance of Shield") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.shield());
			}
			if(e.getEntity().getType().equals(EntityType.IRON_GOLEM) && config.getDouble("Chance of Chestplate") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.bristleback());
			}
			if(e.getEntity().getType().equals(EntityType.EVOKER) && config.getDouble("Chance of Shadowblade") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.shadowblade());
			}
			if(e.getEntity().getType().equals(EntityType.PHANTOM) && config.getDouble("Chance of Icarus") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.icarus());
			}
			if(e.getEntity().getType().equals(EntityType.SILVERFISH) && config.getDouble("Chance of Bedrock") >= ((int) (Math.random()*100))) {
				e.getDrops().add(RelicsItems.bedrock());
			}
		}
	}

}

