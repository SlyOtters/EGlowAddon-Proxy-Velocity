package me.eplugins.velocityaddon;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import lombok.Getter;
import lombok.Setter;
import me.eplugins.velocityaddon.expansion.TABExpansion;
import org.slf4j.Logger;

import java.util.UUID;

@Plugin(
		id = "eglowaddon-velocity",
		name = "eglowaddon-velocity",
		version = "5.0",
		authors = {"ePlugins"},
		dependencies = {
				@Dependency(id = "tab")
		})
public class EGlowAddon{

	@Getter
	public static EGlowAddon instance;

	@Getter
	@Setter
	public static TABExpansion tabExpansion;

	private final MinecraftChannelIdentifier IDENTIFIER = MinecraftChannelIdentifier.from("eglow:bungee");

	public final ProxyServer server;
	public final Logger logger;

	@Inject
	public EGlowAddon(ProxyServer server, Logger logger) {
		this.server = server;
		this.logger = logger;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		logger.info("[Glow-Addon] Enabling ProxyExtension.");
		server.getChannelRegistrar().register(IDENTIFIER);
		instance = this;
		if (server.getPluginManager().getPlugin("tab").isPresent())
			setTabExpansion(new TABExpansion());
	}

	@Subscribe
	public void onPluginMessageFromBackend(PluginMessageEvent event) {
		// Ensure the identifier is what you expect before trying to handle the data

		if (event.getIdentifier() != IDENTIFIER || !(event.getSource() instanceof ServerConnection)) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

		if(in.readUTF().equals("TABProxyUpdateRequest")) {
			if (EGlowAddon.getTabExpansion() != null) {
				EGlowAddon.getTabExpansion().updateColor(UUID.fromString(in.readUTF()), in.readUTF());
			}
		}
	}
}
