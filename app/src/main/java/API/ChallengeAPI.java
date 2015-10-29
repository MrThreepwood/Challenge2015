package API;

import java.util.List;

import Models.KingdomBriefModel;
import Models.KingdomDetailedModel;
import Models.MessageModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by joshuaswoyer on 10/20/15.
 */
public interface ChallengeAPI {
    @FormUrlEncoded
    @POST("/subscribe")
    public void logIn(@Field("email") String email, Callback<MessageModel> message);
    @GET("/kingdoms")
    public void getKingdoms(Callback<List<KingdomBriefModel>> responseCallback);
    @GET("/kingdoms/{id}")
    public void getKingdomDetails(@Path("id") String kingdomId,Callback<KingdomDetailedModel> responseCallback);
}
