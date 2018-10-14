package infinum.pokemonapp.pokemon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import infinum.pokemonapp.ApiCall;
import infinum.pokemonapp.R;
import infinum.pokemonapp.user.CurrentUser;
import infinum.pokemonapp.user.User;
import infinum.pokemonapp.user.UserActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {

    public final static  String apiEndpoint = "https://pokeapi.infinum.co";

    private Call<User> logOutCall;
    private String POKEMON_CHANGED = "POKEMON_CHANGED";
    private String SAVED_FRAGMENT = "SAVED_FRAGMENT";
    private boolean landscapeTablet = false;
    private boolean portraitTablet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        landscapeTablet = getResources().getBoolean(R.bool.LandscapeTablet);
        portraitTablet =  getResources().getBoolean(R.bool.PortraitTablet);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(landscapeTablet){

           fragmentManager.beginTransaction().replace(R.id.fragment_container_left,new PokemonListFragment()).commit();

            if(savedInstanceState==null){
              fragmentManager.beginTransaction().replace(R.id.fragment_container,new PokemonAddFragment()).commit();
            }else{

                Fragment savedFragment = fragmentManager.getFragment(savedInstanceState,SAVED_FRAGMENT);
                if(!(savedFragment instanceof PokemonListFragment)){
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, savedFragment);
                }else{
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,new PokemonAddFragment()).commit();
                }
            }

        }else if(portraitTablet){

            fragmentManager.beginTransaction().replace(R.id.fragment_container, new PokemonListFragment()).commit();
            if(savedInstanceState!=null){

                Fragment savedFragment = fragmentManager.getFragment(savedInstanceState, SAVED_FRAGMENT);
                fragmentManager.executePendingTransactions();
                if (savedFragment instanceof PokemonAddFragment) {
                    if (savedInstanceState.getBoolean(POKEMON_CHANGED)) {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, savedFragment).addToBackStack(((PokemonAddFragment) savedFragment).POKEMON_ADD_FRAGMENT_TAG).commit();
                    }
                }
                if (savedFragment instanceof PokemonDetailsFragment) {
                    fragmentManager.beginTransaction().add(R.id.fragment_container, savedFragment).addToBackStack(((PokemonDetailsFragment) savedFragment).POKEMON_DETAILS_FRAGMENT_TAG).commit();
                }
            }
            invalidateOptionsMenu();
        }else{
            if(savedInstanceState==null){
                fragmentManager.beginTransaction().add(R.id.fragment_container, new PokemonListFragment()).commit();
                invalidateOptionsMenu();
            }
        }


    }


    @Override
    public void onBackPressed() {
        if(landscapeTablet){
            super.onBackPressed();
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment instanceof PokemonAddFragment) {
            ((PokemonAddFragment)fragment).onBack();
        }
        else if (fragment instanceof  PokemonListFragment){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Log out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    logOut();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }else {
            super.onBackPressed();
            invalidateOptionsMenu();
        }
    }

    public void updatePokemonList(){
        if(!landscapeTablet) {
            super.onBackPressed();
        }
        invalidateOptionsMenu();
        Fragment fragment;
        if(!landscapeTablet) {
            fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        }else{
            fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_left);
        }
        if(fragment instanceof PokemonListFragment){
            ((PokemonListFragment) fragment).updatePokemonList();
        }
    }

    public void goBack(){
        super.onBackPressed();
        invalidateOptionsMenu();
    }

    private void logOut(){

        logOutCall = getLogOutUserCall(CurrentUser.getInstance().getAuthorizationString());

        logOutCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().apply();
                    Intent intent = new Intent(PokemonActivity.this, UserActivity.class);
                    startActivity(intent);
                    PokemonActivity.this.finish();
                }else{
                    Toast.makeText(PokemonActivity.this, "Logout unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(PokemonActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<User> getLogOutUserCall(String authorizationHeader) {

        ApiCall apiCall = ApiCall.getInstance();
        return apiCall.getApiService().logoutUser(authorizationHeader);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        if(landscapeTablet) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment != null) {
                if (!(currentFragment instanceof PokemonAddFragment)) {
                    menu.findItem(R.id.btn_add).setVisible(true);
                } else {
                    menu.findItem(R.id.btn_add).setVisible(false);
                }
            }
        }else{
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment != null) {
                if (currentFragment instanceof PokemonAddFragment) {
                    setTitle(PokemonAddFragment.POKEMON_ADD_FRAGMENT_TAG);
                    menu.findItem(R.id.btn_add).setVisible(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else if(currentFragment instanceof PokemonListFragment){
                    menu.findItem(R.id.btn_add).setVisible(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    setTitle(PokemonListFragment.POKEMON_LIST_FRAGMENT_TAG);
                }else{
                    menu.findItem(R.id.btn_add).setVisible(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    setTitle(PokemonDetailsFragment.POKEMON_DETAILS_FRAGMENT_TAG);
                }
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_add:
                ConnectivityManager cm =
                        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    Toast.makeText(this,"Can't add pokemons while offline",Toast.LENGTH_LONG).show();
                }else {
                    if (!landscapeTablet) {
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new PokemonAddFragment()).addToBackStack(PokemonAddFragment.POKEMON_ADD_FRAGMENT_TAG).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PokemonAddFragment()).commit();
                    }
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        FragmentManager manager = getSupportFragmentManager();
        if(landscapeTablet){
            Fragment currentFragment = manager.findFragmentById(R.id.fragment_container);
            if(currentFragment instanceof PokemonAddFragment){
                outState.putBoolean(POKEMON_CHANGED, ((PokemonAddFragment) currentFragment).pokemonChanged());
            }
            manager.beginTransaction().remove(currentFragment);
            manager.putFragment(outState,SAVED_FRAGMENT,currentFragment);

        }else if(portraitTablet){
            Fragment currentFragment = manager.findFragmentById(R.id.fragment_container);
            manager.beginTransaction().remove(currentFragment);
            manager.putFragment(outState,SAVED_FRAGMENT,currentFragment);

        }
        super.onSaveInstanceState(outState);
    }

    public boolean isLandscapeTablet(){
        return landscapeTablet;
    }
}
