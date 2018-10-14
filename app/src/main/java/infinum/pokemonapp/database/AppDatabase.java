package infinum.pokemonapp.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by bivankovic on 1.8.2017..
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "PokemonDatabase";

    public static final int VERSION = 1;
}
