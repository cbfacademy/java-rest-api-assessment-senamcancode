package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyTest {

    @Test
    @DisplayName("Testing crowdFund method increases revenue by 100000 in Company ")
    public void testCrowdFund(){
        Game game = new Game();
        Company company = new Company();
        double initialRevenue = company.getRevenue();

        company.crowdFund();

        double newRevenue = company.getRevenue();
        assertEquals(initialRevenue + 100000, newRevenue);

    }

}
