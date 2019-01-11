package uom.gr.topstreams;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Karypidis on 30-Dec-18.
 */

public class GameAdapter extends ArrayAdapter<Game> {

    private static final String TAG = "GameAdapter";

    private final LayoutInflater inflater;
    private final int layoutResource;
    private List<Game> games;

    public GameAdapter(@NonNull Context context, int resource, @NonNull List<Game> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layoutResource = resource;
        games = objects;
    }

    public Game getGame(int position){
        if(position<games.size()){
            return games.get(position);
        }
        return new Game();
    }
    public void setGames(@Nullable List<Game> games){
        this.games=games;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return games.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Game game = games.get(position);

        viewHolder.name.setText(game.getName());
        viewHolder.viewers.setText(String.format("%,d",game.getViewers()));
        Picasso.get().load(game.getUrlToImage()).into(viewHolder.logo);

        return convertView;
    }

    static class ViewHolder {
        public TextView name;
        public TextView viewers;
        public ImageView logo;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.gameNameText);
            viewers= view.findViewById(R.id.gameViewersText);
            logo=view.findViewById(R.id.gameLogoImage);
        }
    }



}
