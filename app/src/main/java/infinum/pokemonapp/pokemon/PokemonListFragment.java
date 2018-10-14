package infinum.pokemonapp.pokemon;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.Delete;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import infinum.pokemonapp.ApiCall;
import infinum.pokemonapp.R;
import infinum.pokemonapp.database.PokemonItem;
import infinum.pokemonapp.database.SQLitePokemon;
import infinum.pokemonapp.user.CurrentUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PokemonListFragment extends Fragment {


    @BindView(R.id.rec_view)
    RecyclerView recView;

    @BindView(R.id.empty_list_layout)
    RelativeLayout emptyListLayout;

    Unbinder unbinder;

    public static String POKEMON_LIST_FRAGMENT_TAG = "Pokemon";

    private Call<Pokemon[]> pokemonsCall;

    Pokemon[] pokemons;

    private boolean landscapeTablet;

    protected List<Pokemon> pokemonList = new ArrayList<Pokemon>();

    private SQLitePokemon pokemonDatabase;





    public PokemonListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        landscapeTablet = ((PokemonActivity) getActivity()).isLandscapeTablet();


        return view;
    }

    private void setUpRecyclerView() {
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        recView.setAdapter(new PokemonAdapter(pokemonList,getActivity(),new PokemonAdapter.detailsFragmentOpen() {
            @Override
            public void openDetailsFragment(int position) {
                PokemonDetailsFragment detailsFragment = new PokemonDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(PokemonDetailsFragment.POKEMON, position);
                detailsFragment.setArguments(bundle);



                if(!landscapeTablet) {
                    getFragmentManager().beginTransaction().add(R.id.fragment_container, detailsFragment).addToBackStack(PokemonDetailsFragment.POKEMON_DETAILS_FRAGMENT_TAG).commit();
                }else{
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, detailsFragment).commit();
                }
            }
        }));
        if(pokemonList.isEmpty()){
            recView.setVisibility(View.INVISIBLE);
            emptyListLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        pokemonDatabase = new SQLitePokemon();
        List<PokemonItem> items = pokemonDatabase.getPokemons();
        Log.d("START : count : ",String.valueOf(items.size()));
        loadPokemons();
        super.onCreate(savedInstanceState);
    }


    public void loadPokemons() {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading pokemons...");
        dialog.show();

        pokemonsCall = getPokemonsCall();

        pokemonsCall.enqueue(new Callback<Pokemon[]>() {
            @Override
            public void onResponse(Call<Pokemon[]> call, Response<Pokemon[]> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {
                   pokemons = response.body();

                    for(int i =0, n= pokemons.length; i<n ;i++) {
                        Log.d("Adding to list", String.valueOf(i));
                        Pokemon pokemon = pokemons[i];
                        if(pokemon.getImage()!=null) {
                            pokemon.setImage(ApiCall.apiEndpoint + pokemon.getImage());
                        }
                        pokemonList.add(pokemon);
                    }

                    addPokemonsToDatabase();

                    setUpRecyclerView();
                    PokemonList.getInstance().addPokemons(pokemonList);

                } else {
                    Toast.makeText(getActivity(), "Couldn't load pokemons.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pokemon[]> call, Throwable t) {

                dialog.dismiss();
                loadFromDatabase();
            }
        });


    }

    private void loadFromDatabase(){
        pokemonDatabase = new SQLitePokemon();
        List<PokemonItem> items = pokemonDatabase.getPokemons();
        for(int i =0, n= items.size();i<n;i++){
           Pokemon pokemon = items.get(i).toPokemon();
            pokemonList.add(pokemon);
        }
        PokemonList.getInstance().addPokemons(pokemonList);
        setUpRecyclerView();
    }

    private void addPokemonsToDatabase() {

       Thread thread = new Thread((new Runnable() {
            @Override
            public void run() {

                Delete.table(PokemonItem.class);
                pokemonDatabase = new SQLitePokemon();

                for(int i=0,n=pokemonList.size();i<n;i++) {
                    Log.d("pokemons in database",String.valueOf(pokemonDatabase.getPokemons().size()));
                    Pokemon pokemon = pokemonList.get(i);
                    String imageUri = null;
                    FileOutputStream fos = null;

                    if (pokemon.getImage() != null) {

                        try {
                            Bitmap imageBitmap = null;
                            imageBitmap = Glide.
                                    with(getActivity()).
                                    load(pokemon.getImage()).
                                    asBitmap().
                                    into(100, 100).
                                    get();
                            while (imageBitmap == null) {
                                wait(10);
                            }

                            String filename = pokemon.getName() + pokemon.getId() + "image.png";
                            File file = new File(getContext().getFilesDir(), filename);
                            imageUri = file.toString();

                            fos = new FileOutputStream(file);
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (fos != null) {
                                    fos.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    PokemonItem item = new PokemonItem();
                    item.setName(pokemon.getName());
                    item.setDescription(pokemon.getDescription());
                    item.setHeight(pokemon.getHeight());
                    item.setWeight(pokemon.getWeight());
                    item.setCategory(pokemon.getCategory());
                    item.setAbilities(pokemon.getAbilities());
                    item.setImageUri(imageUri);
                    item.save();
                }
            }
        }));
        thread.start();


    }

    private Call<Pokemon[]> getPokemonsCall() {

        ApiCall apiCall = ApiCall.getInstance();
        return apiCall.getApiService().getAllPokemons(CurrentUser.getInstance().getAuthorizationString());
    }

    public void updatePokemonList(){

        recView.getAdapter().notifyDataSetChanged();
        if(!pokemonList.isEmpty()) {
            recView.setVisibility(View.VISIBLE);
            emptyListLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        if(pokemonsCall!=null){
            pokemonsCall.cancel();
        }
        super.onDestroyView();
        unbinder.unbind();
    }


}
