package uk.co.glapworth.boilerplate.data.remote;

import android.content.Context;

import com.google.android.gms.signin.internal.SignInRequest;
import com.google.android.gms.signin.internal.SignInResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import uk.co.glapworth.boilerplate.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.glapworth.boilerplate.data.model.BoilerPlate;
import uk.co.glapworth.boilerplate.data.model.Profile;

/**
 * Created by glapworth on 07/06/16.
 */
public interface BPService {

    String ENDPOINT = BuildConfig.RAILS_SERVER_ENDPOINT;

    //@POST("users/auth/google_oauth2")
    //Observable<SignInResponse> signIn(@Body SignInRequest signInRequest);

    @GET("users/auth/google_oauth2/callback")
    Observable<SignInResponse> signInGoogleOAuth(
            @Query("code") String code
            //@Query("redirect_uri") String redirect_uri
    );

    //@GET("/auth/google_oauth2/callback")
    //Observable<SignInResponse> signInWithGoolgleOAuth(@QueryMap Map<String, String> parameters);
    /******** Factory class that sets up a new BoilerPlate services *******/
    class Factory {

        public static BPService makeBPService(Context context) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                    : HttpLoggingInterceptor.Level.NONE);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new UnauthorisedInterceptor(context))
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BPService.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(BPService.class);
        }
    }

    class Util {
        // Build API authorization string from a given access token.
        public static String buildAuthorization(String accessToken) {
            return "Bearer " + accessToken;
        }
    }

    /******** Specific request and response models ********/
    class SignInRequest {
        public String code;
        //public String redirect_uri;

        public SignInRequest(String code) {
            this.code = code;
            //this.redirect_uri = "http://android-test.glapworth.co.uk:3000/users/auth/google_oauth2/callback";
        }
    }

    class SignInResponse {
        public String accessToken;
        public BoilerPlate boilerPlate;
    }



}
