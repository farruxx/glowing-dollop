package com.farruxx.glowingdollop.network;

import com.farruxx.glowingdollop.model.Artist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArtistService {
  @GET("/mobilization-2016/artists.json")
  Call<List<Artist>> listArtists();
}