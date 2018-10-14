// Generated code from Butter Knife. Do not modify!
package infinum.pokemonapp.user;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import infinum.pokemonapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserRegisterFragment_ViewBinding implements Unbinder {
  private UserRegisterFragment target;

  private View view2131427471;

  @UiThread
  public UserRegisterFragment_ViewBinding(final UserRegisterFragment target, View source) {
    this.target = target;

    View view;
    target.registerEmailInput = Utils.findRequiredViewAsType(source, R.id.register_email_input, "field 'registerEmailInput'", EditText.class);
    target.registerUsernameInput = Utils.findRequiredViewAsType(source, R.id.register_username_input, "field 'registerUsernameInput'", EditText.class);
    target.registerPasswordInput = Utils.findRequiredViewAsType(source, R.id.register_password_input, "field 'registerPasswordInput'", EditText.class);
    target.registerPasswordRepeatInput = Utils.findRequiredViewAsType(source, R.id.register_password_repeat_input, "field 'registerPasswordRepeatInput'", EditText.class);
    view = Utils.findRequiredView(source, R.id.sign_up_button, "field 'signUpButton' and method 'signUp'");
    target.signUpButton = Utils.castView(view, R.id.sign_up_button, "field 'signUpButton'", Button.class);
    view2131427471 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.signUp();
      }
    });
    target.registerToolbar = Utils.findRequiredViewAsType(source, R.id.register_toolbar, "field 'registerToolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserRegisterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.registerEmailInput = null;
    target.registerUsernameInput = null;
    target.registerPasswordInput = null;
    target.registerPasswordRepeatInput = null;
    target.signUpButton = null;
    target.registerToolbar = null;

    view2131427471.setOnClickListener(null);
    view2131427471 = null;
  }
}
