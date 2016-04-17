package com.farruxx.glowingdollop.adapter;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farruxx.glowingdollop.BaseFragment;
import com.farruxx.glowingdollop.R;
import com.farruxx.glowingdollop.model.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Farruxx on 10.04.2016.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistHolder>{
    List<Artist> artists;
    LayoutInflater inflater;
    BaseFragment fragment;

    public ArtistAdapter(BaseFragment fragment, List<Artist> artists) {
        this.fragment = fragment;
        this.artists = artists;
        this.inflater = fragment.getActivity().getLayoutInflater();
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistHolder(inflater.inflate(R.layout.artist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.name.setText(artist.name);
        holder.genres.setText(artist.getGenres());
        holder.songs.setText(fragment.getString(R.string.albums_and_songs,artist.albums, artist.tracks));
        if(artist.cover != null && artist.cover.small != null) {
            Picasso.with(fragment.getActivity())
                    .load(artist.cover.small)
                    .error(R.drawable.holder)
                    .placeholder(R.drawable.holder)
                    .into(holder.cover);
        }
        holder.itemView.setTag(artist);
        holder.itemView.setOnClickListener((View.OnClickListener) fragment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.genres.setTransitionName("transtext" + position);
            holder.cover.setTransitionName("transition" + position);
            holder.songs.setTransitionName("songs" + position);
        }
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}
