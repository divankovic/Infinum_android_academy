package infinum.pokemonapp.database;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by bivankovic on 1.8.2017..
 */

public class SQLitePokemon {

    public List<PokemonItem> getPokemons(){return SQLite.select().from(PokemonItem.class).queryList();}

    public void addPokemon(PokemonItem pokemon){ pokemon.save();}

    public void removePokemon(PokemonItem pokemon){ pokemon.delete();}
}
