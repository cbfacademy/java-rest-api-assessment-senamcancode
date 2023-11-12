package com.cbfacademy.apiassessment.Repository;


import com.cbfacademy.apiassessment.Database.Database;
import com.cbfacademy.apiassessment.EventDeserializer;
import com.cbfacademy.apiassessment.FinTechClasses.Event;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.reflect.TypeToken;


@Repository
public class GameRepository {

    public void saveGameData(Database database) {
        //if the game-data.json is null then do this
        //File file = new File("game-data.json")
        //if(!file.exists()){
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
    }
//        }
//        if there is a game-data.json already just append it to the json file
//         if(file.exists()){
//         String existingContent = new String(Files.readAllBytes(Paths.get("game-data.json)));
//         if(!existingContent.trim().ieEmpty()){
//         existingContent = existingContent.substring(0, existingContent.lastIndexOf("])) + ",";
//         }
//         json = existingContent + json
//
//        Files.write(Paths.get("game-data.json), json.getBytes());
//

    //}


    public void appendGameData(Database database) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Game game = new Game();
        //need to read the database first
        database.addGame(game);

        String json = gson.toJson(database.getGames());

        try (FileWriter writer = new FileWriter("game-data.json")) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //I don't know how this should work if you have more than one game!
    public void updateGameDataById(String gameId, Game updatedGame) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader("game-data.json")) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> games = gson.fromJson(reader, gameListType.getType());

            if (games != null) {
                for (int i = 0; i < games.size(); i++) {
                    if (games.get(i).getGameId().equals(gameId)) {
                        games.set(i, updatedGame);
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
            Type gameListType = new TypeToken<List<Game>>() {
            }.getType();
            List<Game> gamesList = gson.fromJson(reader, gameListType);


            if (gamesList != null) {
                for (Game game : gamesList) {
                    if (game.getGameId().equals(gameId)) {
                        return game;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteGameById(String gameId, Game deletedGame){
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader("game-data.json")) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> games = gson.fromJson(reader, gameListType.getType());

            if (games != null) {
                for (int i = 0; i < games.size(); i++) {
                    if (games.get(i).getGameId().equals(gameId)) {
                        games.remove(i);
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

}


        //1. read the JSON file
        //2. deserialise into the java object array
        //3. find the object that has the gameId
        //return the object with the id
        //then in the addemployee method in the game service call the retreiveData method of the gameRepository and add the employee to the object
        //then call the gameRepository method of the saveGamedata
