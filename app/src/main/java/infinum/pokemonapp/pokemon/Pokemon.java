package infinum.pokemonapp.pokemon;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.net.URI;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

@JsonApi(type = "pokemons")
public class Pokemon extends Resource{


    @Json(name = "image-url")
    private String imageUri;

    @Json(name = "name")
    private String name;

    @Json(name = "description")
    private String description;

    @Json(name = "height")
    private double height;

    @Json(name = "weight")
    private double weight;


    private String category;
    private String abilities;

    public Pokemon(){}

    public Pokemon(String imageUri, String name, String description, double height, double weight, String category, String abilities) {
        this.imageUri = imageUri;
        this.name = name;
        this.description = description;
        this.height = height;
        this.weight = weight;
        this.category = category;
        this.abilities = abilities;
    }

    protected Pokemon(Parcel in) {
        imageUri = in.readString();
        name = in.readString();
        description = in.readString();
        height = in.readDouble();
        weight = in.readDouble();
        category = in.readString();
        abilities = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getImage() {
        return imageUri;
    }

    public void setImage(String imageUri) {
        this.imageUri = imageUri;
    }
}
