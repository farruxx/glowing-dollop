package com.farruxx.glowingdollop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.farruxx.glowingdollop.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Farruxx on 10.04.2016.
 */
public class ArtistHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.cover)
    ImageView cover;

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.genres)
    TextView genres;

    @Bind(R.id.songs)
    TextView songs;

    public ArtistHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
