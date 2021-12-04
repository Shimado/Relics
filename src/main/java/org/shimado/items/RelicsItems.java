package org.shimado.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shimado.classes.ColorText;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;

public class RelicsItems {
	
	private static YamlConfiguration config = ConfigRelics.getConfig();
	private static YamlConfiguration messages = MessagesAndLore.getConfig();

	public static ItemStack arthurSword() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Artur Sword")));
		List<String> lore = messages.getStringList("Artur Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack thorHammer() {
		ItemStack item = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Thors Hammer")));
		List<String> lore = messages.getStringList("Thors Lore"); 
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack zeusBolt() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Zeus Bolt")));
		List<String> lore = messages.getStringList("Zeus Lore"); 
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack radiance() {
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Radiance")));
		List<String> lore = messages.getStringList("Radiance Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack hook() {
		ItemStack item = new ItemStack(Material.BOW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Rudge Hook")));
		List<String> lore = messages.getStringList("Rudge Hook Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack arachne() {
		ItemStack item = new ItemStack(Material.NETHERITE_HOE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Staff of Arachne")));
		List<String> lore = messages.getStringList("Staff of Arachne Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack vampirism() {
		ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Harkons Sword")));
		List<String> lore = messages.getStringList("Harkons Sword Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack machinegun() {
		ItemStack item = new ItemStack(Material.CROSSBOW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Gatling Machine")));
		List<String> lore = messages.getStringList("Gatling Machine Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack waterboots() {
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Moses Boots")));
		List<String> lore = messages.getStringList("Moses Boots Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
		meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack midas() {
		ItemStack item = new ItemStack(Material.GOLDEN_AXE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Midas")));
		List<String> lore = messages.getStringList("Midas Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack scythe() {
		ItemStack item = new ItemStack(Material.NETHERITE_HOE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Scythe of Death")));
		List<String> lore = messages.getStringList("Scythe of Death Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack rocket() {
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Staff of Ra")));
		List<String> lore = messages.getStringList("Staff of Ra Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack berserk() {
		ItemStack item = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Warlord's axes")));
		List<String> lore = messages.getStringList("Warlord's axes Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack neptune() {
		ItemStack item = new ItemStack(Material.TRIDENT);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Neptune's trident")));
		List<String> lore = messages.getStringList("Neptune's trident Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		meta.addEnchant(Enchantment.LOYALTY, 3, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack basher() {
		ItemStack item = new ItemStack(Material.IRON_SHOVEL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Basher")));
		List<String> lore = messages.getStringList("Basher Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack splash() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Sword of the Waves")));
		List<String> lore = messages.getStringList("Sword of the Waves Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack shield() {
		ItemStack item = new ItemStack(Material.SHIELD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Shield")));
		List<String> lore = messages.getStringList("Shield Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack bristleback() {
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Porcupine back")));
		List<String> lore = messages.getStringList("Porcupine back Lore"); 
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack shadowblade() {
		ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("ShadowBlade")));
		List<String> lore = messages.getStringList("ShadowBlade Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack icarus() {
		ItemStack item = new ItemStack(Material.ELYTRA);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Icarus")));
		List<String> lore = messages.getStringList("Icarus Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack bedrock() {
		ItemStack item = new ItemStack(Material.BEDROCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorText.colortext(messages.getString("Thanos")));
		List<String> lore = messages.getStringList("Thanos Lore");
		meta.setLore(ColorText.colorarray(lore));
		meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		if(config.getBoolean("Curse of Vanishing")) {
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		}
		meta.addItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
		return item;
	}
}
