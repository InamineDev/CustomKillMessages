package me.inamine.CKM;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Menu
{
	Utils u = new Utils();

	public void openMenu(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, u.getMessage("inventory-title"));

		ConfigurationSection config = Main.getInst().getConfig();
		Set<String> keys = config.getConfigurationSection("messages").getKeys(false);
		for (String s : keys)
		{
			ItemStack item = new ItemStack(Material.STONE);
			ItemMeta meta = item.getItemMeta();
			if (player.hasPermission("customkm." + s))
			{
				String matst = config.getString("messages." + s + ".material");
				if (Material.matchMaterial(matst) != null)
				{
					item.setType(Material.matchMaterial(matst));
				}
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						config.getString("messages." + s + ".display-name")));
				List<String> conlore = config.getStringList("messages." + s + ".lore");
				List<String> lore = new ArrayList<>();
				for (String l : conlore)
				{
					l = l.replace("%lock-state%", FileManager.getMsg().getString("unlocked"));
					lore.add(ChatColor.translateAlternateColorCodes('&', l));
				}
				meta.setLore(lore);
				item.setItemMeta(meta);

			} else
			{
				String matst = config.getString("locked-icon-material");
				if (Material.matchMaterial(matst) != null)
				{
					item.setType(Material.matchMaterial(matst));
				}
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						config.getString("messages." + s + ".display-name")));
				List<String> conlore = config.getStringList("messages." + s + ".lore");
				List<String> lore = new ArrayList<>();
				for (String l : conlore)
				{
					l = l.replace("%lock-state%", FileManager.getMsg().getString("locked"));
					lore.add(ChatColor.translateAlternateColorCodes('&', l));
				}
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
			inv.addItem(item);
		}

		player.openInventory(inv);
	}

}
