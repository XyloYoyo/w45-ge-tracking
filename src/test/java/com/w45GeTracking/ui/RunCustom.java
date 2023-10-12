package com.w45GeTracking.ui;

import com.w45GeTracking.W45GeTracking;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RunCustom {
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(GeItemListPlugin.class);
        RuneLite.main(args);
    }
}
