package org.shimado.relics;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.shimado.classes.ColorText;
import org.shimado.classes.NaturalSpawning;
import org.shimado.commands.Command;
import org.shimado.configs.ConfigRelics;
import org.shimado.configs.MessagesAndLore;
import org.shimado.listeners.Arachne;
import org.shimado.listeners.ArturSwordListener;
import org.shimado.listeners.Basher;
import org.shimado.listeners.Bedrock;
import org.shimado.listeners.Berserk;
import org.shimado.listeners.Bristleback;
import org.shimado.listeners.Elytra;
import org.shimado.listeners.Harkon;
import org.shimado.listeners.Hook;
import org.shimado.listeners.InventoryListener;
import org.shimado.listeners.MachineGun;
import org.shimado.listeners.Midas;
import org.shimado.listeners.MosesBoots;
import org.shimado.listeners.Neptune;
import org.shimado.listeners.Radiance;
import org.shimado.listeners.RocketLauncher;
import org.shimado.listeners.Scythe;
import org.shimado.listeners.Shadowblade;
import org.shimado.listeners.Shield;
import org.shimado.listeners.Splash;
import org.shimado.listeners.ThorHammer;
import org.shimado.listeners.ZeusBolt;

public class MainRelics extends JavaPlugin{

	@Override
	public void onEnable() {
		enableMessage();
		new ConfigRelics(this);
		ConfigRelics.createFiles();
		new MessagesAndLore(this);
		MessagesAndLore.createConfig();
		new Command(this);
		new InventoryListener(this);
		new ArturSwordListener(this);
		new ThorHammer(this);
		new ZeusBolt(this);
		new Radiance(this);
		new Hook(this);
		new Arachne(this);
		new Harkon(this);
		new Midas(this);
		new MachineGun(this);
		new MosesBoots(this);
		new Scythe(this);
		new RocketLauncher(this);
		new Berserk(this);
		new Neptune(this);
		new Basher(this);
		new Splash(this);
		new Elytra(this);
		new Shadowblade(this);
		new Bristleback(this);
		new Bedrock(this);
		new Shield(this);
		new NaturalSpawning(this);
	}
	
	@Override
	public void onDisable() {
		disableMessage();
	}
	
	private static void enableMessage() {
		Logger loger = Logger.getLogger("Minecraft");
		loger.info(ColorText.colortext("========================"));
		loger.info(ColorText.colortext("RELICS PLUGIN STARTS UP"));
		loger.info(ColorText.colortext("========================"));
	}
	
	private static void disableMessage() {
		Logger loger = Logger.getLogger("Minecraft");
		loger.info(ColorText.colortext("======================="));
		loger.info(ColorText.colortext("RELICS PLUGIN DISABLED"));
		loger.info(ColorText.colortext("======================="));
	}
	
}
