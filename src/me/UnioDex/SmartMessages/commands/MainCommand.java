package me.UnioDex.SmartMessages.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.UnioDex.SmartMessages.SmartMessages;
import net.md_5.bungee.api.ChatColor;

public class MainCommand implements CommandExecutor {

	private SmartMessages plugin;

	public MainCommand(SmartMessages plugin) {
		this.plugin = plugin;
		plugin.getCommand("usm").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("usm.admin") || !sender.isOp()) {
			return false;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				plugin.reloadConfig();
				plugin.messageManager.init();
				sender.sendMessage(ChatColor.RED + "UnioSmartMessages configi yenilendi.");
				return true;
			}
		}

		return false;
	}

}
