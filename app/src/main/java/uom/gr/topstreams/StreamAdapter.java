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

public class StreamAdapter extends ArrayAdapter<Stream> {

    private static final String TAG = "StreamAdapter";

    private final LayoutInflater inflater;
    private final int layoutResource;
    private List<Stream> streams;

    public StreamAdapter(@NonNull Context context, int resource, @NonNull List<Stream> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layoutResource = resource;
        streams = objects;
    }

    public Stream getStream(int position){
        if(position<streams.size()){
            return streams.get(position);
        }
        return new Stream();
    }
    public void setStreams(@Nullable List<Stream> streams){
        this.streams=streams;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return streams.size();
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

        Stream stream = streams.get(position);

        viewHolder.name.setText(stream.getName());
        viewHolder.game.setText(stream.getGame());
        viewHolder.status.setText("-"+stream.getStatus());
        viewHolder.viewers.setText(String.format("%,d",stream.getViewers()));
        Picasso.get().load(stream.getUrlToImage()).into(viewHolder.logo);
        Picasso.get().load(stream.getUrlToPreviewImage()).into(viewHolder.preview);

        return convertView;
    }

    static class ViewHolder {
        public TextView name;
        public TextView game;
        public TextView status;
        public TextView viewers;
        public ImageView logo;
        public ImageView preview;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.nameText);
            game = view.findViewById(R.id.gameText);
            status = view.findViewById(R.id.statusText);
            viewers= view.findViewById(R.id.viewersText);
            logo=view.findViewById(R.id.logoImage);
            preview=view.findViewById(R.id.previewImage);
        }
    }



}
