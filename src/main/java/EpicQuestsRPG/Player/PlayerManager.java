package EpicQuestsRPG.Player;

public class PlayerManager {



    private boolean player_done_before;
    private double money;
    private String uuid;
    private String player_class;
    private String player_current_class;


    public PlayerManager(boolean player_done_before, double money, String uuid, String player_class, String player_current_class) {
        this.player_done_before = player_done_before;
        this.money = money;
        this.uuid = uuid;
        this.player_class = player_class;
        this.player_current_class = player_current_class;
    }


    public boolean isPlayer_done_before() {
        return player_done_before;
    }

    public void setPlayer_done_before(boolean player_done_before) {
        this.player_done_before = player_done_before;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayer_class() {
        return player_class;
    }

    public void setPlayer_class(String player_class) {
        this.player_class = player_class;
    }

    public String getPlayer_current_class() {
        return player_current_class;
    }

    public void setPlayer_current_class(String player_current_class) {
        this.player_current_class = player_current_class;
    }
}