package infinum.pokemonapp.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import infinum.pokemonapp.ApiCall;
import infinum.pokemonapp.R;
import infinum.pokemonapp.ApiService;
import moe.banana.jsonapi2.ResourceAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static infinum.pokemonapp.user.ErrorResponse.getErrorResponse;


public class UserRegisterFragment extends Fragment {

    final String apiEndpoint = "https://pokeapi.infinum.co";

    Call<User> registerUserCall;

    @BindView(R.id.register_email_input)
    EditText registerEmailInput;

    @BindView(R.id.register_username_input)
    EditText registerUsernameInput;

    @BindView(R.id.register_password_input)
    EditText registerPasswordInput;

    @BindView(R.id.register_password_repeat_input)
    EditText registerPasswordRepeatInput;

    @BindView(R.id.sign_up_button)
    Button signUpButton;

    @BindView(R.id.register_toolbar)
    Toolbar registerToolbar;

    Unbinder unbinder;

    public UserRegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_register, container, false);

        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(registerToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.sign_up_button)
    public void signUp(){
        if(TextUtils.isEmpty(registerUsernameInput.getText()) || TextUtils.isEmpty(registerEmailInput.getText()) || TextUtils.isEmpty(registerPasswordInput.getText())
        || TextUtils.isEmpty(registerPasswordRepeatInput.getText())){
            UserActivity.showMessage("There must not be an empty field!", getContext());
            return;
        }
        if(!registerPasswordInput.getText().toString().equals(registerPasswordRepeatInput.getText().toString())){
            UserActivity.showMessage("Passwords do not match",getContext());
            return;
        }

        User user = new User(registerUsernameInput.getText().toString(), registerEmailInput.getText().toString(), registerPasswordInput.getText().toString(), registerPasswordRepeatInput.getText().toString());

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Registering user...");
        dialog.show();

        registerUserCall = getRegisterUserCall(user);

        registerUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                if(response.isSuccessful()){

                    ((UserActivity)getActivity()).saveUser(response.body());
                    ((UserActivity)getActivity()).startPokemonActivity();

                }else{

                    ErrorResponse errorResponse = getErrorResponse(response);
                    List<ErrorResponse.ApiError> apiErrors = errorResponse.getErrors();

                    StringBuilder showErrors  = new StringBuilder();
                    for(int i=0,n=apiErrors.size();i<n;i++){
                        if(i==n-1){
                            showErrors.append(apiErrors.get(i).getSource().getPointerString() + " " +  apiErrors.get(i).getDetail());
                        }else {
                            showErrors.append(apiErrors.get(i).getSource().getPointerString() + " "+ apiErrors.get(i).getDetail() + "\n");
                        }
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(showErrors.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private Call<User> getRegisterUserCall(User user) {

        ApiCall apiCall = ApiCall.getInstance();
        return apiCall.getApiService().createNewUser(user);

    }

    @Override
    public void onDestroy() {
        if (registerUserCall!= null) {
            registerUserCall.cancel();
        }
        super.onDestroy();
    }
}
