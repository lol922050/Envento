package svastek.marriage;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Tatson on 20-01-2016.
 */
public interface Register_inter {
    @FormUrlEncoded
    @POST("/sevento/php/register.php")
    public void uploadprofile
            (@Field("name") String name, @Field("email") String email, @Field("phone") String phone, Callback<Response> callback
            );
}
