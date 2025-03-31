package EpicQuestsRPG.classes;

import EpicQuestsRPG.util.DataBase;

public class Archer {

    private DataBase dataBase;

    public Archer(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void Archer(String player_name, String class_name) {


        dataBase.UpdateClass(class_name, player_name);


    }

}
