package com.cbfacademy.apiassessment.Controller;

import com.cbfacademy.apiassessment.Service.GameService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;


@RestController
@RequestMapping("/")

@OpenAPIDefinition(info = @Info(
        title = "FinTech Tycoon - REST API Game",
        description = "Welcome to FinTech Tycoon! This is a dynamic resource management game where players strive to achieve IPO status through strategic decision-making.Our REST API facilitates this gaming experience, offering specific endpoints to trigger in-game actions. Users can create, read, update, and delete game elements using the standard HTTP methods: POST, GET, PUT, and DELETE. For a comprehensive guide on rules and gameplay, please refer to the README within the com.cbfacademy.appiassessment package.<br /> Get ready to navigate the world of finance and build your financial empire!",
        version = "1.0.0"))

@Tag(name= "Game Actions")
public class GameController {

    @Autowired
    private GameService gameService;

    //need to re-do descriptions for the invalid action error!!!

    @Operation(
            description = "Creates an instance of the game class and an instance of the database class, then adds the game object created to the 'games' ArrayList in the database object, then writes the data from the arrayList to the game-data.json file",
            summary = "Creates a game and writes the data to the game-data.json file",
            responses = {
                    @ApiResponse(
                            description = "New game successfully started and written to game-data.json file",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "Error starting the game: Error writing to file",
                            responseCode = "404",
                            content = @Content(mediaType = "text/plain")
                    )
    }
    )
    @PostMapping("/start")
    public ResponseEntity<Object> startNewGame() {
        try {
            gameService.newGame();

            return ResponseEntity.ok("New game started and data written to file");
        }catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error starting the game: " + e.getMessage());
        }

    }

    @Operation(
            description = "Adds a new game object to the 'games' ArrayList of the database object then writes the data to the game-data.json file",
            summary = "Creates a new game and appends it to the data to a json file",
            responses = {
                    @ApiResponse(
                            description = "New game successfully added to file",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File Not Found: Unable to add a new game.",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/add-game")
    public ResponseEntity<Object> addNewGame() {
        try {
            gameService.appendNewGame();

            return ResponseEntity.ok("New game added to file");
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("File Not Found: Unable to add a new game.");
        }
    }


    @Operation(
            description = "Uses the 'gameId' request parameter to update the name of the FinTech company to inputted in the request parameter then writes the data to the game-data.json file",
            summary = "Updates the name of the FinTech company",
            responses = {
                    @ApiResponse(
                            description = "FinTech company name successfully updated to inputted company name",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File Not Found: Unable to change company name.",
                            responseCode = "404",
                            content = @Content(mediaType = "text/plain")
                    )
            }
    )
    @PutMapping("/company-name")
    public ResponseEntity<String> setCompanyName(@RequestParam String companyName, String gameId) {
        try {
            gameService.nameCompany(gameId, companyName);

            String newCompanyName = gameService.getCompany(gameId).getCompanyName();
            return ResponseEntity.ok("Your FinTech Company name was successfully changed to " + newCompanyName);
        }catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("File Not Found: Unable to change company name.");
        }
    }


    @Operation(
            //change variable potentially - in the description
            description = "Uses 'gameId' and 'employeeNum' request parameters to add a given number of employees to the company, provided there is sufficient funds, then writes the data to the game-data.json file. There are a number of additional 200 responses that can be shown depending on the game state. <br /> (1) If there are insufficient funds the following 200 OK response will be shown: 0 employees added. You have {numberOfEmployees} employee(s), where {numberOfEmployees} is the numberOfEmployees variable retrieved from the company object. <br /> (2) If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions. <br /> (3) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions. <br /> (4) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more action",
            summary = "Adds a specific number of employees to the company",
            responses = {
                    @ApiResponse(
                            description = "Employees successfully added if there are sufficient funds",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File not found: Unable to add employee(s)",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestParam int employeeNum, String gameId) {
        try {
            if(gameService.validActionCheck(gameId)) {

                if (gameService.checkGameIsCompleted(gameId)) {
                    return ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsOver(gameId)) {
                    return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                int initEmployees = gameService.getEmployees(gameId);

                //Invalid action - You can only make 3 actions per turn. Advance turn to get access to more actions

                String resultMessage = gameService.addEmployee(gameId, employeeNum);



                int newEmployees = gameService.getEmployees(gameId);
                int employeesAdded = newEmployees - initEmployees;

                //This code is here to prevent the actionCount being incremented if the user fails to successfully add the employees
                if(initEmployees != newEmployees){
                    gameService.actionCountIncrement(gameId);
                }

                return ResponseEntity.ok(employeesAdded + " employee(s) added" + resultMessage + " You have " + newEmployees + " employee(s)");
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to add employee(s)");
        }
    }



    @Operation(
            description = "Uses 'gameId' and 'employeeNum' request parameters to remove a specific number of employees (provided by 'employeeNum' parameter) to the company, provided there is a sufficient number of employee, then writes the data to the game-data.json file. <br />There are a number of  additional 200 responses that can be shown depending on the game state: <br />(1) If there is an insufficient number of employees to remove from the following 200 OK response is displayed: 0 employee(s) added removed and £0 saved. You cannot get rid of more employees than you already have. You have {newEmployees} employees and {numDepartments} departments, where {newEmployees} and {numDepartments} are retrieved from the company object <br />(2) If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions. <br />(3) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions. <br />(4) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Removes a specific number of employees from the company",
            responses = {
                    @ApiResponse(
                            description = "Employees successfully removed if there are a sufficient number of employees",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File not found: Unable to remove employee(s)",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/remove-employee")
    public ResponseEntity<String> removeEmployee(@RequestParam int numberOfEmployees, String gameId) throws FileNotFoundException {
        try {
            if(gameService.validActionCheck(gameId)){
                if (gameService.checkGameIsCompleted(gameId)) {
                    return ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsOver(gameId)) {
                    return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                double initRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));
                int initEmployees = gameService.getEmployees(gameId);

                String resultMessage = gameService.removeEmployee(gameId, numberOfEmployees);


                int newEmployees = gameService.getEmployees(gameId);

                int employeesRemoved = initEmployees - newEmployees;

                int numOfDepartments = gameService.getDepartments(gameId);

                double moneySaved = Double.parseDouble(gameService.getFormattedRevenue(gameId)) - initRevenue;

                //This code is here to prevent the actionCount being incremented if the user fails to successfully remove the employees
                if(initEmployees != newEmployees){
                    gameService.actionCountIncrement(gameId);
                }

                return ResponseEntity.ok(employeesRemoved + " employee(s) removed and £" + moneySaved + " saved" + resultMessage + "\n You have "
                        + newEmployees + " employees and " + numOfDepartments + " department(s)");
        } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to remove employee(s)");
        }
    }


    @Operation(
            description = "'Uses the 'gameId' request parameter to add revenue to the company, then writes the data to the game-data.json. There are a number of additional 200 responses that can be shown depending on the game state. <br /> (1) If the user has already crowd funded once per game turn: Invalid Action: You can only crowd fund once per turn <br /> (2) If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions. <br /> (3) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions. <br /> (4) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Adds 500000 to the revenue of the company",
            responses = {
                    @ApiResponse(
                            description = "Successful crowd fund",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File not found: Unable to crowd fund",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/crowd-fund")
    public ResponseEntity<String> crowdFund(@RequestParam String gameId) {
        try {
            if(gameService.validActionCheck(gameId)) {
                if (gameService.checkGameIsCompleted(gameId)) {
                    return ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsOver(gameId)) {
                    return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }
                double initRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));

                gameService.crowdFund(gameId);

                double formattedNewRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));

                //This code is here to prevent the actionCount being incremented if the user fails to successfully crowd fund
                if (initRevenue != formattedNewRevenue) {
                    gameService.actionCountIncrement(gameId);
                }
                return ResponseEntity.ok("Crowd fund was successful! You now have £" + formattedNewRevenue);
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to crowd fund.");
        }

    }


    //add to this!!
    @Operation(
            description = "Uses gameId and {action} request parameters to invest - which results in either loss or gain of revenue, then writes the data to the game-data.json file. '{action}' can be either 'sniper' or 'passive' which changes the potential maximum amount of the revenue the gained or lost. There are a number of additional 200 responses that can be shown depending on the game state. <br /> (1) If the user has already invested once game turn: Invalid Action: You can only invest once per turn. <br /> (2) If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions.<br /> (3) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions.<br /> (4) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Adds or minuses a random value from the revenue of the company",
            responses = {
                    @ApiResponse(
                            description = "Sniper investment successfully made or Passive investment successfully made",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File not found: Unable to make investment",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/invest/{action}")
    public ResponseEntity<String> invest(@PathVariable String action, @RequestParam String gameId)  {
        try {
            if(gameService.validActionCheck(gameId)) {
                if (gameService.checkGameIsOver(gameId)) {
                    return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsCompleted(gameId)) {
                    return ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }

                String resultMessage;

                if ("sniper".equals(action)) {
                    double initRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));

                    resultMessage = gameService.sniperInvest(gameId);

                    double newRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));

                    if(initRevenue != newRevenue){
                        gameService.actionCountIncrement(gameId);
                    }
                    return ResponseEntity.ok("Sniper investment: " + resultMessage);
                }

                if ("passive".equals(action)) {
                    double initRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));

                    resultMessage = gameService.passiveInvest(gameId);
                    double newRevenue = Double.parseDouble(gameService.getFormattedRevenue(gameId));

                    //This code is here to prevent the actionCount being incremented if the user fails to successfully invest
                    if(initRevenue != newRevenue){
                        gameService.actionCountIncrement(gameId);
                    }

                    return ResponseEntity.ok("Passive investment: " + resultMessage);
                }
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found: Unable to make investment");
        }

        return null;
    }

    //change these!
    @Operation(
            description = "Uses the 'gameId' request parameter to add a department to the company, provided the player has a sufficient number of employees, then writes the data to the game-data.json file. There are a number of additional 200 responses that can be shown depending on the game state. <br />(1) If the user has maxed out the product XP the following message can also be shown: 0 department(s) added. You do not have enough employees to make a department. <br />(2) If the user has an insufficient number of employees to make a department the following 200 OK response is shown: 0 department(s) added. You do not have enough employees to make a department. <br />(3) If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions. <br />(4) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions. <br /> (5) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Adds a department to the company",
            responses = {
                    @ApiResponse(
                            description = "Department successfully added provided there is a sufficient number of employees",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File not found: Unable to add department",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/department")
    public ResponseEntity<String> addDepartment(@RequestParam String gameId) {
        try {
            if(gameService.validActionCheck(gameId)) {
                if (gameService.checkGameIsOver(gameId)) {
                    return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsCompleted(gameId)) {
                    return ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }

                int initNumOfDepartments = gameService.getDepartments(gameId);
                String resultMessage = gameService.addDepartment(gameId);
                int newNumberOfDepartments = gameService.getDepartments(gameId);

                //This code is here to prevent the actionCount being incremented if the user fails to successfully add a department
                if(initNumOfDepartments != newNumberOfDepartments){
                    gameService.actionCountIncrement(gameId);
                }

                return ResponseEntity.ok(newNumberOfDepartments + " department(s) added" + resultMessage);
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found: Unable to add department");
        }
    }


    @Operation(
            description = "Uses the 'gameId' request parameter to add to 2 to the productXP of the company, provided the player has a sufficient funds, then writes the data to the game-data.json file. There are a number of additional 200 responses that can be shown depending on the game state: <br /> (1) if the user has maxed out the product XP the following message can also be shown: You have maxed out your product XP and so can no longer use the R&D method. You have a product XP of {productXP}, where {productXP} is retrieved from the company object.<br /> (2) If the game is completed the 200 OK response is: You completed the game, you cannot take any more action.<br />(3) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions.<br /> (4) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Adds a department to the company",
            responses = {
                    @ApiResponse(
                            description = " Research and development success, 2 XP added to the product",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File not found:  Unable to add to do research and development",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/research")
    public ResponseEntity<String> researchAndDev(@RequestParam String gameId) {
        try {
            if (gameService.validActionCheck(gameId)) {
                if (gameService.checkGameIsOver(gameId)) {
                    return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsCompleted(gameId)) {
                    return ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }
                int initProductXP = gameService.getProductXP(gameId);

                String resultMessage = gameService.researchAndDev(gameId);

                int newProductXP = gameService.getProductXP(gameId);

                //This code is here to prevent the actionCount being incremented if the user fails to successfully do research and development
                if (initProductXP != newProductXP) {
                    gameService.actionCountIncrement(gameId);
                }

                return ResponseEntity
                        .ok(resultMessage + "You have a product XP of "
                                + newProductXP);
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to add to do research and development");
        }
    }

    @Operation(
            description = "Uses the 'gameId' request parameter to add to 1000 the customer base of the company, provided the user has a sufficient funds, then writes the data to the game-data.json file.There are a number of additional 200 responses that can be shown depending on the game state: <br /> (1) If there is insufficient funds, the 200 OK response is: You have a customer base of Insufficient funds: You do not have enough funds to implement marketing. <br /> (2) If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions. <br /> (3) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions. <br /> (4) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Adds to 1000 to the customer base of the company",
            responses = {
                    @ApiResponse(
                            description = "Customer base successfully added to provided there is sufficient funds",
                            responseCode = "200",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            description = "File not found:  Unable to do marketing action",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/marketing")
    public ResponseEntity<String> marketing(@RequestParam String gameId) {
        try {
            if (gameService.validActionCheck(gameId)) {
                if (gameService.checkGameIsOver(gameId)) {
                    return ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsCompleted(gameId)) {
                    return ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }

                int initCustomerBase = gameService.getCustomerBase(gameId);
                String resultMessage = gameService.market(gameId);
                int newCustomerBase = gameService.getCustomerBase(gameId);

                //This code is here to prevent the actionCount from being incremented if the user fails to successfully do marketing
                if(initCustomerBase != newCustomerBase){
                    gameService.actionCountIncrement(gameId);
                }
                return ResponseEntity.ok(resultMessage + "You have a customer base of " + newCustomerBase);
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: unable to do marketing action.");
        }
    }



    @Operation(
            description = "Gets and displays all games in the game-data.json file in order of most recently created",
            summary = "Gets all games in the game-data.json file",
            responses = {

                    @ApiResponse(
                            description = "All games successfully fetched and transmitted to the message body",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "File not found: unable to get all games.",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/games")
    public ResponseEntity<?> getAllGames() {
        try {
            return ResponseEntity.ok(gameService.getAllGames());
        } catch(FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to get all games.");
        }

    }


    @Operation(
            description = "Uses the gameId path variable to get and display all information in a specific company object in the game-data.json file ",
            summary = "Gets all information for the specified company",
            responses = {
                    @ApiResponse(
                            description = "Company data successfully fetched and transmitted to the message body",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Company data not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/company/{gameId}")
    public ResponseEntity<?> companyInfo(@PathVariable("gameId") String gameId) {
        try {
            return ResponseEntity.ok(gameService.getCompany(gameId));
        }catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to get company");
        }
    }

    //not sure why but this does not throw an exception

    @Operation(
            description = "Uses the gameId path variable to get and display all information pertaining to the company in the game-data.json file ",
            summary = "Gets all information for the specified game",
            responses = {
                    @ApiResponse(
                            description = "Game data successfully fetched and transmitted to the message body",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Game data not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> gameInfo(@PathVariable("gameId") String gameId) {
        try {
            return ResponseEntity.ok(gameService.getGame(gameId));
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to get game");
        }
    }

    @Operation(
            description = "Uses the gameId path variable to get and display the current turn that the player is on out of the total amount of turns within the game ",
            summary = "Gets the current turn of the player for the specified game",
            responses = {
                    @ApiResponse(
                            description = "Current turn successfully fetched and transmitted to the message body",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Current turn data not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/turn/{gameId}")
    public ResponseEntity<String> getCurrentTurn(@PathVariable("gameId") String gameId) {
       try {
           return ResponseEntity.ok("You are currently in turn " + gameService.getCurrentTurn(gameId) + " of " + gameService.getMaxTurns(gameId));
       } catch (FileNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to get current turn");
       }
    }

    @Operation(
            description = "Uses the gameId path variable to get and display all information pertaining to the company in the game-data.json file ",
            summary = "Gets the number of actions the player has remaining in the current turn of a specified game",
            responses = {
                    @ApiResponse(
                            description = "Actions remaining turn successfully fetched and transmitted to the message body",
                            responseCode = "200",
                            content = @Content(mediaType = "plain/text")
                    ),
                    @ApiResponse(
                            description = "Actions remaining not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/actions/{gameId}")
    public ResponseEntity<?> getActionsRemaining(@PathVariable("gameId") String gameId) {
        try {
            return ResponseEntity.ok(gameService.getNumberOfRemainingActions(gameId));
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to get number of remaining actions");
        }
    }

    
    @Operation(
            description = "Uses the gameId request parameter to add to the currentTurn, then writes the data to game-data.json file. There are a number of additional 200 responses that can be shown depending on the game state: <br />(1) If the game is over the 200 OK response is: GAME OVER! You failed to reach IPO status!. you cannot take any more actions. <br />(2) If the game is completed the 200 OK response is: CONGRATULATIONS!!!: Your company has successfully reached IPO status. You beat the game! ",
            summary = "Advances the currentTurn variable of the specified game",
            responses = {
                    @ApiResponse(
                            description = "Successfully advanced turn",
                            responseCode = "200",
                            content = @Content(mediaType = "plain/text")
                    ),
                    @ApiResponse(
                            description = "File Not Found: Unable to advance turn ",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/advance-turn")
    public ResponseEntity<String> advanceTurn(@RequestParam String gameId)   {
        try {
            if (gameService.checkGameIsCompleted(gameId)) {
                return ResponseEntity
                        .ok("CONGRATULATIONS!!!: Your company has successfully reached IPO status. You beat the game!");
            } else if (gameService.checkGameIsOver(gameId)) {
                return ResponseEntity.ok("GAME OVER! You failed to reach IPO status!");
            }

            String resultMessage = gameService.triggerRandomEvent(gameId);

            gameService.advanceTurn(gameId);
            return ResponseEntity.ok("You have advanced to the next turn " + resultMessage);
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not found: Unable to advance turn");
        }
    }

    @Operation(
            description = "Uses the gameId request parameter to delete the game object from the gamesList in the database class, then writes the data to the game-data.json file ",
            summary = "Deletes the specified game",
            responses = {
                    @ApiResponse(
                            description = "Successful game deletion",
                            responseCode = "200",
                            content = @Content(mediaType = "plain/text")
                    ),
                    @ApiResponse(
                            description = "File not found: Unable to delete game",
                            responseCode = "404"
                    )
            }
    )
    @DeleteMapping("/game")
    public ResponseEntity<String> deleteGame(@RequestParam String gameId) throws FileNotFoundException {
        try {
            gameService.deleteGame(gameId);
            return ResponseEntity.ok("You successfully deleted the game");
        } catch(FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to delete game");
        }
    }

    @Operation(
            description = "Updates the revenue of the company to 9999999. There are a number of additional 200 responses that can be shown depending on the game state:<br />(1)  If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions. <br />(2) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions <br />(3) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Updates the revenue of the specified company to 9999999",
            responses = {
                    @ApiResponse(
                            description = "Successful update to revenue",
                            responseCode = "200",
                            content = @Content(mediaType = "plain/text")
                    ),
                    @ApiResponse(
                            description = "File not found: unable to use money cheat code",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/money") //the actionCountIncrement method is missing from the money method as this  method is a cheat code
    public ResponseEntity<String> moneyMoneyMoney(@RequestParam String gameId) throws FileNotFoundException {
        try {
            if (gameService.validActionCheck(gameId)) {
                if (gameService.checkGameIsOver(gameId)) {
                    ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsCompleted(gameId)) {
                    ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }
                gameService.moneyMoneyMoney(gameId);
                return ResponseEntity.ok("Money money money! Must be funny in a rich man's world! Here's £9999999 on us!");
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }
        } catch(FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: unable to use money cheat code");
        }
    }


    @Operation(
            description = "Uses the gameId request parameter to update the company parameters:revenue, departments, employees, customerBase and productXP to values required for IPO status (£5000000, 3, 30, 10000 and 30). There are a number of additional 200 responses that can be shown depending on the game state: <br /> (1) If the game is over the 200 OK response is: You failed to complete the game. you cannot take any more actions. <br /> (2) If the game is completed the 200 OK response is: You completed the game, you cannot take any more actions. <br /> (3) If the user tries to make an invalid action, the 200 OK response is: Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions",
            summary = "Updates revenue, departments, employees, customerBase and productXP variables of the company object to those required for IPO status",
            responses = {
                    @ApiResponse(
                            description = "Successful setting of company variables to achieve IPO status",
                            responseCode = "200",
                            content = @Content(mediaType = "plain/text")
                    ),
                    @ApiResponse(
                            description = "File not found: Unable to use motherlode cheat code",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/motherlode") //the actionCountIncrement method is missing from the motherlode method as this  method is a cheat code
    public ResponseEntity<String> motherLoad(@RequestParam String gameId) {
        try {
            if (gameService.validActionCheck(gameId)) {
                if (gameService.checkGameIsOver(gameId)) {
                    ResponseEntity.ok("You failed to complete the game, you cannot take any more actions");
                }

                if (gameService.checkGameIsCompleted(gameId)) {
                    ResponseEntity.ok("You completed the game, you cannot take any more actions");
                }
                gameService.motherLode(gameId);
                return ResponseEntity.ok("Your FinTech Company has all it needs for IPO status - Don't worry your secret's safe with us ;)");
            } else {
                return ResponseEntity.ok("Invalid action: You are only permitted 3 actions per turn. Advance turn to have access to more actions");
            }
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: Unable to use motherlode cheat code");
        }
    }

}
