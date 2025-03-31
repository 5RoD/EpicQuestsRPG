package EpicQuestsRPG.classes;

import EpicQuestsRPG.util.DataBase;

public class Mage {

    private DataBase dataBase;

    public Mage(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void Mage(String player_name, String class_name) {


        dataBase.UpdateClass(class_name, player_name);


    }

}
