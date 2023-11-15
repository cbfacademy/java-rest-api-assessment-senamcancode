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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<Object> startNewGame() {
        try {
            gameService.newGame();

            return ResponseEntity.ok("New game started and data written to file");
        }catch (FileNotFoundException e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error starting the game: " + e.getMessage());
        }

    }

    @PostMapping("/add-new-game")
    public ResponseEntity<Object> addNewGame() {
        try {
            gameService.appendNewGame();

            return ResponseEntity.ok("New game started and data appended to file");
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File Not Found: Unable to add a new game.");
        }
    }

    @PutMapping("/company-name")
    public ResponseEntity<String> setCompanyName(@RequestParam String companyName, String gameId) {
        try {
            gameService.nameCompany(gameId, companyName);

            String newCompanyName = gameService.getCompany(gameId).getCompanyName();
            return ResponseEntity.ok("Your FinTech Company name was successfully changed to " + newCompanyName);
        }catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File Not Found: Unable to change company name.");
        }
    }

    @PostMapping("/add-employee")
    // need to chang the next 2 methods so that the numberofemployees information is
    // not coming directly form the client
    public ResponseEntity<String> addEmployee(@RequestParam int numberOfEmployees, String gameId)
            throws FileNotFoundException {
        try {

            if (gameService.checkGameIsCompleted(gameId)) {
                return ResponseEntity.ok("You completed the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsOver(gameId)) {
                return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
            }

            int initEmployees = gameService.getEmployees(gameId);

            //Invalid action - You can only make 3 actions per turn. Advance turn to get access to more actions"

            String resultMessage = gameService.addEmployee(gameId, numberOfEmployees);
            gameService.actionsManager(gameId);


            int newEmployees = gameService.getEmployees(gameId);
            int employeesAdded = newEmployees - initEmployees;


            return ResponseEntity.ok(employeesAdded + " employee(s) added" + resultMessage + " You have " + newEmployees + " employee(s)");
            //might also add the number of departments


        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: Unable to add employee(s)");
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Action error: " + e.getMessage());
        }

    }

    @PutMapping("/remove-employee")
    public ResponseEntity<String> removeEmployee(@RequestParam int numberOfEmployees, String gameId)
            throws FileNotFoundException {
        try {
            if (gameService.checkGameIsCompleted(gameId)) {
                return ResponseEntity.ok("You completed the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsOver(gameId)) {
                return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
            }

            double initRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));
            int initEmployees = gameService.getEmployees(gameId);

            String resultMessage = gameService.removeEmployee(gameId, numberOfEmployees);
            gameService.actionsManager(gameId);

            int newEmployees = gameService.getEmployees(gameId);

            int employeesRemoved = initEmployees - newEmployees;

            int numOfDepartments = gameService.getDepartments(gameId);

            double moneySaved = Double.parseDouble(gameService.getFormattedRevenue(gameId)) - initRevenue; ;

            return ResponseEntity.ok(employeesRemoved + " employee(s) removed and £" + moneySaved + " saved" + resultMessage + "\n You have "
                    + newEmployees + " employees and " + numOfDepartments + " department(s)");

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Action error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: Unable to remove employee(s)");
        }
    }

    @PostMapping("/crowd-fund")
    public ResponseEntity<String> crowdFund(@RequestParam String gameId)
            throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsCompleted(gameId)) {
                return ResponseEntity.ok("You completed the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsOver(gameId)) {
                return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
            }


            gameService.crowdFund(gameId);
            gameService.actionsManager(gameId);

            String formattedRevenue = gameService.getFormattedRevenue(gameId);
            return ResponseEntity.ok("Crowd fund was successful! You now have £" + formattedRevenue);

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Action error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: Unable to crowd fund.");
        }

    }

    @PostMapping("/invest/{action}")
    public ResponseEntity<String> invest(@PathVariable String action, @RequestParam String gameId) throws FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                return ResponseEntity.ok("You completed the game, you cannot take any more actions");
            }

            String resultMessage;

            if ("sniper".equals(action)) {
                resultMessage = gameService.sniperInvest(gameId);
                gameService.actionsManager(gameId);
                // need to find a way to tell the user that they have lost or gained money
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
            throw new FileNotFoundException("File Not Found: Unable to make investment");
        }

        return null;
    }

    @PostMapping("/add-department")
    public ResponseEntity<String> addDepartment(@RequestParam String gameId) throws FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                return ResponseEntity.ok("You completed the game, you cannot take any more actions");
            }

            String resultMessage = gameService.addDepartment(gameId);
            gameService.actionsManager(gameId);

            int numberOfDepartments = gameService.getDepartments(gameId);
            return ResponseEntity.ok(numberOfDepartments + " department(s) added" + resultMessage);
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Action error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File Not Found: Unable to add department");
        }
    }

    @PostMapping("/research-and-dev")
    public ResponseEntity<String> researchAndDev(@RequestParam String gameId)
            throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            String resultMessage = gameService.researchAndDev(gameId);
            gameService.actionsManager(gameId);

            int productXP = gameService.getProductXP(gameId);
            return ResponseEntity
                    .ok(resultMessage + "You have a product XP of "
                            + productXP);
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Action error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: Unable to add to do research and development");
        }
    }

    @PostMapping("/marketing")
    public ResponseEntity<String> marketing(@RequestParam String gameId)
            throws InvalidActionException, FileNotFoundException {
        try {
            if (gameService.checkGameIsOver(gameId)) {
                throw new InvalidActionException("You failed to complete the game, you cannot take any more actions");
            }

            if (gameService.checkGameIsCompleted(gameId)) {
                throw new InvalidActionException("You completed the game, you cannot take any more actions");
            }

            String resultMessage = gameService.market(gameId);
            gameService.actionsManager(gameId);

            int customerBase = gameService.getCustomerBase(gameId);
            return ResponseEntity.ok(resultMessage + "You have a customer base of " + customerBase);
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Marketing error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: unable to market.");
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

    //not sure why but this does not throw an exception
    @GetMapping("/get-game/{gameId}")
    public ResponseEntity<Game> gameInfo(@PathVariable("gameId") String gameId) throws FileNotFoundException {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @GetMapping("/get-turn/{gameId}")
    public ResponseEntity<String> getCurrentTurn(@PathVariable("gameId") String gameId) throws FileNotFoundException {
        return ResponseEntity.ok("You are currently in turn " + gameService.getCurrentTurn(gameId) + " of 20");
    }

    @GetMapping("/get-actions-num/{gameId}")
    public ResponseEntity<Integer> getActionsRemaining(@PathVariable("gameId") String gameId)
            throws FileNotFoundException {
        return ResponseEntity.ok(gameService.getNumberOfRemainingActions(gameId));
    }

    //Might need to change the structure of this so that it mimics the post request
    @PostMapping("/advance-turn/{gameId}")
    public ResponseEntity<String> advanceTurn(@PathVariable("gameId") String gameId)  {
        try {
            if (gameService.checkGameIsCompleted(gameId)) {
                return ResponseEntity
                        .ok("CONGRATULATIONS!!!: Your company has successfully reached IPO status. You beat the game!");
            } else if (gameService.checkGameIsOver(gameId)) {
                return ResponseEntity.ok("GAME OVER! You failed to reach IPO status!");
            }

            String resultMessage = gameService.triggerRandomEvent(gameId);

            gameService.advanceTurn(gameId);
            return ResponseEntity.ok("You have advanced to the next turn\n " + resultMessage);
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-game")
    public ResponseEntity<String> deleteGame(@RequestParam String gameId) throws FileNotFoundException {
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

        return ResponseEntity
                .ok("Your FinTech Company has all it needs for IPO status - Don't worry your secret's safe with us ;)");
    }

}
