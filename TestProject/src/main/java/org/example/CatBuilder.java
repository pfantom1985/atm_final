package org.example;

public class CatBuilder {

    public static CatTeam createCat() {
        CatTeam catTeam = new CatTeam();
        catTeam.addCat("Bonny", new Cat("Bonny", "Grey", 16));
        catTeam.addCat("Betty", new Cat("Betty", "grey", 15));
        catTeam.addCat("Tomas", new Cat("Tomas", "white", 6));
        return catTeam;
    }

}
