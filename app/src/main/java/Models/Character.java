package Models;

import java.io.Serializable;

/**
 * Created by joshuaswoyer on 10/29/15.
 */
public class Character implements Serializable {
    String id;
    String name;
    String image;
    String profession;
    String bio;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getProfession() {
        return profession;
    }

    public String getBio() {
        return bio;
    }

    public Character (String id, String name, String image, String profession, String bio) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.profession = profession;
        this.bio = bio;
    }
}
