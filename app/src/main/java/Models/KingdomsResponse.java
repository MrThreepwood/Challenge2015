package Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuaswoyer on 10/20/15.
 */
public class KingdomsResponse {
    public List<KingdomBriefModel> kingdoms;
    public KingdomsResponse() {
        kingdoms = new ArrayList<KingdomBriefModel>();
    }

    public static KingdomsResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        KingdomsResponse kingdomsResponse = gson.fromJson(response, KingdomsResponse.class);
        return kingdomsResponse;
    }
}
