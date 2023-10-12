package com.w45GeTracking;

import com.google.gson.Gson;
import com.google.inject.Provides;

import javax.inject.Inject;

import com.w45GeTracking.priceApi.PriceApi;
import com.w45GeTracking.priceApi.PriceApiImpl;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GrandExchangeOfferChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.Arrays;

@Slf4j
@PluginDescriptor(
        name = "W45 Ge Tracker"
)
public class W45GeTracking extends Plugin {
    final static Logger logger = LoggerFactory.getLogger(W45GeTracking.class);
    @Inject
    private ClientToolbar clientToolbar;

    private NavigationButton button;
    @Inject
    private Gson gson;
    @Inject
    private LoginTracker loginTracker;
    @Inject
    private Client client;
    @Inject
    public GePluginPanel panel;
    @Inject
    private PriceApiImpl priceApi;
    @Subscribe
    public void onGrandExchangeOfferChanged(GrandExchangeOfferChanged offerEvent) {

        final GrandExchangeOffer offer = offerEvent.getOffer();
        if (
                eventIsBuyOrSellEvent(offer)
                && loginTracker.hasBeenLoggedInForAWhile()
                && onPermDmmWorld()
        ) {
            try {
                TransactionInfo transactionInfo = new ConvertedTransactionInfo(offer);
                priceApi.addTransaction(transactionInfo);
            }catch(Exception e){
                // ignore exception, we just skip the trade if there is an issue.
                logger.debug("Error {}", e.getMessage());
            }
        }
    }
    public boolean onPermDmmWorld(){
        // client.getWorldType().contains(WorldType.DEADMAN)
        return Arrays.stream(new int[]{ 345 }).anyMatch(e -> e == client.getWorld());
    }
    private boolean eventIsBuyOrSellEvent(GrandExchangeOffer offer){
        return offer.getState() == GrandExchangeOfferState.BOUGHT
                || offer.getState() == GrandExchangeOfferState.SOLD;
    }
    @Override
    public void startUp(){
        clientToolbar.addNavigation(W45NavButton.create(panel));
    }
}