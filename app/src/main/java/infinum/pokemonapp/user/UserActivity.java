package infinum.pokemonapp.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import infinum.pokemonapp.R;
import infinum.pokemonapp.pokemon.PokemonActivity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container, new UserLoginFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==  android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void showMessage(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public void startPokemonActivity(){
        Intent intent = new Intent(this, PokemonActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void saveUser(User user) {

        CurrentUser currentUser = CurrentUser.getInstance();
        currentUser.setAuthorizationToken(user.getAuthToken());
        currentUser.setEmail(user.getEmail());
        currentUser.setUsername(user.getUsername());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.current_user_authtoken),currentUser.getAuthorizationToken());
        editor.putString(getString(R.string.username),currentUser.getUsername());
        editor.putString(getString(R.string.current_user_email),currentUser.getEmail());
        editor.apply();

    }
}
