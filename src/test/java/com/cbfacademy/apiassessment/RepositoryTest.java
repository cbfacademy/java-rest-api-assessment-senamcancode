package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RepositoryTest {
    @Test
    @DisplayName("Testing the retrieve game data method")
    public void testRetrieveGame(){
        GameRepository repo = new GameRepository();
        Game game = new Game();
        Game returned = GameRepository.retrieveGame("8c3edf72-cb80-4a9e-8a10-aa2fc9411fca");

        assertThat(returned).hasSameClassAs(game);
        
    }
}
