package me.inamine.CKM;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.md_5.bungee.api.ChatColor;

public class ListenerClass implements Listener
{
	Utils u = new Utils();
	@EventHandler
	public void onInvClick(InventoryClickEvent e)
	{
		if (e.getView().getTitle().equals(u.getMessage("inventory-title")))
		{
			e.setCancelled(true);
		}
	}
	

	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		if (!e.getEntity().hasMetadata("NPC"))
		{
			Player vic = e.getEntity().getPlayer();
			if (vic.getKiller() != null && vic.getKiller() instanceof Player && !vic.getKiller().hasMetadata("NPC"))
			{
				Player killer = vic.getKiller();
				ConfigurationSection config = Main.getInst().getConfig();
				Set<String> keys = config.getConfigurationSection("messages").getKeys(false);
				List<String> messages = new ArrayList<>();
				for (String s : keys)
				{
					if (killer.hasPermission("customkm." + s))
					{
						messages.add(config.getString("messages." + s + ".message"));
					}
				}
				String message = "";
				if (messages.isEmpty())
				{
					message = config.getString("default-message");
				}
				else if (messages.size() == 1)
				{
					message = messages.get(0);
				}
				else
				{
					int size = messages.size();
					Random r = new Random();
					int choice = r.nextInt(size);
					message = messages.get(choice);
					
				}
				message = message.replace("%killer%", killer.getName());
				message = message.replace("%victim%", vic.getName());
				message = ChatColor.translateAlternateColorCodes('&', message);
				Bukkit.getServer().broadcastMessage(message);
			}
		}
	}

}
