package com.cbfacademy.apiassessment.Repository;


import com.cbfacademy.apiassessment.Database.Database;
import com.cbfacademy.apiassessment.EventDeserializer;
import com.cbfacademy.apiassessment.FinTechClasses.Event;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.SortAlgo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.reflect.TypeToken;


@Repository
public class GameRepository {

//this is the one that works!
    public void saveGameData(Database database) {
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


    public void appendGameData()  {
            //this method should only be run if there is a game-data.json file
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader("game-data.json")) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> gamesList = gson.fromJson(reader, gameListType.getType());


            if(!gamesList.isEmpty()){
                gamesList.add(new Game());
            }


            try (FileWriter writer = new FileWriter("game-data.json")) {
                gson.toJson(gamesList, writer);
            }


            //once you have the gamesList you want to check that the game isnt already present in the games list by gameId
            //if its not present you add the game to the gamesList
            //if it is present you do not add the game to the gamesList
            //then you write everything in the gamesList to the game-data.json file

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateGameDataById(String gameId, Game updatedGame) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader("game-data.json")) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> gamesList = gson.fromJson(reader, gameListType.getType());

            if (gamesList != null) {
                for (int i = 0; i < gamesList.size(); i++) {
                    if (gamesList.get(i).getGameId().equals(gameId)) {
                        gamesList.set(i, updatedGame);
                        break;
                    }
                }

                try (FileWriter writer = new FileWriter("game-data.json")) {
                    gson.toJson(gamesList, writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game retrieveGame(String gameId) { //use the relative path! & need gameId as argument
        String filePath = "game-data.json";

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

    public void deleteGameById(String gameId, Game deletedGame) throws FileNotFoundException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader("game-data.json")) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> gamesList = gson.fromJson(reader, gameListType.getType());

            if (gamesList != null) {
                for (int i = 0; i < gamesList.size(); i++) {
                    if (gamesList.get(i).getGameId().equals(gameId)) {
                        gamesList.remove(i);
                        break;
                    }
                }

                try (FileWriter writer = new FileWriter("game-data.json")) {
                    gson.toJson(gamesList, writer);
                }
            }
        } catch (IOException e) {
           throw new FileNotFoundException();
        }
    }

    public List<Game> getAllGames() throws FileNotFoundException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader("game-data.json")) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> gamesList = gson.fromJson(reader, gameListType.getType());

            List<Game> sortedGames = SortAlgo.quickSort(gamesList, 0, gamesList.size() - 1);

            return sortedGames;

    } catch (IOException e) {
            throw new FileNotFoundException();
        }

    }
}


        //1. read the JSON file
        //2. deserialise into the java object array
        //3. find the object that has the gameId
        //return the object with the id
        //then in the addemployee method in the game service call the retreiveData method of the gameRepository and add the employee to the object
        //then call the gameRepository method of the saveGamedata
