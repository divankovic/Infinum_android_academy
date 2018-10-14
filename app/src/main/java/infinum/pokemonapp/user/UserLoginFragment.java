package infinum.pokemonapp.user;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import infinum.pokemonapp.ApiCall;
import infinum.pokemonapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static infinum.pokemonapp.user.ErrorResponse.getErrorResponse;


public class UserLoginFragment extends Fragment implements Animation.AnimationListener {

    private final String PERFORM_ANIMATION = "perform_animation";
    private boolean passwordVisible = false;
    private boolean animation;
    Call<User> loginUserCall;

    @BindView(R.id.pokemon_logo_picture)
    ImageView pokemonText;

    @BindView(R.id.pokeball_picture)
    ImageView pokeball;

    @BindView(R.id.login_email_input)
    EditText userEmailInput;

    @BindView(R.id.login_password_input)
    EditText userPasswordInput;

    @BindView(R.id.visibility_icon)
    ImageView visibilityIcon;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.sign_up_window_button)
    Button signUpWindowButton;

    @BindView(R.id.email_password_layout)
    RelativeLayout loginLayout;

    Unbinder unbinder;

    private TranslateAnimation translateAnimation;
    private ScaleAnimation scaleAnimation;

    public UserLoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState==null){
            animation = true;
            setUpAnimations();
        }else{
            animation = false;
        }
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String authToken = sharedPref.getString(getString(R.string.current_user_authtoken), "");
        if (!authToken.equals("")) {
            String username = sharedPref.getString(getString(R.string.username), "");
            String email = sharedPref.getString(getString(R.string.current_user_email), "");
            CurrentUser currentUser = CurrentUser.getInstance();
            currentUser.setUsername(username);
            currentUser.setEmail(email);
            currentUser.setAuthorizationToken(authToken);

        }
        super.onCreate(savedInstanceState);
    }

    private void setUpAnimations() {

        translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0.6f, Animation.RELATIVE_TO_PARENT, 0f);
        translateAnimation.setDuration(1000);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(this);

        scaleAnimation = new ScaleAnimation((float) 0.2, 1, (float) 0.2, 1, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setAnimationListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (animation) {
            startAnimation();
        }
        return view;
    }

    private void startAnimation() {
        pokeball.setVisibility(View.INVISIBLE);
        pokemonText.setVisibility(View.INVISIBLE);
        loginLayout.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        signUpWindowButton.setVisibility(View.INVISIBLE);

        pokemonText.setVisibility(View.VISIBLE);
        pokemonText.startAnimation(translateAnimation);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.visibility_icon)
    public void changePasswordVisibility() {
        if (!passwordVisible) {
            visibilityIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
            userPasswordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordVisible = true;
        } else {
            visibilityIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_on));
            userPasswordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordVisible = false;
        }
    }

    @OnClick(R.id.login_button)
    public void login() {

        if (TextUtils.isEmpty(userEmailInput.getText()) || TextUtils.isEmpty(userPasswordInput.getText())) {
            UserActivity.showMessage("Invalid username or password.", getContext());
            return;
        }

        User user = new User(userEmailInput.getText().toString(), userPasswordInput.getText().toString());

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Logging in ...");
        dialog.show();

        loginUserCall = getLoginUserCall(user);
        loginUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    ((UserActivity) getActivity()).saveUser(response.body());
                    ((UserActivity) getActivity()).startPokemonActivity();

                } else {

                    ErrorResponse errorResponse = getErrorResponse(response);
                    List<ErrorResponse.ApiError> apiErrors = errorResponse.getErrors();
                    StringBuilder showErrors = new StringBuilder();
                    for (int i = 0, n = apiErrors.size(); i < n; i++) {
                        if (i == n - 1) {
                            showErrors.append(apiErrors.get(i).getDetail());
                        } else {
                            showErrors.append(apiErrors.get(i).getDetail() + "\n");
                        }
                    }

                    Toast.makeText(getActivity(), showErrors.toString(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Call<User> getLoginUserCall(User user) {

        ApiCall apiCall = ApiCall.getInstance();
        return apiCall.getApiService().loginUser(user);

    }

    @OnClick(R.id.sign_up_window_button)
    public void openSignUp() {
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.user_fragment_container, new UserRegisterFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onDestroy() {
        if (loginUserCall != null) {
            loginUserCall.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation instanceof TranslateAnimation) {
            pokeball.setVisibility(View.VISIBLE);
            pokeball.startAnimation(scaleAnimation);
        } else {
            if (CurrentUser.getInstance().getAuthorizationToken() == null) {
                loginLayout.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                signUpWindowButton.setVisibility(View.VISIBLE   );
            }else{
                ((UserActivity) getActivity()).startPokemonActivity();
            }
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
