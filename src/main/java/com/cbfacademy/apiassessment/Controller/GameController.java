package com.cbfacademy.apiassessment.Controller;


import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.Service.GameService;
import org.apache.coyote.Response;
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


    //@PutMapping("/company-name)
    //public ResponseEntity<String> addCompanyName(){
    //  gameService.addName();
    // }

    @PostMapping("/add-employee")
    //need to chang the next 2 methods so that the numberofemployees information is not coming directly form the client
    public ResponseEntity<String> addEmployee(@RequestParam int numberOfEmployees, String gameId) {
        try {
            gameService.actionsManager(gameId);

            int initEmployees = gameService.getEmployees(gameId);

            gameService.addEmployee(gameId, numberOfEmployees);

            int newEmployees = gameService.getEmployees(gameId);

            int employeesAdded = newEmployees - initEmployees;

            return ResponseEntity.ok(employeesAdded + " Employee(s) successfully added. You now have a total of " + newEmployees + " employees");

        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Adding employee(s) error: " + e.getMessage());
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Actions error: " + e.getMessage());
        }

    }

    @PutMapping("/remove-employee")
    public ResponseEntity<String> removeEmployee(@RequestParam int numberOfEmployees, String gameId) {
        try {
            gameService.actionsManager(gameId);
            int initEmployees = gameService.getEmployees(gameId);

            gameService.removeEmployee(gameId, numberOfEmployees);

            int newEmployees = gameService.getEmployees(gameId);

            int employeesRemoved = initEmployees - newEmployees;

            return ResponseEntity.ok(employeesRemoved + " Employee(s) successfully removed. You now have a total of " + newEmployees + " employees");

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Removing employee(s) error: " + e.getMessage());
        }
    }

        @PostMapping("/crowd-fund")
    public ResponseEntity<String> crowdFund(@RequestParam String gameId) throws InvalidActionException {
        try {
            gameService.actionsManager(gameId);
            gameService.crowdFund(gameId);

            String formattedRevenue = gameService.getFormattedRevenue(gameId);
            return ResponseEntity.ok("Crowd fund was successful. You now have £ " + formattedRevenue);

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Crowd funding error: " + e.getMessage());
        }

    }

    @PostMapping("/invest/{action}")
    public ResponseEntity<String> invest(@PathVariable String action, @RequestParam String gameId) throws InvalidActionException{
        try {
            gameService.actionsManager(gameId);
            if("sniper".equals(action)){
                gameService.sniperInvest(gameId);
                //need to find a way to tell the user that they have lost or gained money
                return ResponseEntity.ok("Sniper investment successfully made");
            }

            if ("passive".equals(action)){
                gameService.passiveInvest(gameId);
                return ResponseEntity.ok("Passive investment successfully made");
            }

        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Investing error: " + e.getMessage());
        }

        return null;
    }


    @PostMapping("/add-department")
    public ResponseEntity<String> addDepartment(@RequestParam String gameId) throws InvalidActionException {
        try {
            gameService.actionsManager(gameId);
            gameService.addDepartment(gameId);
            int numberOfDepartments = gameService.getDepartments(gameId);
            return ResponseEntity.ok("Department added. You now have " +  numberOfDepartments + " departments");
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Add department error: " + e.getMessage());
        }
    }

    @PostMapping("research-and-dev")
    public ResponseEntity<String> researchAndDev(@RequestParam String gameId) throws InvalidActionException{
        try {
            gameService.actionsManager(gameId);
            gameService.researchAndDev(gameId);

            int productXP = gameService.getProductXP(gameId);
            return ResponseEntity.ok("Research and development success, 2 XP added to the product. You now have a product XP of " + productXP);
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Research and development error: " + e.getMessage());
        }
    }

    @PostMapping("marketing")
    public ResponseEntity<String> marketing(@RequestParam String gameId) throws InvalidActionException{
        try {
            gameService.actionsManager(gameId);
            gameService.market(gameId);

            int customerBase = gameService.getCustomerBase(gameId);
            return ResponseEntity.ok("Marketing was successful. You now have a customer base of " + customerBase);
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Marketing error: " + e.getMessage());
        }
    }

    @GetMapping("/get-company/{gameId}")
    public ResponseEntity<Company> companyInfo(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok(gameService.getCompany(gameId));
    }

    @GetMapping("/get-game/{gameId}")
    public ResponseEntity<Game> gameInfo(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @GetMapping("/get-turn/{gameId}")
    public ResponseEntity<Integer> getCurrentTurn(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok(gameService.getCurrentTurn(gameId));
    }

    @GetMapping("/get-actions/{gameId}")
    public ResponseEntity<Integer> getActionsRemaining(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok(gameService.getNumberOfRemainingActions(gameId));
    }

    @GetMapping("/advance-turn/{gameId}")
    public ResponseEntity<String> advanceTurn(@PathVariable("gameId") String gameId) {
        gameService.advanceTurn(gameId);
        return ResponseEntity.ok("You have advanced to the next turn" );
    }
}

    //you need to be able to show the data from the company and the event that occured - how would i do this? can i have gameRepository methods in the game controller section?


    //In which situation for this game would I be using a put request?
    //in the situation of changing the number of employees


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


