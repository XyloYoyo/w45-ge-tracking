package com.w45GeTracking;

import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarningPanel extends JPanel {
    private static final String WARNING_PANEL = "WARNING_PANEL";
    private static final String CONTENT_PANEL = "CONTENT_PANEL";
    private final CardLayout cardLayout = new CardLayout();
    public WarningPanel(String warningMessage, JComponent content){
        setLayout(cardLayout);
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        add(createWarningPanel(warningMessage), WARNING_PANEL);
        add(content, CONTENT_PANEL);
        displayWarning();
    }
    private JPanel createWarningPanel(String warningMessage){
        JPanel p = new JPanel();
        p.setBackground(ColorScheme.DARK_GRAY_COLOR);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(createWarningLabel(warningMessage));
        p.add(createConfirmButton());
        return p;
    }
    private JLabel createWarningLabel(String warningMessage){
        JLabel label = new JLabel();
        String style = ""
                + "width: 100%;"
                + "text-align: center;";
        String warningHeader = "<h2>Warning</h2>";
        final String html = "<html><body style='" + style + "'>" + warningHeader;
        label.setForeground(Color.WHITE);
        label.setText(html + warningMessage + "<br/><br/>");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    private JButton createConfirmButton(){
        JButton button = new JButton();
        button.setText("I understand");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                hideWarning();
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    private void hideWarning(){
        displayContent();
    }
    private void displayWarning(){
        cardLayout.show(this, WARNING_PANEL);
    }
    private void displayContent(){
        cardLayout.show(this, CONTENT_PANEL);
    }
}
