package me.eplugins.velocityaddon.expansion;

import lombok.Getter;
import lombok.Setter;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.eplugins.velocityaddon.EGlowAddon;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

@Setter
@Getter
public class TABExpansion {
	private boolean expansionEnabled;

	public TABExpansion() {
		EGlowAddon.getInstance().logger.info("[Glow-Addon] Enabling TABExtension.");
		setExpansionEnabled(true);
	}

	public void updateColor(UUID uuid, String glowColor) {
		TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(uuid);

		if (tabPlayer == null)
			return;

		if (TabAPI.getInstance().getNameTagManager() != null) {
			String tagPrefix = TabAPI.getInstance().getNameTagManager().getOriginalPrefix(tabPlayer);

			switch (glowColor.toLowerCase()) {
				case "red" -> glowColor = color("&c");
				case "dark_red" -> glowColor = color("&4");
				case "gold" -> glowColor = color("&6");
				case "yellow" -> glowColor = color("&e");
				case "aqua" -> glowColor = color("&b");
				case "dark_aqua" -> glowColor = color("&3");
				case "blue" -> glowColor = color("&9");
				case "dark_blue" -> glowColor = color("&1");
				case "green" -> glowColor = color("&a");
				case "dark_green" -> glowColor = color("&2");
				case "light_purple" -> glowColor = color("&d");
				case "dark_purple" -> glowColor = color("&5");
				case "white" -> glowColor = color("&f");
				case "gray" -> glowColor = color("&7");
				case "dark_gray" -> glowColor = color("&8");
				case "black" -> glowColor = color("&0");
				case "reset" -> glowColor = "";
			}
			
			if (glowColor.isEmpty()) {
				TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, null);
			} else {
				TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, ((!tagPrefix.isEmpty()) ? tagPrefix : "") + glowColor);
			}

		}

		if (TabAPI.getInstance().getTabListFormatManager() != null) {
			String tabPrefix = TabAPI.getInstance().getTabListFormatManager().getOriginalPrefix(tabPlayer);
			TabAPI.getInstance().getTabListFormatManager().setPrefix(tabPlayer, tabPrefix);
		}
	}

	public String color(String msg) {
		return MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(msg));
	}
}