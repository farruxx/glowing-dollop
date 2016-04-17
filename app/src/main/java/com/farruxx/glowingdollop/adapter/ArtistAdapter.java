package com.farruxx.glowingdollop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.farruxx.glowingdollop.BaseFragment;
import com.farruxx.glowingdollop.R;
import com.farruxx.glowingdollop.model.Artist;

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
            Glide.with(fragment).load(artist.cover.small).into(holder.cover);
        }
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}
