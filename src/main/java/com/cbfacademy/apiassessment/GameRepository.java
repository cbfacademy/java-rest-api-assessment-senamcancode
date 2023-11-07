package com.cbfacademy.apiassessment;


import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



@Repository
public class GameRepository {
    public void saveGameData(Game game) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(game);
        try (FileWriter writer = new FileWriter("game-data.json")) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Game retrieveGame(String gameId){ //use the relative path! & need gameId as argument
        String filePath = "/Users/senam/Documents/Code/entry-to-tech/CBF Final Project/CBF-final-project/java-rest-api-assessment-senamcancode/game-data.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileReader reader = new FileReader(filePath)){
            return gson.fromJson(reader, Game.class);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
    //need retrieveAllGames methods to return an array of games

}
