package org.shimado.classes;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Found {
	
	public static void foundRelic(Player player) {
		Firework item = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
		FireworkMeta meta = item.getFireworkMeta();
		meta.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).flicker(true).build());
		meta.setPower(3);
		item.setFireworkMeta(meta);
	}

}
