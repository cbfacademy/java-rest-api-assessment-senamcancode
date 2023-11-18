package com.cbfacademy.apiassessment.Database;

import com.cbfacademy.apiassessment.FinTechClasses.Game;

import java.util.List;
import java.util.ArrayList;

public class Database {
    private List<Game> games;

    public Database(){
        games = new ArrayList<>();
    }

    public List<Game> getGames(){
        return games;
    }

    public void addGame(Game game){
        games.add(game);
    }

}
