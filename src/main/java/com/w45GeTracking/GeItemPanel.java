package com.w45GeTracking;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import net.runelite.api.ItemComposition;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.QuantityFormatter;

import static com.w45GeTracking.W45GeTracking.logger;

/**
 * This panel displays an individual item result in the
 * Grand Exchange search plugin.
 */
public class GeItemPanel extends JPanel
{
    private static final Dimension ICON_SIZE = new Dimension(32, 32);
    private final ItemManager itemManager;
    private final ClientThread clientThread;
    private final int id;
    private final Price price;
    public GeItemPanel(ClientThread clientThread, ItemManager itemManager, int id, Price price)
    {
        this.itemManager = itemManager;
        this.clientThread = clientThread;
        this.id = id;
        this.price = price;
        BorderLayout layout = new BorderLayout();
        layout.setHgap(5);
        setLayout(layout);
        setBorder(new EmptyBorder(5, 5, 5, 0));
        setupMouseListeners();
        setupToolTip();
        add(createItemIcon(), BorderLayout.WEST);
        add(createInfoPanel(), BorderLayout.CENTER);
        setBackgroundNormal();
    }
    public void setupToolTip(){
        clientThread.invoke(() -> {
            setToolTipText(itemManager.getItemComposition(id).getName());
        });
    }
    public JLabel createItemIcon(){
        JLabel itemIcon = new JLabel();
        itemIcon.setPreferredSize(ICON_SIZE);
        clientThread.invoke(() -> {
            AsyncBufferedImage icon = itemManager.getImage(id);
            if (icon != null) {
                icon.addTo(itemIcon);
            }
        });
        return itemIcon;
    }
    public JPanel createInfoPanel(){
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        infoPanel.add(createItemName(), c);
        c.gridx = 0;
        c.gridy = 1;
        infoPanel.add(createDataGrid(), c);
        return infoPanel;
    }
    public JPanel createDataGrid(){
        JPanel dataGrid = new JPanel(new GridLayout(2, 2));
        JLabel[] left = new JLabel[]{
                createBuyPrice(),
                createSellPrice(),
        };
        JLabel[] right = new JLabel[]{
                createBuyTime(),
                createSellTime(),
        };
        Arrays.stream(right).forEach(c -> c.setHorizontalAlignment(SwingConstants.RIGHT));
        int rows = left.length;
        for (int i = 0; i < rows; i++) {
            dataGrid.add(left[i]);
            //right[i].setAlignmentX(1);
            dataGrid.add(right[i]);
        }
        return dataGrid;
    }
    public JLabel createItemName(){
        JLabel itemName = new JLabel();
        itemName.setForeground(Color.WHITE);
        //itemName.setMaximumSize(new Dimension(0, 0));        // to limit the label's size for
        //itemName.setPreferredSize(new Dimension(0, 0));    // items with longer names
        clientThread.invoke(() -> {
            itemName.setText(itemManager.getItemComposition(id).getName());
        });
        return itemName;
    }
    private JLabel createAlchPrice(){
        JLabel haPriceLabel = new JLabel();
        haPriceLabel.setForeground(ColorScheme.GRAND_EXCHANGE_ALCH);
        clientThread.invoke(() -> {
            haPriceLabel.setText(QuantityFormatter.formatNumber(itemManager.getItemComposition(id).getHaPrice()) + " alch");
        });
        return haPriceLabel;
    }
    private JLabel createBuyPrice(){
        return prefixLabel("B: ", createGePrice(price.buy.price));
    }
    private JLabel createSellPrice(){
        return prefixLabel("S: ", createGePrice(price.sell.price));
    }
    private JLabel createBuyTime(){
        return createTime(price.buy.time);
    }
    private JLabel createSellTime(){
        return createTime(price.sell.time);
    }
    private JLabel createTime(long time){
        JLabel label = new JLabel();
        label.setForeground(ColorScheme.GRAND_EXCHANGE_PRICE);
        if(time != 0){
            label.setText(formatTime(System.currentTimeMillis() - time));
        }else {
            label.setText("N/A");
        }
        return label;

    }
    private JLabel prefixLabel(String prefix, JLabel label){
        label.setText(prefix + label.getText());
        return label;
    }
    private String formatTime(long ms) {
        return formatTime(ms, "");
    }
    private String formatTime(long ms, String separator){
        long t;
        String type;
        if(ms >= TimeIntervals.d){
            t = ms / TimeIntervals.d;
            type = "d";
        }else if(ms >= TimeIntervals.h){
            t = ms / TimeIntervals.h;
            type = "h";
        }else if(ms >= TimeIntervals.min){
            t = ms / TimeIntervals.min;
            type = "min";
        }else {
            t = ms / TimeIntervals.s;
            type = "s";
        };
        return t + separator + type;
    }
    private JLabel createGePrice(int gp){
        JLabel label = new JLabel();
        label.setForeground(ColorScheme.GRAND_EXCHANGE_PRICE);
        label.setText(formatPrice(gp));
        return label;
    }
    public String formatPrice(int gp){
        if(gp > 0){
            return QuantityFormatter.formatNumber(price.buy.price) + " gp";
        }else{
            return "N/A";
        }
    }
    private void setupMouseListeners(){
        MouseAdapter itemPanelMouseListener = new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setHover(true);
            }
            @Override
            public void mouseExited(MouseEvent e)
            {
                setHover(false);
            }
        };
        addMouseListener(itemPanelMouseListener);
    }
    private void setHover(boolean hover){
        if(hover){
            setBackgroundHover();
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            setBackgroundNormal();
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    private void setBackgroundNormal(){
        setBackgroundIncludingChildren(this, ColorScheme.DARKER_GRAY_COLOR);
    }
    private void setBackgroundHover(){
        setBackgroundIncludingChildren(this, ColorScheme.DARKER_GRAY_HOVER_COLOR);
    }
    private void setBackgroundIncludingChildren(JPanel panel, Color color)
    {
        panel.setBackground(color);
        for (Component c : panel.getComponents())
        {
            if(c instanceof JPanel){
                setBackgroundIncludingChildren((JPanel) c, color);
            }else{
                c.setBackground(color);
            }
        }
    }
}