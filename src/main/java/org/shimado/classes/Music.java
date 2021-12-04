package org.shimado.classes;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.shimado.configs.MessagesAndLore;

public class Music {
	
	private static YamlConfiguration messages = MessagesAndLore.getConfig();

	public static void findRelic() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.getWorld().playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5F, 0F);
		}
	}
	
	public static void lostRelic() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 0.5F, 0F);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 0.5F, 0F);
			player.sendMessage(ColorText.colortext(messages.getString("Relic lost")));
		}
	}
	
	public static void midas(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0F);
	}
	
	public static void shot(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 0.5F, 0F);
	}
	
	public static void explosion(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.5F, 0F);
	}
	
	public static void scythe(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_HURT, 0.5F, 0F);
	}
	
	public static void rocket(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.5F, 0F);
	}
	
	public static void bedrock(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 1F, 0F);
	}
	
	public static void splashHit(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DROWNED_SWIM, 1F, 0F);
	}
	
	public static void findArthur(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WOLF_HOWL, 0.8F, 0F);
	}
	
	public static void findThor(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.8F, 0F);
	}
	
	public static void findRadiance(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.8F, 0F);
	}
	
	public static void findHook(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 0.5F, 0F);
	}
	
	public static void findArachne(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 0.5F, 0F);
	}
	
	public static void findVarmpir(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_LOOP, 0.8F, 0F);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_AMBIENT, 0.9F, 0F);
	}
	
	public static void findGun(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 0.5F, 0F);
	}
	
	public static void findBoots(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 0.8F, 0F);
	}
	
	public static void findMidas(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 0.8F, 0F);
	}
	
	public static void findWither(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 0.8F, 0F);
	}
	
	public static void findRa(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.8F, 0F);
	}
	
	public static void findAxes(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PILLAGER_CELEBRATE, 0.8F, 0F);
	}
	
	public static void findBasher(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.8F, 0F);
	}
	
	public static void findCaptains(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 0.8F, 0F);
	}
	
	public static void findBlade(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.8F, 0F);
	}
	
	public static void findIcarus(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_ELYTRA_FLYING, 0.8F, 0F);
	}
	
	public static void findBedrock(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.8F, 0F);
	}
}
