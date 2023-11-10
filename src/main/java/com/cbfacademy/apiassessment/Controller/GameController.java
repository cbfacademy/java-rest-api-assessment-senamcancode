package com.cbfacademy.apiassessment.Controller;


import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/game")
public class GameController {

    @Autowired
    private GameService gameService;


    @PostMapping("/start")
    public ResponseEntity<Object> startNewGame() {
        gameService.newGame();

        return ResponseEntity.ok("New game started and data written to file");
    }


    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestParam int numberOfEmployees, String gameId){
        try {
            gameService.addEmployee(gameId, numberOfEmployees);
            return ResponseEntity.ok(numberOfEmployees + " Employee(S) added.");
        } catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding employee(s): " + e.getMessage());
        }

    }

    //is this a valid post request or a get request?
    @PostMapping("/crowd-fund")
    public ResponseEntity<String> crowdFund(@RequestParam String gameId) throws InvalidActionException {
        try{
            gameService.crowdFund(gameId);
            return ResponseEntity.ok("Crowd fund was successful");
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error crowd funding: " + e.getMessage());
        }


    }

    @GetMapping("/advance-turn/{gameId}")
    public ResponseEntity<String> advanceTurn(@PathVariable("gameId") String gameId){
        gameService.advanceTurn(gameId);

        return ResponseEntity.ok("You have advanced to the next turn");
    }

    //you need to be able to show the data from the company and the event that occured - how would i do this? can i have gameRepository methods in the game controller section?


    //In which situation for this game would I be using a put request?


//
//    @PostMapping("/advance-turn")
//    public ResponseEntity<String> advanceTurn(){
//        if(game != null){
//            game.advanceTurn(); //advance the turn in the game
//            return ResponseEntity.ok("Turn advanced to " + game.getCurrentTurn());
//        } else {
//            return ResponseEntity.badRequest().body("No active game found.");
//        }
//    }

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


