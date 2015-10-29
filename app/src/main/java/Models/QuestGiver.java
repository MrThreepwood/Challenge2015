package Models;

import java.io.Serializable;

/**
 * Created by joshuaswoyer on 10/22/15.
 */
public class QuestGiver implements Serializable {
    String id;
    String name;
    String image;

    public String getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public String getImage() {

        return image;
    }
    public QuestGiver QuestGiver (String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
        return this;
    }
}
