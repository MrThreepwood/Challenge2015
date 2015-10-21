package API;

import java.util.List;

import Models.KingdomModel;
import Models.MessageModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by joshuaswoyer on 10/20/15.
 */
public interface ChallengeAPI {
    @FormUrlEncoded
    @POST("/subscribe")
    public void logIn(@Field("email") String email, Callback<MessageModel> message);
    @GET("/kingdoms")
    public void getKingdoms(Callback<List<KingdomModel>> responseCallback);
}