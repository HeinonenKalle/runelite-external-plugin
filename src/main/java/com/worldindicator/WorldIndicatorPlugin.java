package com.worldindicator;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.FriendsChatMember;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "World Indicator"
)
public class WorldIndicatorPlugin extends Plugin
{
	@Inject
	private Client client;


	@Inject
	private WolrdIndicatorConfig config;



	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent scriptCallbackEvent){
		switch (scriptCallbackEvent.getEventName()){
			case"chatMessageBuilding":
			{
				int uid = client.getIntStack()[client.getIntStackSize() -1];
				final MessageNode messageNode = client.getMessages().get(uid);
				assert messageNode != null : "unknown message";
				//Chat message type means is it public chat, friend chat ect.
				final ChatMessageType messageType = messageNode.getType();
				switch (messageType)
				{
					case FRIENDSCHAT :
						final String[] stringStack = client.getStringStack();
						final int stringSize = client.getStringStackSize();
						final String name = stringStack[stringSize - 3];
						//with just name the world isn't always displayed so the tags need to be removed
						final int member_world = getWorld(Text.removeTags(name));
						stringStack[stringSize - 3] = name + " " + config.Separator() + " World: " + String.valueOf(member_world);
						break;
					default:
						return;
				}
			}
		}

	}



	private int getWorld(String playerName){
		final FriendsChatManager friendsChatManager = client.getFriendsChatManager();
		if (friendsChatManager == null || playerName == ""){
			return 0;
		}
		FriendsChatMember member = friendsChatManager.findByName(playerName);
		return member.getWorld();
	}

	@Provides
	WolrdIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WolrdIndicatorConfig.class);
	}
}
