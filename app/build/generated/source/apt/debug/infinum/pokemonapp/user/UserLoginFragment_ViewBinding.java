// Generated code from Butter Knife. Do not modify!
package infinum.pokemonapp.user;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import infinum.pokemonapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserLoginFragment_ViewBinding implements Unbinder {
  private UserLoginFragment target;

  private View view2131427461;

  private View view2131427453;

  private View view2131427462;

  @UiThread
  public UserLoginFragment_ViewBinding(final UserLoginFragment target, View source) {
    this.target = target;

    View view;
    target.pokemonText = Utils.findRequiredViewAsType(source, R.id.pokemon_logo_picture, "field 'pokemonText'", ImageView.class);
    target.pokeball = Utils.findRequiredViewAsType(source, R.id.pokeball_picture, "field 'pokeball'", ImageView.class);
    target.userEmailInput = Utils.findRequiredViewAsType(source, R.id.login_email_input, "field 'userEmailInput'", EditText.class);
    target.userPasswordInput = Utils.findRequiredViewAsType(source, R.id.login_password_input, "field 'userPasswordInput'", EditText.class);
    view = Utils.findRequiredView(source, R.id.visibility_icon, "field 'visibilityIcon' and method 'changePasswordVisibility'");
    target.visibilityIcon = Utils.castView(view, R.id.visibility_icon, "field 'visibilityIcon'", ImageView.class);
    view2131427461 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.changePasswordVisibility();
      }
    });
    view = Utils.findRequiredView(source, R.id.login_button, "field 'loginButton' and method 'login'");
    target.loginButton = Utils.castView(view, R.id.login_button, "field 'loginButton'", Button.class);
    view2131427453 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.login();
      }
    });
    view = Utils.findRequiredView(source, R.id.sign_up_window_button, "field 'signUpWindowButton' and method 'openSignUp'");
    target.signUpWindowButton = Utils.castView(view, R.id.sign_up_window_button, "field 'signUpWindowButton'", Button.class);
    view2131427462 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openSignUp();
      }
    });
    target.loginLayout = Utils.findRequiredViewAsType(source, R.id.email_password_layout, "field 'loginLayout'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserLoginFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pokemonText = null;
    target.pokeball = null;
    target.userEmailInput = null;
    target.userPasswordInput = null;
    target.visibilityIcon = null;
    target.loginButton = null;
    target.signUpWindowButton = null;
    target.loginLayout = null;

    view2131427461.setOnClickListener(null);
    view2131427461 = null;
    view2131427453.setOnClickListener(null);
    view2131427453 = null;
    view2131427462.setOnClickListener(null);
    view2131427462 = null;
  }
}
