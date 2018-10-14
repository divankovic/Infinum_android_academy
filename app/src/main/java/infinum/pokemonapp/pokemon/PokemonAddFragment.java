package infinum.pokemonapp.pokemon;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import infinum.pokemonapp.R;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;


public class PokemonAddFragment extends Fragment{

    private boolean landscapeTablet;

    protected Uri pictureUri = Uri.EMPTY;


    public static String POKEMON_ADD_FRAGMENT_TAG = "Add new Pokemon";

    protected static final String URI = "uri";
    protected static final int SET_POKEMON_PICTURE_REQUEST = 100;
    protected static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Call<Pokemon> pokemonAddCall;

    @BindView(R.id.pokemon_picture)
    ImageView pokemonPicture;

    @BindView(R.id.name_input)
    EditText nameInput;

    @BindView(R.id.height)
    EditText height;

    @BindView(R.id.weight)
    EditText weight;

    @BindView(R.id.category)
    EditText category;

    @BindView(R.id.abilities)
    EditText abilities;

    @BindView(R.id.description)
    EditText description;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;


    Unbinder unbinder;


    public PokemonAddFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        landscapeTablet = ((PokemonActivity) getActivity()).isLandscapeTablet();
        getActivity().invalidateOptionsMenu();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_add, container, false);
        unbinder = ButterKnife.bind(this, view);



        getActivity().invalidateOptionsMenu();

        if (savedInstanceState != null) {
            pictureUri = savedInstanceState.getParcelable(URI);
            if (pictureUri != Uri.EMPTY) {
                pokemonPicture.setImageURI(pictureUri);
                pokemonPicture.setBackgroundColor(Color.WHITE);
            }
        }
        return view;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (pictureUri != Uri.EMPTY) {
            outState.putParcelable(URI, pictureUri);
        }
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.btn_save)
    public void save() {
        if (TextUtils.isEmpty(nameInput.getText())) {
            showMessage("Name must not be empty");
        } else {

            Double heightDb, weightDb;
            heightDb = getDouble(height);
            weightDb = getDouble(weight);

            if (pictureUri==null){
                pictureUri = Uri.EMPTY;
            }
           final Pokemon pokemon = new Pokemon(pictureUri.toString(), nameInput.getText().toString(), description.getText().toString(), heightDb, weightDb,
                    category.getText().toString(), abilities.getText().toString());
            PokemonList.getInstance().addPokemon(pokemon);
            if(landscapeTablet){
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new PokemonAddFragment()).commit();
                ((PokemonActivity)getActivity()).updatePokemonList();
            }else {
                ((PokemonActivity)getActivity()).updatePokemonList();

            }

            /*File file = new File(pictureUri.getPath());
            RequestBody image =
                    RequestBody.create(
                            MediaType.parse(getActivity().getContentResolver().getType(pictureUri)),
                            file
                    );
            pokemonAddCall = ApiCall.getInstance().getApiService().createPokemon(CurrentUser.getInstance().getAuthorizationString(),
                    pokemon.getName(),pokemon.getHeight(),pokemon.getWeight(),pokemon.getDescription(),image);

            pokemonAddCall.enqueue(new Callback<Pokemon>() {
                                       @Override
                                       public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                           if(response.isSuccessful()){
                                               if(landscapeTablet){
                                                   getFragmentManager().beginTransaction().replace(R.id.fragment_container, new PokemonAddFragment()).commit();
                                                   ((PokemonActivity)getActivity()).updatePokemonList();
                                               }else {
                                                   ((PokemonActivity)getActivity()).updatePokemonList();

                                               }
                                           }
                                       }

                                       @Override
                                       public void onFailure(Call<Pokemon> call, Throwable t) {
                                           Toast.makeText(getContext(),"failed",Toast.LENGTH_LONG);
                                       }
                                   }
            );*/

        }
    }

    @OnClick(R.id.pokemon_picture)
    public void setPokemonPicture() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showMessageRationale("You can set a photo of your pokemon by accessing phone's photos.");
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            selectPicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPicture();
        }
    }

    public void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, SET_POKEMON_PICTURE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_POKEMON_PICTURE_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pictureUri = imageUri;
            pokemonPicture.setImageURI(imageUri);
            pokemonPicture.setBackgroundColor(Color.WHITE);

        }
    }

    public  void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public void showMessageRationale(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);

            }
        }).create().show();
    }

    public Double getDouble(EditText value) {
        double result;
        try {
            result = Double.parseDouble(value.getText().toString());
        } catch (Exception ex) {
            result = 0.0;
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onBack() {
        if (!TextUtils.isEmpty(nameInput.getText())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Would you like to save the changes?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    btnSave.callOnClick();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ((PokemonActivity)getActivity()).goBack();

                }
            }).create().show();

        }else {
            ((PokemonActivity)getActivity()).goBack();
        }
    }

    public boolean pokemonChanged(){
        if(!TextUtils.isEmpty(nameInput.getText()) || !TextUtils.isEmpty(description.getText()) || !TextUtils.isEmpty(height.getText()) || !TextUtils.isEmpty(weight.getText()) ||
                !TextUtils.isEmpty(category.getText()) || !TextUtils.isEmpty(abilities.getText()) || !pictureUri.equals(Uri.EMPTY)){
            return true;
        }
        else{
            return false;
        }
    }


}
