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
        String json = gson.toJson(game); //this will change to the database class :)
        //because I am going to have 1 JSON file called the database with each game contained all the saved games
        //as an array of game objects
        try (FileWriter writer = new FileWriter("game-data.json")) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if there is a game-data.json already just append it to the json file
    }

    public void updateGameData(Game game){
        //this method should append the updated game to the database object then write that to the "game-data" json file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(game); //this will change to the database class :)
        //because I am going to have 1 JSON file called the database with each game containing all the saved games
        try (FileWriter writer = new FileWriter("game-data.json")) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if there is a game-data.json already just append it to the json file
    }


    public Game retrieveGame(String gameId) { //use the relative path! & need gameId as argument
        String filePath = "/Users/senam/Documents/Code/entry-to-tech/CBF Final Project/CBF-final-project/java-rest-api-assessment-senamcancode/game-data.json";
        //Need to find the specific game by id

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader reader = new FileReader(filePath)) {
            Game[] gamesArray = gson.fromJson(reader, Game[].class);

            if (gamesArray != null){
                for(Game game: gamesArray){
                 if(game.getGameId().equals(gameId)){
                     return game;
                 }
                }
            }  //the gameId needs to used to find a specific which matches it
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

        //1. read the JSON file
        //2. deserialise into the java object array
        //3. find the object that has the gameId
        //return the object with the id
        //then in the addemployee method in the game service call the retreiveData method of the gameRepository and add the employee to the object
        //then call the gameRepository method of the saveGamedata


//    } public Game retrieveGame(String gameId){ //use the relative path! & need gameId as argument
//        String filePath = "/Users/senam/Documents/Code/entry-to-tech/CBF Final Project/CBF-final-project/java-rest-api-assessment-senamcancode/game-data.json";
//        //Need to find the specific game by id
//
//        String id = game.getGameId();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try(FileReader reader = new FileReader(filePath)){
//            return gson.fromJson(reader, Game.class); //the gameId needs to used to find a specific which matches it
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//
//        //1. read the JSON file
//        //2. deserialise into the java object array
//        //3. find the object that has the gameId
//        //return the object with the id
//        //then in the addemployee method in the game service call the retreiveData method of the gameRepository and add the employee to the object
//        //then call the gameRepository method of the saveGamedata
//
//
//    }
        //need retrieveAllGames methods to return an array of games

    }
}

//    public void saveGameData(Game game) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(game); //this will change to the database class :)
//        //because I am going to have 1 JSON file called the database with each game containing all the saved games
//        try (FileWriter writer = new FileWriter("game-data.json")) {
//            writer.write(json);
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
