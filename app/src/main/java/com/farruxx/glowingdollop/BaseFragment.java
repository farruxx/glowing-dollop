package com.farruxx.glowingdollop;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.farruxx.glowingdollop.dagger.ServiceComponent;
import com.farruxx.glowingdollop.network.ArtistService;

import javax.inject.Inject;

/**
 * Created by Farruxx on 10.12.2015.
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    public ArtistService service;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment_toolbar, container, false);
        inflater.inflate(getLayoutId(), (ViewGroup) view.findViewById(R.id.content), true);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(isHasToolbar());
        return view;

    }

    protected boolean isHasToolbar(){
        return false;
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    protected abstract @LayoutRes int getLayoutId();

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        init(getView(), savedInstanceState);

    }

    protected abstract void init(View view, Bundle savedInstanceState);

    public void showLoading(){
        if(getActivity() != null && toolbar != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.loading));
        }
    }

    public void hideLoading(){
        if(getActivity() != null && toolbar != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        }
    }

    public ServiceComponent getComponent(){
        return ((App)getActivity().getApplication()).getComponent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
