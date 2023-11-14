package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Game;

import java.util.List;

public class SortAlgo {
    public static List<Game> quickSort(List<Game> games, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(games, low, high);

            quickSort(games, low, pivotIndex - 1);
            quickSort(games, pivotIndex + 1, high);
        }
        return games;
    }

    private static int partition(List<Game> games, int low, int high) {
        Game pivot = games.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (games.get(j).getDateCreated().compareTo(pivot.getDateCreated()) >= 0) {
                i++;
                swap(games, i, j);
                //NB - you can compare the names of the finTech companies too!!!
            }
        }

        swap(games, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Game> games, int i, int j) {
        Game temp = games.get(i);
        games.set(i, games.get(j));
        games.set(j, temp);
    }
}
