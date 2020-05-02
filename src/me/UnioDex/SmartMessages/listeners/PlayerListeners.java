package me.UnioDex.SmartMessages.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.events.IslandNewEvent;

import me.UnioDex.SmartMessages.SmartMessages;

public class PlayerListeners implements Listener {

	private SmartMessages plugin;

	public PlayerListeners(SmartMessages plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (!ASkyBlockAPI.getInstance().hasIsland(player.getUniqueId())) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					if (!ASkyBlockAPI.getInstance().hasIsland(player.getUniqueId())) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.noIsland")
								.replaceAll("%prefix1%", plugin.getConfig().getString("prefix1"))
								.replaceAll("%prefix2%", plugin.getConfig().getString("prefix2"))
								.replaceAll("%prefix3%", plugin.getConfig().getString("prefix3"))
								.replaceAll("%player%", player.getName())));
					}
				}
			}, 40L);
		}
	}

	@EventHandler
	public void onIslandCreate(IslandNewEvent event) {
		final Player player = event.getPlayer();

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.islandCreateMessage")
						.replaceAll("%prefix1%", plugin.getConfig().getString("prefix1"))
						.replaceAll("%prefix2%", plugin.getConfig().getString("prefix2"))
						.replaceAll("%prefix3%", plugin.getConfig().getString("prefix3"))
						.replaceAll("%player%", player.getName())));
			}
		}, 30L);
	}
}
