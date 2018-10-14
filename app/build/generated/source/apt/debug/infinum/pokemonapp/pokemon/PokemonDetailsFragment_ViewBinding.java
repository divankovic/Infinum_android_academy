// Generated code from Butter Knife. Do not modify!
package infinum.pokemonapp.pokemon;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import infinum.pokemonapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PokemonDetailsFragment_ViewBinding implements Unbinder {
  private PokemonDetailsFragment target;

  @UiThread
  public PokemonDetailsFragment_ViewBinding(PokemonDetailsFragment target, View source) {
    this.target = target;

    target.detailsPicture = Utils.findRequiredViewAsType(source, R.id.details_picture, "field 'detailsPicture'", ImageView.class);
    target.detailsName = Utils.findRequiredViewAsType(source, R.id.details_name, "field 'detailsName'", TextView.class);
    target.detailsDescription = Utils.findRequiredViewAsType(source, R.id.details_description, "field 'detailsDescription'", TextView.class);
    target.pokemonHeight = Utils.findRequiredViewAsType(source, R.id.pokemon_height, "field 'pokemonHeight'", TextView.class);
    target.pokemonWeight = Utils.findRequiredViewAsType(source, R.id.pokemon_weight, "field 'pokemonWeight'", TextView.class);
    target.pokemonCategory = Utils.findRequiredViewAsType(source, R.id.pokemon_category, "field 'pokemonCategory'", TextView.class);
    target.pokemonAbilities = Utils.findRequiredViewAsType(source, R.id.pokemon_abilities, "field 'pokemonAbilities'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PokemonDetailsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.detailsPicture = null;
    target.detailsName = null;
    target.detailsDescription = null;
    target.pokemonHeight = null;
    target.pokemonWeight = null;
    target.pokemonCategory = null;
    target.pokemonAbilities = null;
  }
}
