package com.farruxx.glowingdollop.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.farruxx.glowingdollop.BaseFragment;
import com.farruxx.glowingdollop.R;
import com.farruxx.glowingdollop.model.Artist;
import com.farruxx.glowingdollop.util.Const;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by Farruxx on 10.04.2016.
 */
public class DetailFragment extends BaseFragment {
    @Bind(R.id.cover)
    ImageView cover;
    @Bind(R.id.genres)
    TextView genres;
    @Bind(R.id.songs)
    TextView songs;

    @Bind(R.id.biography)
    TextView biography;
    @Override
    protected int getLayoutId() {
        return R.layout.detail_fragment;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        final Artist artist = (Artist) getArguments().getSerializable(Const.ARTIST);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(artist.name);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String transCover = getArguments().getString(Const.COVER);
            String transText = getArguments().getString(Const.GENRES);
            String songsText = getArguments().getString(Const.SONGS);
            cover.setTransitionName(transCover);
            genres.setTransitionName(transText);
            songs.setTransitionName(songsText);
        }

        genres.setText(artist.getGenres().replace(",", " •"));
        biography.setText(artist.description);
        songs.setText(getString(R.string.albums_and_songs,artist.albums, artist.tracks));
        Picasso.with(getActivity())
                .load(artist.cover.small)
                .placeholder(R.drawable.holder)
                .into(cover, new Callback() {
                    @Override
                    public void onSuccess() {
                        if(getActivity() != null) {
                            Picasso.with(getActivity())
                                    .load(artist.cover.big)
                                    .noFade()
                                    .placeholder(cover.getDrawable())
                                    .into(cover);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

    }


    @Override
    protected boolean isHasToolbar() {
        return true;
    }
}
