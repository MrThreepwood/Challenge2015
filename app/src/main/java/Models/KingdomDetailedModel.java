package Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by joshuaswoyer on 10/22/15.
 */
public class KingdomDetailedModel implements Serializable{
    String id;
    String name;
    String image;
    String climate;
    String population;
    List<Quest> quests;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {

        return image;
    }

    public String getClimate() {

        return climate;
    }

    public String getPopulation() {

        return population;
    }

    public List<Quest> getQuests() {

        return quests;
    }

    public KingdomDetailedModel KingdomDetailedModel (String id, String name, String image, String climate, String population, List<Quest> quests) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.climate = climate;
        this.population = population;
        this.quests = quests;
        return this;
    }
}
