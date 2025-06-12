package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class MainCat {

    private static final String DATAFILE = "dataFile.json";

    public static void main(String[] args) {

        CatTeam catTeam = CatBuilder.createCat();
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(DATAFILE), catTeam);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            CatTeam catTeamRead = mapper.readValue(new File(DATAFILE), CatTeam.class);
            System.out.println(ColorUtil.printGreen + catTeamRead.getCatTeamBand() + ColorUtil.Reset);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}