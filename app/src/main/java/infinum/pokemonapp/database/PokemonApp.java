package infinum.pokemonapp.database;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by bivankovic on 1.8.2017..
 */

public class PokemonApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
