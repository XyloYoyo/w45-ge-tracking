package com.w45GeTracking.ui;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;

public class PanelPluginPanel extends PluginPanel {
    public PanelPluginPanel(JPanel panel, boolean stretch){
        super(false);
        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        if(stretch){
            add(panel, BorderLayout.CENTER);
        }else{
            add(panel, BorderLayout.NORTH);
        }
    }
}
