package com.w45GeTracking;

import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.swing.JPanel;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import java.awt.*;
import java.lang.annotation.Target;

public class GePluginPanel extends PluginPanel {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GeSearchPanel.class);
    private final String warningMessage =
            "This plugin sends trade data (id, price, buy/sell) to 3rd party servers for price tracking. "
                    + "Prices reported by this plugin may be inaccurate or false. "
                    + "Prices could be manipulated by plugin maintainers or other users. ";
    @Inject
    public GePluginPanel(GeSearchPanel searchPanel) {
        super(false);
        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        JPanel warningPanel = new WarningPanel(warningMessage, searchPanel);
        add(warningPanel, BorderLayout.CENTER);
    }
}
