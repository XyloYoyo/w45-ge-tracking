package com.w45GeTracking;

import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class LoginTracker {
    @Inject
    private Client client;
    private int lastLoginTick = 0;

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        switch (gameStateChanged.getGameState())
        {
            case LOGIN_SCREEN:
                lastLoginTick = client.getTickCount();
            case LOGGING_IN:
                lastLoginTick = client.getTickCount();
            case HOPPING:
                lastLoginTick = client.getTickCount();
            case CONNECTION_LOST:
                lastLoginTick = client.getTickCount();
            case LOGGED_IN:
                lastLoginTick = client.getTickCount();
        }
    }
    public boolean hasBeenLoggedInForAWhile(){
        return getTicksSinceLogin() > 2;
    }
    public int getTicksSinceLogin(){
        return client.getTickCount() - lastLoginTick;
    }

}
