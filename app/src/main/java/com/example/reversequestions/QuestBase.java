package com.example.reversequestions;

import java.util.ArrayList;

public class QuestBase {

    public static ArrayList<Quest> questList = new ArrayList<>();

    public QuestBase(){
        questList.add(new Quest("Jakiego kolru jest melko?", "Białego", "Czarnego", 1));
        questList.add(new Quest("Kim był Napoleon Bonaparte?", "Francuzem", "Niemcem", 1));
        questList.add(new Quest("Ile to jest 2 + 2?", "4", "5", 1));
        questList.add(new Quest("Jaki kształt ma globus?", "Kuli", "Trójkąta", 1));
        questList.add(new Quest("Ile kilometr ma metrów?", "1000", "100", 1));
        questList.add(new Quest("Co jest stolicą Polski?", "Warszawa", "Gdańsk", 1));
        questList.add(new Quest("Ile nóg ma kot?", "4", "2", 1));
        questList.add(new Quest("Jaki wzór chemiczny ma woda?", "H2O", "CO2", 1));
        questList.add(new Quest("Słońce to?", "Gwiazda", "Planeta", 1));
        questList.add(new Quest("Które z podanych jest drzewem?", "Dąb", "Audi", 1));
    }

}
