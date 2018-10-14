package infinum.pokemonapp.pokemon;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import infinum.pokemonapp.ApiCall;
import infinum.pokemonapp.R;
import infinum.pokemonapp.database.PokemonItem;
import infinum.pokemonapp.database.SQLitePokemon;

public class PokemonDetailsFragment extends Fragment{

    private boolean landscapeTablet;
    public static String POKEMON_DETAILS_FRAGMENT_TAG = "Details";
    protected static String FRAGMENT_TITLE = "Details";
    protected static String POKEMON = "Pokemon";

    @BindView(R.id.details_picture)
    ImageView detailsPicture;

    @BindView(R.id.details_name)
    TextView detailsName;

    @BindView(R.id.details_description)
    TextView detailsDescription;

    @BindView(R.id.pokemon_height)
    TextView pokemonHeight;

    @BindView(R.id.pokemon_weight)
    TextView pokemonWeight;

    @BindView(R.id.pokemon_category)
    TextView pokemonCategory;

    @BindView(R.id.pokemon_abilities)
    TextView pokemonAbilities;


    Unbinder unbinder;


    public PokemonDetailsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_details, container, false);
        Bundle bundle = getArguments();
        unbinder = ButterKnife.bind(this, view);

        getActivity().invalidateOptionsMenu();


        int position = bundle.getInt(POKEMON);
        SQLitePokemon pokemonDatabase = new SQLitePokemon();
        PokemonItem item = pokemonDatabase.getPokemons().get(position);
        Pokemon pok = item.toPokemon();

        if (pok.getImage()!=null){
            if(!pok.getImage().equals("")) {
                detailsPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
                detailsPicture.setImageURI(Uri.parse(pok.getImage()));
            }
        }
        detailsName.setText(pok.getName());
        detailsDescription.setText(pok.getDescription());
        if (pok.getHeight() != 0.0) {
            pokemonHeight.setText(String.valueOf(pok.getHeight() + " cm"));
        }
        if (pok.getWeight() != 0.0) {
            pokemonWeight.setText(String.valueOf(pok.getWeight() + " kg"));
        }
        pokemonCategory.setText(pok.getCategory());
        pokemonAbilities.setText(pok.getAbilities());

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
