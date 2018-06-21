package mahjong.coup;

import mahjong.Tuile;

public abstract class Coup {

    public abstract String save();

    public static Coup loadSave(String texteSauvegarde) {
        Coup coup;
        if (texteSauvegarde.substring(0, 2).equals("CR")) {
            String values[] = texteSauvegarde.substring(2).split(",");
            int score = Integer.parseInt(values[2]);
            coup = new CoupRetirerTuile(new Tuile(values[0]), new Tuile(values[1]), score);
        }
        else
        {
            String value = texteSauvegarde.substring(2);
            coup = new CoupMelangerPlateau(Long.parseLong(value));
        }
        return coup;
    }
}
