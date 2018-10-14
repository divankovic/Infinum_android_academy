// Generated code from Butter Knife. Do not modify!
package infinum.pokemonapp.pokemon;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import infinum.pokemonapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PokemonAddFragment_ViewBinding implements Unbinder {
  private PokemonAddFragment target;

  private View view2131427428;

  private View view2131427436;

  @UiThread
  public PokemonAddFragment_ViewBinding(final PokemonAddFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.pokemon_picture, "field 'pokemonPicture' and method 'setPokemonPicture'");
    target.pokemonPicture = Utils.castView(view, R.id.pokemon_picture, "field 'pokemonPicture'", ImageView.class);
    view2131427428 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setPokemonPicture();
      }
    });
    target.nameInput = Utils.findRequiredViewAsType(source, R.id.name_input, "field 'nameInput'", EditText.class);
    target.height = Utils.findRequiredViewAsType(source, R.id.height, "field 'height'", EditText.class);
    target.weight = Utils.findRequiredViewAsType(source, R.id.weight, "field 'weight'", EditText.class);
    target.category = Utils.findRequiredViewAsType(source, R.id.category, "field 'category'", EditText.class);
    target.abilities = Utils.findRequiredViewAsType(source, R.id.abilities, "field 'abilities'", EditText.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.description, "field 'description'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_save, "field 'btnSave' and method 'save'");
    target.btnSave = Utils.castView(view, R.id.btn_save, "field 'btnSave'", Button.class);
    view2131427436 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.save();
      }
    });
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.linear_layout, "field 'linearLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PokemonAddFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pokemonPicture = null;
    target.nameInput = null;
    target.height = null;
    target.weight = null;
    target.category = null;
    target.abilities = null;
    target.description = null;
    target.btnSave = null;
    target.linearLayout = null;

    view2131427428.setOnClickListener(null);
    view2131427428 = null;
    view2131427436.setOnClickListener(null);
    view2131427436 = null;
  }
}
