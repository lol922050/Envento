package svastek.marriage;



import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


/**
 * Created by Tatson on 01-12-2015.
 */
public interface Senddata {
    @FormUrlEncoded
    @POST("/sevento/php/events.php")
    public void uploadprofile
            (@Field("mob") String user_mob, @Field("events") String event, @Field("cat") String v_cat, @Field("name") String v_name, @Field("loc") String v_loc,Callback<Response>callback
            );


}
