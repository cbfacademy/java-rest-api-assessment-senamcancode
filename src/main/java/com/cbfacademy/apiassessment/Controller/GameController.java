package com.cbfacademy.apiassessment.Controller;


import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


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

    @PostMapping("/append-new-game")
    public ResponseEntity<Object> appendNewGame() throws FileNotFoundException {
        gameService.appendNewGame();

        return ResponseEntity.ok("New game started and data appended to file");
    }


    @PutMapping("/company-name")
    public ResponseEntity<String> setCompanyName(@RequestParam String companyName, String gameId) throws FileNotFoundException {
        gameService.nameCompany(gameId, companyName);

        String newCompanyName = gameService.getCompany(gameId).getCompanyName();
        return ResponseEntity.ok("Your FinTech Company name was successfully changed to " + newCompanyName);
    }


    @PostMapping("/add-employee")
    //need to chang the next 2 methods so that the numberofemployees information is not coming directly form the client
    public ResponseEntity<String> addEmployee(@RequestParam int numberOfEmployees, String gameId) throws FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            int initEmployees = gameService.getEmployees(gameId);

            gameService.addEmployee(gameId, numberOfEmployees);
            gameService.actionsManager(gameId);

            int newEmployees = gameService.getEmployees(gameId);

            int employeesAdded = newEmployees - initEmployees;

            if (gameService.checkGameIsCompleted(gameId)) {


                return ResponseEntity.ok("Congratulations!Your company reached IPO status! You completed the game!!");
            }

            return ResponseEntity.ok(employeesAdded + " Employee(s) successfully added. You now have a total of " + newEmployees + " employees");

        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Adding employee(s) error: " + e.getMessage());
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Actions error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("JSON file not found.");
        }

    }

    @PutMapping("/remove-employee")
    public ResponseEntity<String> removeEmployee(@RequestParam int numberOfEmployees, String gameId) throws FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            int initEmployees = gameService.getEmployees(gameId);

            gameService.removeEmployee(gameId, numberOfEmployees);
            gameService.actionsManager(gameId);

            int newEmployees = gameService.getEmployees(gameId);

            int employeesRemoved = initEmployees - newEmployees;

            return ResponseEntity.ok(employeesRemoved + " Employee(s) successfully removed. You now have a total of " + newEmployees + " employees");

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Removing employee(s) error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("JSON file not found.");
        }
    }

    @PostMapping("/crowd-fund")
    public ResponseEntity<String> crowdFund(@RequestParam String gameId) throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            gameService.crowdFund(gameId);
            gameService.actionsManager(gameId);


            String formattedRevenue = gameService.getFormattedRevenue(gameId);
            return ResponseEntity.ok("Crowd fund was successful. You now have £ " + formattedRevenue);

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Crowd funding error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("JSON file not found.");
        }

    }

    @PostMapping("/invest/{action}")
    public ResponseEntity<String> invest(@PathVariable String action, @RequestParam String gameId) throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }


            String resultMessage;

            if ("sniper".equals(action)) {
                resultMessage = gameService.sniperInvest(gameId);
                gameService.actionsManager(gameId);
                //need to find a way to tell the user that they have lost or gained money
                return ResponseEntity.ok("Sniper investment successfully made: " + resultMessage);
            }

            if ("passive".equals(action)) {
                resultMessage = gameService.passiveInvest(gameId);
                gameService.actionsManager(gameId);
                return ResponseEntity.ok("Passive investment successfully made: " + resultMessage);
            }

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Investing error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("JSON file not found.");
        }

        return null;
    }


    @PostMapping("/add-department")
    public ResponseEntity<String> addDepartment(@RequestParam String gameId) throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            gameService.addDepartment(gameId);
            gameService.actionsManager(gameId);

            int numberOfDepartments = gameService.getDepartments(gameId);
            return ResponseEntity.ok("Department added. You now have " + numberOfDepartments + " department(s)");
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Add department error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("JSON file not found.");
        }
    }

    @PostMapping("/research-and-dev")
    public ResponseEntity<String> researchAndDev(@RequestParam String gameId) throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            gameService.researchAndDev(gameId);
            gameService.actionsManager(gameId);

            int productXP = gameService.getProductXP(gameId);
            return ResponseEntity.ok("Research and development success, 2 XP added to the product. You now have a product XP of " + productXP);
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Research and development error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("JSON file not found.");
        }
    }

    @PostMapping("/marketing")
    public ResponseEntity<String> marketing(@RequestParam String gameId) throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            gameService.market(gameId);
            gameService.actionsManager(gameId);

            int customerBase = gameService.getCustomerBase(gameId);
            return ResponseEntity.ok("Marketing was successful. You now have a customer base of " + customerBase);
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Marketing error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("JSON file not found.");
        }
    }


    @GetMapping("get-all-games")
    public ResponseEntity<List<Game>> getAllGames() throws FileNotFoundException {
            return ResponseEntity.ok(gameService.getAllGames());

    }

    @GetMapping("/get-company/{gameId}")
    public ResponseEntity<Company> companyInfo(@PathVariable("gameId") String gameId) throws FileNotFoundException {
        return ResponseEntity.ok(gameService.getCompany(gameId));
    }

    @GetMapping("/get-game/{gameId}")
    public ResponseEntity<Game> gameInfo(@PathVariable("gameId") String gameId) throws FileNotFoundException {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @GetMapping("/get-turn/{gameId}")
    public ResponseEntity<String> getCurrentTurn(@PathVariable("gameId") String gameId) throws FileNotFoundException {
        return ResponseEntity.ok("You are currently in turn " + gameService.getCurrentTurn(gameId) + " of 20");
    }

    @GetMapping("/get-actions-num/{gameId}")
    public ResponseEntity<Integer> getActionsRemaining(@PathVariable("gameId") String gameId) throws FileNotFoundException {
        return ResponseEntity.ok(gameService.getNumberOfRemainingActions(gameId));
    }

    @PostMapping("/advance-turn/{gameId}")
    public ResponseEntity<String> advanceTurn(@PathVariable("gameId") String gameId) throws InvalidActionException {
        try {
            if(gameService.checkGameIsOver(gameId)){
                return ResponseEntity.ok("GAME OVER! You failed to reach IPO status!");
            }

            if(gameService.checkGameIsCompleted(gameId)){
                return ResponseEntity.ok("CONGRATULATIONS!!!: Your company has successfully reached IPO status. You beat the game!");
            }

            String resultMessage = gameService.triggerRandomEvent(gameId);

            gameService.advanceTurn(gameId);
            return ResponseEntity.ok("You have advanced to the next turn\n " + resultMessage);
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Game Over: " + e.getMessage());

        }
    }


    @DeleteMapping("/delete-game")
    public ResponseEntity<String>deleteGame(@RequestParam String gameId) throws FileNotFoundException {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok("You successfully deleted the game");
    }

    @PutMapping("/money")
    public ResponseEntity<String> moneyMoneyMoney(@RequestParam String gameId) throws FileNotFoundException {
        gameService.moneyMoneyMoney(gameId);
        return ResponseEntity.ok("Money money money! Must be funny in a rich man's world! Here's £9999999 on us!");
    }

    @PutMapping("/motherlode")
    public ResponseEntity<String> motherLoad(@RequestParam String gameId) throws FileNotFoundException {
        gameService.motherLode(gameId);

        return ResponseEntity.ok("Your FinTech Company has all it needs for IPO status - Don't worry your secret's safe with us ;)");
    }

}


