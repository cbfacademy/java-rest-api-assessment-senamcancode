package com.cbfacademy.apiassessment.Repository;


import com.cbfacademy.apiassessment.Database.Database;
import com.cbfacademy.apiassessment.EventDeserializer;
import com.cbfacademy.apiassessment.FinTechClasses.Event;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.reflect.TypeToken;


@Repository
public class GameRepository {



    public void saveGameData(Database database) {
        //if the game-data.json is null then do this
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(database.getGames()); //this will change to the database class :)
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

//    public void updateGameData(Game game){
//        //this method should append the updated game to the database object then write that to the "game-data" json file
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(game); //this will change to the database class :)
//        //because I am going to have 1 JSON file called the database with each game containing all the saved games
//        try (FileWriter writer = new FileWriter("game-data.json")) {
//            writer.write(json);
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //if there is a game-data.json already just append it to the json file
//    }

    //i don't know how this should work if you have more than one game!
    public void updateGameDataById(String gameId, Game updatedGame) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader("game-data.json")) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {};
            List<Game> games = gson.fromJson(reader, gameListType.getType());

            if (games != null) {
                for (int i =0; i < games.size(); i++){
                    if (games.get(i).getGameId().equals(gameId)){
                        games.set(i,updatedGame);
                        break;
                    }
                }

                try (FileWriter writer = new FileWriter("game-data.json")) {
                    gson.toJson(games, writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game retrieveGame(String gameId) { //use the relative path! & need gameId as argument
        String filePath = "game-data.json";
        //Need to find the specific game by id

        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader(filePath)) {
            Type gameListType = new TypeToken<List<Game>>(){}.getType();
            List<Game> gamesList = gson.fromJson(reader, gameListType);


            if (gamesList != null) {
                for (Game game : gamesList) {
                    if (game.getGameId().equals(gameId)) {
                        return game;
                    }
                }
            }  //the gameId needs to used to find a specific which matches it
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    }


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

//    }
//}

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
