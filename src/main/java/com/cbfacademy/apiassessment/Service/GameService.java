package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;


//@Service
//    public class GameService {
//        private Game game; // Assuming game is initialized somewhere
//
//
//        public void startNewGame() throws IOException{
//            this.game = new Game();
//
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String json = gson.toJson(game);
//
//            //how can I write the file to a specific place in the directory? - would i make this part of the JSON controller?
//            try (FileWriter writer = new FileWriter("game-data.json")) {
//                writer.write(json);
//            } catch (IOException e) {
//                throw new IOException("Error occured while writing game data to file");
//            }
//        }
//
//        public void advanceTurn() {
//            if (game != null && !game.isGameCompleted()) {
//                game.advanceTurn(); // Advancing the turn in the game
//                // Other logic to handle turn advancements
//            }
//        }
//
//
//    }

//}
