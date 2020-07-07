package me.inamine.CKM;

import net.md_5.bungee.api.ChatColor;

public class Utils
{

	public String getMessage(String st)
	{
		String msg = FileManager.getMsg().getString(st);
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}
}