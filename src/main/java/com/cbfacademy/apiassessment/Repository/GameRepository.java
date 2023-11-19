package com.cbfacademy.apiassessment.Repository;


import com.cbfacademy.apiassessment.Database.Database;
import com.cbfacademy.apiassessment.EventDeserializer;
import com.cbfacademy.apiassessment.FinTechClasses.Event;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.QuickSortAlgo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.google.gson.reflect.TypeToken;


@Repository
public class GameRepository {

    //for vs code the String relativePath = "java-rest-api-assessment-senamcancode/src/main/game-data.json"
    //for IntelliJ the String relativePath = "/src/main/game-data.json"

    public static String getFilePath(){
        String relativePath = "java-rest-api-assessment-senamcancode/src/main/game-data.json";
        Path path = Paths.get(System.getProperty("user.dir"), relativePath);
        return path.toString();
    }


    public void saveGameData(Database database) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(database.getGames());
        try (FileWriter writer = new FileWriter(getFilePath())) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();

            throw new FileNotFoundException("Error writing to file");
        }
    }


    public void appendGameData()  throws FileNotFoundException{
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader(getFilePath())) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> gamesList = gson.fromJson(reader, gameListType.getType());


            if(!gamesList.isEmpty()){
                gamesList.add(new Game());
            }


            try (FileWriter writer = new FileWriter(getFilePath())) {
                gson.toJson(gamesList, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();

            throw new FileNotFoundException("Error writing to file");
        }
    }


    public void updateGameDataById(String gameId, Game updatedGame) throws FileNotFoundException{
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader(getFilePath())) {
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

                try (FileWriter writer = new FileWriter(getFilePath())) {
                    gson.toJson(gamesList, writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            throw new FileNotFoundException("Error writing to file");
        }
    }

    public static Game retrieveGame(String gameId) throws FileNotFoundException{

        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader(getFilePath())) {
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

            throw new FileNotFoundException("Error reading file");

        }
        return null;
    }

    public void deleteGameById(String gameId, Game deletedGame) throws FileNotFoundException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader(getFilePath())) {
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

                try (FileWriter writer = new FileWriter(getFilePath())) {
                    gson.toJson(gamesList, writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
           throw new FileNotFoundException("Error reading file");
        }
    }

    public List<Game> getAllGames() throws FileNotFoundException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Event.class, new EventDeserializer()).setPrettyPrinting().create();

        try (FileReader reader = new FileReader(getFilePath())) {
            TypeToken<List<Game>> gameListType = new TypeToken<List<Game>>() {
            };
            List<Game> gamesList = gson.fromJson(reader, gameListType.getType());

            List<Game> sortedGames = QuickSortAlgo.quickSort(gamesList, 0, gamesList.size() - 1);

            return sortedGames;

    } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException("Error reading file");
        }

    }
}

