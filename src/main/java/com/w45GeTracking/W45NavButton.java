package com.w45GeTracking;

import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

public class W45NavButton {
    public static NavigationButton create(PluginPanel pluginPanel){
        final BufferedImage icon = ImageUtil.loadImageResource(W45GeTracking.class, "/law-rune.png");
        NavigationButton button = NavigationButton.builder()
                .tooltip("W45 Grand Exchange Tracker")
                .icon(icon)
                .priority(3)
                .panel(pluginPanel)
                .build();
        return button;
    }
}
