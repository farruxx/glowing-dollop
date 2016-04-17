package com.farruxx.glowingdollop.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Farruxx on 10.04.2016.
 */
public class Artist implements Serializable{
    public Integer id;
    public String name;
    public String [] genres;
    public Integer tracks;
    public Integer albums;
    public String link;
    public String description;
    public Cover cover;

    public transient String genresString;

    public String getGenres(){
        if(genresString == null) {
            genresString = arrayToCommaSeparatedString(genres);
        }
        return genresString;
    }

    private String arrayToCommaSeparatedString(String[] genres) {
        StringBuilder result = new StringBuilder();
        if (genres != null && genres.length > 0) {
            List<String> genresList = Arrays.asList(genres);
            Iterator iterator = genresList.iterator();
            while (iterator.hasNext()) {
                result.append(iterator.next());
                if (iterator.hasNext()) {
                    result.append(", ");
                }
            }
        }
        return result.toString();
    }

    public class Cover implements Serializable{
        public String small;
        public String big;
    }
}
