// Generated code from Butter Knife. Do not modify!
package infinum.pokemonapp.pokemon;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import infinum.pokemonapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PokemonListFragment_ViewBinding implements Unbinder {
  private PokemonListFragment target;

  @UiThread
  public PokemonListFragment_ViewBinding(PokemonListFragment target, View source) {
    this.target = target;

    target.recView = Utils.findRequiredViewAsType(source, R.id.rec_view, "field 'recView'", RecyclerView.class);
    target.emptyListLayout = Utils.findRequiredViewAsType(source, R.id.empty_list_layout, "field 'emptyListLayout'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PokemonListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recView = null;
    target.emptyListLayout = null;
  }
}
