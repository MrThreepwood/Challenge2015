package Models;

/**
 * Created by joshuaswoyer on 10/20/15.
 */
public class KingdomModel {
    String id;
    String name;
    String image;

    public String getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }
    public KingdomModel (String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
