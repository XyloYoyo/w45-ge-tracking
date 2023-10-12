package com.w45GeTracking;

import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.api.events.GrandExchangeOfferChanged;

public class ConvertedTransactionInfo extends TransactionInfo {
    public ConvertedTransactionInfo(GrandExchangeOffer offer) throws Exception {
        if (offer.getState() == GrandExchangeOfferState.BOUGHT) {
            this.type = TransactionType.BUY;
        } else if (offer.getState() == GrandExchangeOfferState.SOLD) {
            this.type = TransactionType.SELL;
        } else {
            throw new Exception("invalid state");
        }
        this.id = offer.getItemId();
        int requestedPrice = offer.getPrice();
        int actualSpent = offer.getSpent();
        int quantity = offer.getQuantitySold();
        int actualPrice = Math.round(((float) actualSpent) / (float) quantity);
        this.price = actualPrice;
    }
}