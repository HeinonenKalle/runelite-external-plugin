package com.worldindicator;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface WolrdIndicatorConfig extends Config
{
	@ConfigItem(
		keyName = "Separator_text",
		name = "Separator character",
		description = "Write the character you want to use as a separator between names and the world"
	)
	default String Separator()
	{
		return "|";
	}
}
