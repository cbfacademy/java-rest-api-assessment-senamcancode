package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Database.Database;
import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    //we need to account for if there is a json file already - we need to append the new game to the json file


    public void newGame() {
        Game game = new Game();
        Company company = game.getCompany();
        Database gameData = new Database();
        gameData.addGame(game);

        gameRepository.saveGameData(gameData);
    }

    //Is it ideal for the user to constantly have to put the gameId in?
    public void addEmployee(String gameId, int numberOfEmployees) throws InsufficientFundsException {
        Game game = GameRepository.retrieveGame(gameId);
        //this line is constantly repeated can I refactor?

        assert game != null;
        game.getCompany().addEmployee(numberOfEmployees);

        game.addToCurrentNumberOfActions();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void crowdFund(String gameId) throws InvalidActionException {
        Game game = GameRepository.retrieveGame(gameId);

        game.getCompany().crowdFund();

        gameRepository.updateGameDataById(gameId, game);

    }

    public void advanceTurn(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        game.advanceTurn();

        gameRepository.updateGameDataById(gameId, game);
    }
}



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

