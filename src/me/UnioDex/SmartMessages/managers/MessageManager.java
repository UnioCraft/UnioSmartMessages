package me.UnioDex.SmartMessages.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.UnioDex.SmartMessages.SmartMessages;

public class MessageManager {

	private SmartMessages plugin;

	private List<String> beginnerMessages;
	private List<String> intermediateMessages;
	private List<String> masterMessages;

	public MessageManager(SmartMessages plugin) {
		this.plugin = plugin;
		init();
	}

	public void init() {
		List<String> configBeginnerMessages = plugin.getConfig().getStringList("messages.beginner");
		List<String> configIntermediateMessages = plugin.getConfig().getStringList("messages.intermediate");
		List<String> configMasterMessages = plugin.getConfig().getStringList("messages.master");
		List<String> configEveryoneMessages = plugin.getConfig().getStringList("messages.everyone");

		beginnerMessages = new ArrayList<String>();
		intermediateMessages = new ArrayList<String>();
		masterMessages = new ArrayList<String>();

		int i = 0;
		while(true) {
			int exceptionCount = 0;
			try {
				beginnerMessages.add(configBeginnerMessages.get(i));
			}catch (IndexOutOfBoundsException e) {
				exceptionCount++;
			}
			try {
				beginnerMessages.add(configEveryoneMessages.get(i));
			}catch (IndexOutOfBoundsException e) {
				exceptionCount++;
			}

			if (exceptionCount == 2) {
				i = 0;
				break;
			}

			i++;
		}

		while(true) {
			int exceptionCount = 0;
			try {
				intermediateMessages.add(configIntermediateMessages.get(i));
			}catch (IndexOutOfBoundsException e) {
				exceptionCount++;
			}
			try {
				intermediateMessages.add(configEveryoneMessages.get(i));
			}catch (IndexOutOfBoundsException e) {
				exceptionCount++;
			}

			if (exceptionCount == 2) {
				i = 0;
				break;
			}

			i++;
		}

		while(true) {
			int exceptionCount = 0;
			try {
				masterMessages.add(configMasterMessages.get(i));
			}catch (IndexOutOfBoundsException e) {
				exceptionCount++;
			}
			try {
				masterMessages.add(configEveryoneMessages.get(i));
			}catch (IndexOutOfBoundsException e) {
				exceptionCount++;
			}

			if (exceptionCount == 2) {
				i = 0;
				break;
			}

			i++;
		}
	}

	public String getNextMessage(PlayerType type) {
		if (type.equals(PlayerType.BEGINNER)) {
			String msg = beginnerMessages.get(0);
			beginnerMessages.add(beginnerMessages.remove(0));
			return ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%prefix1%", plugin.getConfig().getString("prefix1")).replaceAll("%prefix2%", plugin.getConfig().getString("prefix2")).replaceAll("%prefix3%", plugin.getConfig().getString("prefix3")));
		}

		if (type.equals(PlayerType.INTERMEDIATE)) {
			String msg = intermediateMessages.get(0);
			intermediateMessages.add(intermediateMessages.remove(0));
			return ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%prefix1%", plugin.getConfig().getString("prefix1")).replaceAll("%prefix2%", plugin.getConfig().getString("prefix2")).replaceAll("%prefix3%", plugin.getConfig().getString("prefix3")));
		}

		if (type.equals(PlayerType.MASTER)) {
			String msg = masterMessages.get(0);
			masterMessages.add(masterMessages.remove(0));
			return ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%prefix1%", plugin.getConfig().getString("prefix1")).replaceAll("%prefix2%", plugin.getConfig().getString("prefix2")).replaceAll("%prefix3%", plugin.getConfig().getString("prefix3")));
		}

		return null;
	}

	public PlayerType getPlayerType(Player player) {
		if (!ASkyBlockAPI.getInstance().hasIsland(player.getUniqueId())) {
			return PlayerType.BEGINNER;
		} else {
			if (ASkyBlockAPI.getInstance().getLongIslandLevel(player.getUniqueId()) >= 10000) {
				return PlayerType.MASTER;
			} else if (ASkyBlockAPI.getInstance().getLongIslandLevel(player.getUniqueId()) >= 250) {
				return PlayerType.INTERMEDIATE;
			} else {
				return PlayerType.BEGINNER;
			}
		}
	}

	public enum PlayerType {
		BEGINNER, INTERMEDIATE, MASTER;
	}
}

