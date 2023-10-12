package com.w45GeTracking.ui;

import com.w45GeTracking.*;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;

import javax.inject.Inject;
import javax.swing.*;

@PluginDescriptor(
        name = "W45 ge tracker"
)
public class GeItemListPlugin extends Plugin {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GeSearchPanel.class);
    @Inject
    private ClientToolbar clientToolbar;
    @Inject
    private GeItemListPanel geItemListPanel;
    @Override
    public void startUp() throws Exception{
        clientToolbar.addNavigation(W45NavButton.create(new PanelPluginPanel(geItemListPanel.rootPanel, true)));
        geItemListPanel.setItems(new int[]{1, 2, 3});
        geItemListPanel.displayResults();
    }
}
