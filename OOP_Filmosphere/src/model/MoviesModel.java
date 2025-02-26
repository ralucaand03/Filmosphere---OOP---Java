package model;

import java.util.Date;
import java.util.List;

public class MoviesModel {
    private List<MoviesModel> moviesList;  // New property

    private int movie_id;
    private String title;
    private int duration;
    private Date release_date;
    private boolean liked;
    private String description;
    private String posterUrl;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }
    public boolean isLiked() {
        return liked;
    }
    public void setLiked(boolean liked) {
        this.liked = liked;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    // New getter and setter for moviesList
    public List<MoviesModel> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<MoviesModel> moviesList) {
        this.moviesList = moviesList;
    }
}
