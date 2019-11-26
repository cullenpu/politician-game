package com.example.politicgame.GameMode;

import android.content.Context;
import android.content.Intent;

import com.example.politicgame.Application.PoliticGameApp;
import com.example.politicgame.GameEnd.SummaryActivity;

public class StampArcade extends ArcadeMode{
    private static final String LEVEL_NAME = "LEVEL3";

    public StampArcade(/*PoliticGameApp app*/){
        super(/*app,*/ LEVEL_NAME);
    }

    /**
     * Returns the Intent to the next required activity
     *
     * @param lastActivity  The activity this class was instantiated in
     * @return              The Intent to the next required activity
     */
    public Intent next(Context lastActivity){
        Intent switchSummaryIntent = new Intent(lastActivity, SummaryActivity.class);
        return switchSummaryIntent;
    }
}