package me.UnioDex.SmartMessages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.UnioDex.SmartMessages.commands.MainCommand;
import me.UnioDex.SmartMessages.listeners.PlayerListeners;
import me.UnioDex.SmartMessages.managers.MessageManager;
import me.UnioDex.SmartMessages.managers.MessageManager.PlayerType;

@SuppressWarnings("deprecation")
public class SmartMessages extends JavaPlugin {

	public MessageManager messageManager;

	@Override
	public void onEnable() {

		if (!Bukkit.getPluginManager().isPluginEnabled("ASkyBlock")) {
			throw new RuntimeException("Could not find ASkyBlock! Plugin can not work without it!");
		}

		saveDefaultConfig();
		messageManager = new MessageManager(this);
		getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
		new MainCommand(this);
		setupTimer();
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	private void setupTimer() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				String beginnerMessage = messageManager.getNextMessage(PlayerType.BEGINNER);
				String intermediateMessage = messageManager.getNextMessage(PlayerType.INTERMEDIATE);
				String masterMessage = messageManager.getNextMessage(PlayerType.MASTER);

				for (Player p : getServer().getOnlinePlayers()) {
					if (messageManager.getPlayerType(p).equals(PlayerType.BEGINNER)) {
						p.sendMessage(beginnerMessage.replaceAll("%player%", p.getName()));
					}
					if (messageManager.getPlayerType(p).equals(PlayerType.INTERMEDIATE)) {
						p.sendMessage(intermediateMessage.replaceAll("%player%", p.getName()));
					}
					if (messageManager.getPlayerType(p).equals(PlayerType.MASTER)) {
						p.sendMessage(masterMessage.replaceAll("%player%", p.getName()));
					}
				}
			}
		}, 180*20L, 120*20L);
	}

}