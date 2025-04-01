package EpicQuestsRPG.classes;

import EpicQuestsRPG.util.DataBase;

public class Mage {

    private final DataBase dataBase;

    public Mage(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void mage(String player_name, String class_name) {


        dataBase.UpdateClass(class_name, player_name);


    }

    // Write here the logic for each class and then pass it into ChangeClass

}
