package org.example;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CatTeam {

    Map<String, Cat> catTeamBand = new HashMap<>();

    public void addCat(String catName, Cat cat) {
        this.catTeamBand.put(catName, cat);
    }

}
