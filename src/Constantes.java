import java.util.HashMap;

/**
 *
 * @author Louis
 */
public final class Constantes {

    public final static HashMap<String, String> presence = new HashMap<String, String>();
    static {
        presence.put("1", "Présent");
        presence.put("2", "Absent");
        presence.put("3", "Excusé");
    }
    
}