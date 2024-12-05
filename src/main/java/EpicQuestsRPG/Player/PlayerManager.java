package EpicQuestsRPG.Player;

public class PlayerManager {



    private boolean player_done_before;
    private double player_money;
    private String uuid;
    private String player_name;
    private String player_class;
    private String player_current_quest;


    public PlayerManager(boolean player_done_before, double player_money, String uuid, String player_class, String player_current_quest, String player_name) {
        this.player_done_before = player_done_before;
        this.player_money = player_money;
        this.uuid = uuid;
        this.player_name = player_name;
        this.player_class = player_class;
        this.player_current_quest = player_current_quest;
    }


    public boolean isPlayer_done_before() {
        return player_done_before;
    }

    public void setPlayer_done_before(boolean player_done_before) {
        this.player_done_before = player_done_before;
    }

    public double getMoney() {
        return player_money;
    }

    public void setMoney(double player_money) {
        this.player_money = player_money;
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