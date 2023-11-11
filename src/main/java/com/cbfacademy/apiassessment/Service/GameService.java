package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Database.Database;
import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;


@Service
public class GameService {

    //should i make gameRepository static?
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


    public void addEmployee(String gameId, int numberOfEmployees) throws InsufficientFundsException {
        Game game = GameRepository.retrieveGame(gameId);
        //this line is constantly repeated can I refactor?

        assert game != null;
        game.getCompany().addEmployee(numberOfEmployees);

        game.getCompany().productivityBoost();

        game.addToCurrentNumberOfActions();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void removeEmployee(String gameId, int numberOfEmployees) throws InvalidActionException{
        Game game = gameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().removeEmployee(numberOfEmployees);

        game.getCompany().productivityBoost();

        game.addToCurrentNumberOfActions();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void crowdFund(String gameId) throws InvalidActionException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().crowdFund();

        gameRepository.updateGameDataById(gameId, game);

    }

    public void sniperInvest(String gameId) throws InvalidActionException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().sniperInvestment();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void passiveInvest(String gameId) throws InvalidActionException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().passiveInvestment();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void addDepartment(String gameId) throws InvalidActionException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().addDepartment();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void researchAndDev(String gameId) throws InvalidActionException{
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().researchAndDev();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void market(String gameId) throws InvalidActionException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().marketing();

        gameRepository.updateGameDataById(gameId, game);
    }


    public void advanceTurn(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        game.advanceTurn();

        gameRepository.updateGameDataById(gameId, game);
    }

    //getters
    public int getEmployees(String gameId){
        Game game = GameRepository.retrieveGame(gameId);

        return game.getCompany().getEmployees();
    }

    public int getDepartments(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        return game.getCompany().getDepartments();
    }
    //may have to put the formatted revenue in the game class and return it to here
    public String getFormattedRevenue(String gameId){
        Game game = GameRepository.retrieveGame(gameId);

        double revenue = game.getCompany().getRevenue();

        NumberFormat formatter = new DecimalFormat("#0.00");

        return formatter.format(revenue);

    }

    public int getProductXP(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        return game.getCompany().getProductXP();
    }

    public int getCustomerBase(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        return game.getCompany().getCustomerBase();
    }

    public Company getCompany(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        return game.getCompany();
    }

    public Game getGame(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        return game;
    }

    public int getCurrentTurn(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        return game.getCurrentTurn();
    }

    public int getNumberOfRemainingActions(String gameId){
        Game game = GameRepository.retrieveGame(gameId);
        return game.actionsRemaining();
    }
}



