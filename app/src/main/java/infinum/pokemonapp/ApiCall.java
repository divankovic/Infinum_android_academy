package infinum.pokemonapp;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;

import infinum.pokemonapp.pokemon.Pokemon;
import infinum.pokemonapp.pokemon.PokemonList;
import infinum.pokemonapp.user.JsonApiConverterFactory;
import infinum.pokemonapp.user.User;
import moe.banana.jsonapi2.ResourceAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by bivankovic on 30.7.2017..
 */

public class ApiCall {

    public final static  String apiEndpoint = "https://pokeapi.infinum.co";

    private ApiService apiService;

    private static final ApiCall ourInstance = new ApiCall();

    public static ApiCall getInstance() {
        return ourInstance;
    }

    private ApiCall() {
        JsonAdapter.Factory jsonApiAdapterFactory = ResourceAdapterFactory.builder()
                .add(User.class).add(Pokemon.class)
                .build();

        Moshi moshi = new Moshi.Builder()
                .add(jsonApiAdapterFactory)
                .build();

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("api_tag", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .addConverterFactory(JsonApiConverterFactory.create(moshi))
                .client(client).build();

        apiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
