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
    @Inject
    public GePluginPanel(GeSearchPanel panel) {
        super(false);
        logger.debug("GePluginPanel panel {}", panel == null);
        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        add(panel, BorderLayout.CENTER);
    }
}
