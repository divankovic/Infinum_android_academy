package infinum.pokemonapp.pokemon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import infinum.pokemonapp.ApiCall;
import infinum.pokemonapp.R;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<Pokemon> pokemonList;
    private detailsFragmentOpen itemOnClickListener;
    private Context context;


    public interface detailsFragmentOpen {
        public void openDetailsFragment(int position);
    }

    public PokemonAdapter(List<Pokemon> pokemonList, Context context, detailsFragmentOpen itemOnClickListener) {
        this.context = context;
        this.pokemonList = pokemonList;
        this.itemOnClickListener = itemOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CircleImageView pokPicture = (CircleImageView) holder.itemView.findViewById(R.id.pok_picture);
        TextView pokemonName = (TextView) holder.itemView.findViewById(R.id.pokemon_name);

        if (pokemonList.get(position).getImage() != null) {
                Glide.with(context).load(pokemonList.get(position).getImage()).into(pokPicture);
        }else{
            pokPicture.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_person_details));
        }

        pokemonName.setText(pokemonList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClickListener.openDetailsFragment(getAdapterPosition());
                }
            });
        }
    }
}
