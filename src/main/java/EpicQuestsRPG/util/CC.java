package EpicQuestsRPG.util;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

public class CC {

    public static String translate(String text) {
        return IridiumColorAPI.process(text);
    }
    public static String rainbow(String text) {
        return IridiumColorAPI.rainbow(text, 1);
    }

}

