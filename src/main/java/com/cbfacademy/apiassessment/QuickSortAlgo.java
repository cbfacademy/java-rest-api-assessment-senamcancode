package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Game;

import java.util.List;

public class QuickSortAlgo {
    public static List<Game> quickSort(List<Game> games, int begin, int end) {
        if (begin < end) {
            int pivotIndex = partition(games, begin, end);

            quickSort(games, begin, pivotIndex - 1);
            quickSort(games, pivotIndex + 1, end);
        }
        return games;
    }

    private static int partition(List<Game> games, int begin, int end) {
        Game pivot = games.get(end);
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (games.get(j).getDateCreated().compareTo(pivot.getDateCreated()) >= 0) {
                i++;
                swap(games, i, j);
                //NB - you can compare the names of the finTech companies too!!!
            }
        }

        swap(games, i + 1, end);
        return i + 1;
    }

    private static void swap(List<Game> games, int i, int j) {
        Game temp = games.get(i);
        games.set(i, games.get(j));
        games.set(j, temp);
    }
}
