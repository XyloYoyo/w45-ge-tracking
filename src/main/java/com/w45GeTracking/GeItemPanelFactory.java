package com.w45GeTracking;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;

public class GeItemPanelFactory {
    @Inject
    private ItemManager itemManager;
    @Inject
    private ClientThread clientThread;
    public GeItemPanel create(int id, Price price){
        return new GeItemPanel(clientThread, itemManager, id, price);
    }
}
