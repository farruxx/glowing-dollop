package com.farruxx.glowingdollop.dagger;

import com.farruxx.glowingdollop.BaseFragment;
import com.farruxx.glowingdollop.dagger.module.AppModule;
import com.farruxx.glowingdollop.dagger.module.ServiceModule;
import com.farruxx.glowingdollop.fragment.MainFragment;
import com.farruxx.glowingdollop.network.ArtistService;
import com.google.gson.Gson;
import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ServiceModule.class, AppModule.class})
public interface ServiceComponent {

  void inject(BaseFragment fragment);

  Retrofit retrofit();
  OkHttpClient okHttpClient();
  ArtistService artistService();
}
