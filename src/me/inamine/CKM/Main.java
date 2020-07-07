package me.inamine.CKM;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	Utils u = new Utils();
	private static Main inst;

	public Main()
	{
		inst = this;
	}

	public static Main getInst()
	{
		return inst;
	}

	public void onEnable()
	{
		FileManager.checkFiles();
		saveDefaultConfig();
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ListenerClass(), this);
		for (World w : Bukkit.getWorlds())
		{
			w.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
		}
	}

	public void onDisable()
	{
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			if (args.length == 0)
			{
				sender.sendMessage(u.getMessage("no-console"));
				return true;
			} else if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("reload"))
				{
					reloadConfig();
					FileManager.checkFiles();
					sender.sendMessage(u.getMessage("reload-complete"));
					return true;
				}
			}
		} else
		{
			Player pl = (Player) sender;
			if (args.length == 0)
			{
				Menu m = new Menu();
				m.openMenu(pl);
				return true;
			} else if (args.length == 1 && pl.hasPermission("customkm.reload"))
			{
				if (args[0].equalsIgnoreCase("reload"))
				{
					reloadConfig();
					FileManager.checkFiles();
					sender.sendMessage(u.getMessage("reload-complete"));
					return true;
				}
			}
		}

		return false;
	}

}
