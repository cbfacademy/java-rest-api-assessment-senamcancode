package com.cbfacademy.apiassessment.Controller;


//import com.cbfacademy.apiassessment.Service.GameService;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.Service.GameService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("api/game")
public class GameController {
    private Game game;
    private Company company;

    @Autowired
    private GameService gameService;

//    @PostMapping("/start")
//    public ResponseEntity<Object> startNewGame() {
//        try {
//            gameService.startNewGame();
//            return ResponseEntity.ok("New game started and data written to file.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error occurred while writing game data to file");
//
//        }
//
//    }

    @PostMapping("/start")
    public ResponseEntity<Object> startNewGame() {
        this.game = new Game();
        this.company = game.getCompany();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(game);

        try (FileWriter writer = new FileWriter("game-data.json")) {
            writer.write(json);
            writer.flush();
            return ResponseEntity.ok("New game started and data written to file.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred while writing game data to file");
        }
    }



    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestParam int numberOfEmployees){if (company.hasSufficientFunds(numberOfEmployees)) {
            company.addEmployee(numberOfEmployees);
            int totalEmployees = company.getEmployees();
            return ResponseEntity.ok(numberOfEmployees + " Employee(S) added. You now have " + totalEmployees);

        } else {
            return ResponseEntity.ok("You don't have sufficient funds to employ " + numberOfEmployees + "employee(s)");
        }

    }

    @PostMapping("/advance-turn")
    public ResponseEntity<String> advanceTurn(){
        if(game != null){
            game.advanceTurn(); //advance the turn in the game
            return ResponseEntity.ok("Turn advanced to " + game.getCurrentTurn());
        } else {
            return ResponseEntity.badRequest().body("No active game found.");
        }
    }

}

//should probably have an update method which applies all the changes in the game object ie company too and writes it to the JSON file before giong to the next turn
//    @RestController
//    @RequestMapping("/game")
//    public class GameController {
//        private GameService gameService; // Inject GameService
//
//        @PostMapping("/start")
//        public ResponseEntity<Object> startNewGame() {
//            gameService.startNewGame(); // Start a new game
//            // Other logic related to starting a game
//            return ResponseEntity.ok("New game started.");
//        }
//
//        @PostMapping("/advance-turn")
//        public ResponseEntity<String> advanceTurn() {
//            gameService.advanceTurn(); // Advance the turn in the game
//            // Other logic related to advancing a turn eg resetting various values
//            return ResponseEntity.ok("Turn advanced.");
//        }
//
//        // Other endpoint handling game actions and responses
//    }

//@PostMapping("/start")
//    public ResponseEntity<Object> start(){
//        try {
//            gameService.startNewGame();
//            return ResponseEntity.ok("New game started and data written to file.");
//        } catch (IOException e){
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error occurred while writing game data to file");
//        }
//    }


