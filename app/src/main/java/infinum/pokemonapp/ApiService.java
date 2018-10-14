package infinum.pokemonapp;


import java.util.List;

import infinum.pokemonapp.pokemon.Pokemon;
import infinum.pokemonapp.user.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @POST("/api/v1/users/login")
    Call<User> loginUser(@Body User user);

    @POST("/api/v1/users/")
    Call<User> createNewUser(@Body User user);

    @DELETE("/api/v1/users/logout")
    Call<User> logoutUser(@Header("Authorization") String authorizationHeader);

    @GET("/api/v1/pokemons")
    Call<Pokemon[]> getAllPokemons(@Header("Authorization") String authorizationHeader);

    @Multipart
    @POST("/api/v1/pokemons")
    Call<Pokemon> createPokemon(@Header("Authorization") String authorizationHeader,
                                @Part(value = "data[attributes][name]", encoding = "text/plain") String name,
                                @Part("data[attributes][height]") double height,
                                @Part("data[attributes][weight]") double weight,
                                @Part(value = "data[attributes][description]", encoding = "text/plain") String description,
                                @Part("data[attributes][image]\"; filename=\"pokemon.jpg") RequestBody image);
}
