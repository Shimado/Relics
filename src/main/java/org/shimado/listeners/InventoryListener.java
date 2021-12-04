package org.shimado.listeners;

import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.shimado.relics.MainRelics;

public class InventoryListener implements Listener{
	
	private MainRelics plugin;
	public InventoryListener(MainRelics plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void checkInv(InventoryClickEvent e) {
		if(e.getView().getTitle().contains("All Relics") && IntStream.rangeClosed(0, 44).filter(p -> e.getRawSlot() == p).count() > 0) {
			if(e.getWhoClicked() instanceof Player && !e.getWhoClicked().hasPermission("relics.openshop")) {
				e.setCancelled(true);
			}
		}
		if(e.getView().getTitle().contains("All Relics") && e.isShiftClick()) {
			if(e.getWhoClicked() instanceof Player && !e.getWhoClicked().hasPermission("relics.openshop")) {
				e.setCancelled(true);
			}
		}
	}
}
