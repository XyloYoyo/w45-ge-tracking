package com.w45GeTracking;

import com.w45GeTracking.priceApi.PriceApi;
import com.w45GeTracking.priceApi.PriceApiImpl;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.CustomScrollBarUI;
import net.runelite.client.ui.components.PluginErrorPanel;
import org.apache.commons.lang3.NotImplementedException;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GeItemListPanel {
    @Inject
    private PriceApiImpl priceApi;
    @Inject
    private GeItemPanelFactory geItemPanelFactory;
    private final JPanel searchItemsPanel;
    private final PluginErrorPanel errorPanel = new PluginErrorPanel();

    private final GridBagConstraints constraints;
    private final CardLayout cardLayout = new CardLayout();
    public final JPanel rootPanel = new JPanel(cardLayout);
    private static final String ERROR_PANEL = "ERROR_PANEL";
    private static final String RESULTS_PANEL = "RESULTS_PANEL";

    public GeItemListPanel(){
        searchItemsPanel = createSearchItemsPanel();
        constraints = createGridBagConstraints();
        rootPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        rootPanel.add(getSearchPanelWrapper(searchItemsPanel), RESULTS_PANEL);
        rootPanel.add(getErrorWrapper(errorPanel), ERROR_PANEL);
        displayHomePage();

    }
    public void setItems(int[] ids) throws Exception {
        clearItems();
        Price[] prices = priceApi.getPrices(ids);
        for(int i = 0; i < ids.length; i++){
            GeItemPanel itemPanel = geItemPanelFactory.create(ids[i], prices[i]);
            JPanel panel;
            if(i == 0){
                panel = getWrappedItemPanel(itemPanel);
            }else{
                panel = itemPanel;
            }
            searchItemsPanel.add(panel, constraints);
            constraints.gridy++;
        }
    }
    private JPanel getWrappedItemPanel(GeItemPanel panel){
        JPanel marginWrapper = new JPanel(new BorderLayout());
        marginWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
        marginWrapper.setBorder(new EmptyBorder(5, 0, 0, 0));
        marginWrapper.add(panel, BorderLayout.NORTH);
        return marginWrapper;
    }
    public void clearItems() {
        searchItemsPanel.removeAll();
        searchItemsPanel.repaint();
        constraints.gridy = 0;
    }
    public void displayResults(){
        displayResultsPanel();
    }
    public void displayHomePage(){
        errorPanel.setContent(
                "W45 Grand Exchange Search",
                "Here you can search for an item by its name to find price information."
        );
        displayErrorPanel();
    }
    public void displayError(String message){
        errorPanel.setContent("Error", message);
        displayErrorPanel();
    }
    private void displayResultsPanel(){
        cardLayout.show(rootPanel, RESULTS_PANEL);
    }
    private void displayErrorPanel(){
        cardLayout.show(rootPanel, ERROR_PANEL);
    }
    private GridBagConstraints createGridBagConstraints(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        return c;
    }
    private JPanel getErrorWrapper(JPanel errorPanel){
        JPanel errorWrapper = new JPanel(new BorderLayout());
        errorWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
        errorWrapper.add(errorPanel, BorderLayout.NORTH);
        return errorWrapper;
    }
    private JPanel createSearchItemsPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        return panel;
    }
    private JComponent getSearchPanelWrapper(JPanel searchPanel){
        // This panel wraps the results panel and guarantees the scrolling behaviour
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
        wrapper.add(searchPanel, BorderLayout.NORTH);
        return wrapper;
        /*
        // The results wrapper, this scrolling panel wraps the results container
        JScrollPane resultsWrapper = new JScrollPane(wrapper);
        resultsWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
        resultsWrapper.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
        resultsWrapper.getVerticalScrollBar().setBorder(new EmptyBorder(0, 5, 0, 0));
        resultsWrapper.getVerticalScrollBar().setBackground(ColorScheme.DARK_GRAY_COLOR);
        resultsWrapper.getVerticalScrollBar().setBackground(ColorScheme.DARK_GRAY_COLOR);
        CustomScrollBarUI.createUI(resultsWrapper.getVerticalScrollBar());
        resultsWrapper.setVisible(false);
        return resultsWrapper;
        */
    }

}
