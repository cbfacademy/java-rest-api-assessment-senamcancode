package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    public void newGame() {
        Game game = new Game();
        Company company = game.getCompany();

        gameRepository.saveGameData(game);
    }

    public void addEmployee(String gameId, int numberOfEmployees){
        Game game = gameRepository.retrieveGame(gameId);
        //game.company.addEmployee(numberOfEmployees)

        gameRepository.saveGameData(game);

    }
}




//    public void startNewGame() throws IOException{
////        this.game = new Game();
//
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(game);
//
//        try (FileWriter writer = new FileWriter("game-data.json")) {
//         writer.write(json);
//         writer.flush();
//        } catch (IOException e) {
//         throw new IOException("Error occurred while writing game data to file: " + e.getMessage());
//        }
//    }

//    public void addEmployee(int numberOfEmployees){
//        if(company.hasSufficientFunds(numberOfEmployees)) {
//            company.addEmployee(numberOfEmployees);
//        }
//    }
//}



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
