package com.farruxx.glowingdollop.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import com.farruxx.glowingdollop.BaseFragment;
import com.farruxx.glowingdollop.R;
import com.farruxx.glowingdollop.adapter.ArtistAdapter;
import com.farruxx.glowingdollop.model.Artist;
import com.farruxx.glowingdollop.util.Const;
import com.farruxx.glowingdollop.util.DividerItemDecoration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farruxx on 10.04.2016.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    List<Artist> artists = new ArrayList<>();
    ArtistAdapter adapter;
    LinearLayoutManager layoutManager;
    Parcelable mListState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        adapter = new ArtistAdapter(this, artists);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        if(savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable("LIST_STATE");
            if (mListState != null) {
                layoutManager.onRestoreInstanceState(mListState);
            }
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        if(artists.isEmpty()) {
            showLoading();
            Call<List<Artist>> artistsCall = service.listArtists();
            artistsCall.enqueue(new Callback<List<Artist>>() {
                @Override
                public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                    hideLoading();
                    artists.clear();
                    artists.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Artist>> call, Throwable t) {
                    hideLoading();
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mListState = layoutManager.onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            layoutManager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.artist_item:
                ImageView image = (ImageView) v.findViewById(R.id.cover);
                View text = v.findViewById(R.id.genres);
                View songsView = v.findViewById(R.id.songs);
                Fragment fragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.ARTIST, (Serializable) v.getTag());
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.root, fragment)
                        .addToBackStack("transaction");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String genres = text.getTransitionName();
                    String cover= image.getTransitionName();
                    String songs= songsView.getTransitionName();

                    setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_image_transform));
                    setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
                    fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_image_transform));
                    fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.no_transition));
                    fragment.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));
                    ft.addSharedElement(text, genres);
                    ft.addSharedElement(image, cover);
                    ft.addSharedElement(songsView, songs);


                    bundle.putString(Const.COVER, cover);
                    bundle.putString(Const.GENRES, genres);
                    bundle.putString(Const.SONGS, songs);
                }

                ft.commit();
                break;
        }
    }

    @Override
    protected boolean isHasToolbar() {
        return true;
    }
}
