package com.farruxx.glowingdollop.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.View;
import butterknife.Bind;
import com.farruxx.glowingdollop.BaseFragment;
import com.farruxx.glowingdollop.R;
import com.farruxx.glowingdollop.adapter.ArtistAdapter;
import com.farruxx.glowingdollop.model.Artist;
import com.farruxx.glowingdollop.provider.ArtistProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ArtistProvider artistProvider = new ArtistProvider();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        adapter = new ArtistAdapter(this, artists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        Call<List<Artist>> artistsCall = service.listArtists();
        showLoading();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView:
                Fragment fragment = new DetailFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.root, fragment)
                        .addToBackStack("transaction");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_image_transform));
                    setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
                    fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_image_transform));
                    fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));

//                    ft.addSharedElement(image, getString(R.string.app_name));
                }

                ft.commit();
                break;
        }
    }

    @Override
    protected boolean isHasToolbar() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        artistProvider.bind(this, adapter,artists);
    }

    @Override
    public void onPause() {
        super.onPause();
        artistProvider.unbind();
    }
}
