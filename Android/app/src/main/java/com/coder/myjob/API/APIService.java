package com.coder.myjob.API;

import android.content.Context;

import com.coder.myjob.BuildConfig;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.LoginModel;
import com.coder.myjob.response.GetJob;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @GET("api/seeker/job")
    Call<GetJob> dapatseekerjob(@Query("user_id") String idUser,
                                    @Query("token") String token);

    @GET("api/freelancer/job")
    Call<GetJob> dapatfreelancerjob(@Query("user_id") String idUser,
                                @Query("token") String token);

    //API untuk login
    @FormUrlEncoded
    @POST("api/login")
    Call<LoginModel> loginUser(@Field("email") String email,
                               @Field("password") String password);

    class Factory{
        public static APIService create(Context mContext){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
//            builder.addInterceptor(new NetworkConnectionInterceptor(mContext));
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            if(BuildConfig.DEBUG){
//                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            }else {
//                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
//            }

//            OkHttpClient client = builder.addInterceptor(logging).build();
            OkHttpClient client = builder.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(APIService.class);
        }
    }
}
