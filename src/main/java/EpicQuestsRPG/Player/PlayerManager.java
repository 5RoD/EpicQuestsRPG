package EpicQuestsRPG.Player;

import EpicQuestsRPG.util.CC;

public class PlayerManager {



    private boolean player_done_before;
    private String uuid;
    private String player_name;
    private String player_class;
    private String player_current_quest;



    public PlayerManager(boolean player_done_before, String uuid, String player_class, String player_current_quest, String player_name) {
        this.player_done_before = player_done_before;
        this.player_name = player_name;
        this.uuid = uuid;
        this.player_class = player_class;
        this.player_current_quest = player_current_quest;


    }

//translates the playermanager objects into readable text
    public String toString() {
        return CC.translate(
                "&6player_done_before: &e" + player_done_before + "\n" +
                        "&6uuid: &e" + uuid + "\n" +
                        "&6player_name: &e" + player_name + "\n" +
                        "&6player_class: &e" + player_class + "\n" +
                        "&6player_current_quest: &e" + player_current_quest
        );
    }

    public boolean isPlayer_done_before() {
        return player_done_before;
    }


    public String player_search(String player_name) {
        return player_name;
    }

    public void setPlayer_done_before(boolean player_done_before) {
        this.player_done_before = player_done_before;
    }



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_Name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_class() {
        return player_class;
    }

    public void setPlayer_class(String player_class) {
        this.player_class = player_class;
    }

    public String getPlayer_current_quest() {
        return player_current_quest;
    }

    public void setPlayer_current_quest(String player_current_quest) {
        this.player_current_quest = player_current_quest;
    }
}