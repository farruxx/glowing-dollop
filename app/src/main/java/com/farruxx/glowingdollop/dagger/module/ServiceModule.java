package com.farruxx.glowingdollop.dagger.module;

import android.app.Application;
import com.farruxx.glowingdollop.network.ArtistService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module
public class ServiceModule {

  @Provides
  @Singleton
  @SuppressWarnings("unused")
  ArtistService provideArtistService(Retrofit retrofit) {
    return retrofit.create(ArtistService.class);
  }


  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient(Application application) {
    int cacheSize = 10 * 1024 * 1024;
    Cache cache = new Cache(application.getCacheDir(), cacheSize);
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.cache(cache);
    return builder.build();
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .baseUrl("http://download.cdn.yandex.net")
            .client(okHttpClient)
            .build();
    return retrofit;
  }
}
