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
public class GeItemPlugin extends Plugin {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GeSearchPanel.class);
    @Inject
    private ClientToolbar clientToolbar;
    @Inject
    private GeItemPanelFactory geItemPanelFactory;
    @Override
    public void startUp(){
        JPanel panel = geItemPanelFactory.create(12817, examplePrice() );
        clientToolbar.addNavigation(W45NavButton.create(new PanelPluginPanel(panel, false)));
    }
    public static Price examplePrice(){
        Price price = new Price();
        price.buy = new DatedPrice(1697002742000L, 123);
        price.sell = new DatedPrice(1697001742000L, 456);
        return price;
    }
}
