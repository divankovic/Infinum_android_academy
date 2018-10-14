package infinum.pokemonapp.pokemon;


import java.util.ArrayList;
import java.util.List;

public class PokemonList {

    private List<Pokemon> myPokemons;

    private static final PokemonList ourInstance = new PokemonList();

    public static PokemonList getInstance() {
        return ourInstance;
    }

    private PokemonList() {
        myPokemons = new ArrayList<>();
    }

    public List<Pokemon> getMyPokemons(){
        return myPokemons;
    }

    public void addPokemon(Pokemon pokemon){
        myPokemons.add(pokemon);
    }

    public void addPokemons(List<Pokemon> pokemons){
        myPokemons.addAll(pokemons);
    }

}
