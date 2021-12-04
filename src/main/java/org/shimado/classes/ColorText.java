package org.shimado.classes;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;

public class ColorText {

	public static String colortext(String text) {
		return text != null ? ChatColor.translateAlternateColorCodes('&', text) : "";
	}
	
	public static String title(String text) {
		String result = "";
		for (int i = 0; i < (38 - colortext(text).length())/2; i++) {
			result = result.concat(" ");
		}
		return result.concat(colortext(text));
	}
	
	public static List<String> colorarray(List<String> array){
		return array.stream().map(p -> p = colortext(p)).collect(Collectors.toList());
	}
}
