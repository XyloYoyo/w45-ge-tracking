package com.w45GeTracking;


import com.google.common.base.Strings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.w45GeTracking.priceApi.PriceApi;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemComposition;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.CustomScrollBarUI;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.http.api.item.ItemPrice;
import net.runelite.http.api.item.ItemStats;

public class GeSearchPanel extends JPanel
{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GeSearchPanel.class);
    @Inject
    private ItemManager itemManager;
    @Inject
    private RuneLiteConfig runeLiteConfig;

    private final IconTextField searchBar;
    private final GeItemListPanel itemListPanel;
    @Inject
    public GeSearchPanel(GeItemListPanel itemListPanel)
    {
        this.itemListPanel = itemListPanel;
        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(5, 5));
        container.setBorder(new EmptyBorder(10, 10, 10, 10));
        container.setBackground(ColorScheme.DARK_GRAY_COLOR);
        searchBar = createSearchBar();
        container.add(searchBar, BorderLayout.NORTH);
        container.add(itemListPanel.rootPanel, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
    }
    private IconTextField createSearchBar(){
        IconTextField searchBar = new IconTextField();
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(100, 30));
        searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARKER_GRAY_HOVER_COLOR);
        /*
        I don't know if this is a dev environment issue,
        but the searchbar text is dark gray by default and afaik there's no options to change it
         */
        setColorOfAllTextFields(searchBar, Color.lightGray);
        searchBar.addActionListener(e -> updateSearch());
        searchBar.addClearListener(this::updateSearch);
        return searchBar;
    }
    private void updateSearch()
    {
        String lookup = searchBar.getText();
        if (Strings.isNullOrEmpty(lookup))
        {
            itemListPanel.clearItems();
            itemListPanel.displayHomePage();
            searchBarReady();
        }else {
            processLookup(lookup);
        }
    }
    private void processLookup(String lookup){
        searchBarLoading();
        try {
            List<ItemPrice> results = itemManager.search(lookup);
            int maxSize = 7;
            if(results.size() > maxSize){
                results.subList(maxSize, results.size()).clear();
            }
            if (results.isEmpty()){
                itemListPanel.displayError("No results found");
            } else {
                itemListPanel.setItems(results.stream().mapToInt(ItemPrice::getId).toArray());
                itemListPanel.displayResults();
            }
        }catch(Exception e){
            logger.debug(e.toString());
            itemListPanel.displayError(e.getMessage());
            searchBarReady();
        }finally {
            searchBarReady();
            searchBar.requestFocusInWindow();
        }

    }
    private void searchBarLoading(){
        searchBar.setEditable(false);
        searchBar.setIcon(IconTextField.Icon.LOADING);
    }
    private void searchBarReady(){
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setEditable(true);
    }
    private void setColorOfAllTextFields(JPanel panel, Color color){
        for (Component c : panel.getComponents()){
            if(c instanceof JPanel) {
                setColorOfAllTextFields((JPanel) c, color);
            }else if(c instanceof JTextField){
                c.setForeground(color);
            }
        }
    }
}