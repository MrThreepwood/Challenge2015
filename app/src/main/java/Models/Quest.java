package Models;

import java.io.Serializable;

/**
 * Created by joshuaswoyer on 10/22/15.
 */
public class Quest implements Serializable {
    String id;
    String name;
    String description;
    String image;
    QuestGiverBrief giver;

    public String getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {

        return description;
    }

    public String getImage() {

        return image;
    }

    public QuestGiverBrief getGiver() {

        return giver;
    }

    public Quest Quests(QuestGiverBrief giver, String image, String description, String name, String id) {
        this.giver = giver;
        this.image = image;
        this.description = description;
        this.name = name;
        this.id = id;
        return this;
    }
}
