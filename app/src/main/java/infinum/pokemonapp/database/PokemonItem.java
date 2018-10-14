package infinum.pokemonapp.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;

import infinum.pokemonapp.pokemon.Pokemon;

/**
 * Created by bivankovic on 1.8.2017..
 */

@Table(database = AppDatabase.class)
public class PokemonItem extends BaseModel{

    @PrimaryKey(autoincrement = true)
    private long id;

    @Column
    private String name;

    @Column
    private String imageUri;

    @Column
    private String description;

    @Column
    private double height;

    @Column
    private double weight;

    @Column
    private String category;

    @Column
    private String abilities;

    public PokemonItem(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getWeight(){
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pokemon toPokemon(){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(this.name);
        pokemon.setDescription(this.description);
        pokemon.setHeight(this.height);
        pokemon.setWeight(this.weight);
        pokemon.setCategory(this.category);
        pokemon.setAbilities(this.abilities);
        pokemon.setImage(this.imageUri);
        return pokemon;
    }

}
