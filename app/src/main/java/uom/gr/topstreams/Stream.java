package uom.gr.topstreams;

import java.io.Serializable;

/**
 * Created by Karypidis on 30-Dec-18.
 */

public class Stream implements Serializable {

    private String name;
    private String game;
    private String status;
    private int viewers;
    private int totalViews;
    private int followers;
    private int fps;
    private int delay;
    private String language;
    private String url;
    private String urlToImage;
    private String urlToPreviewImage;
    private String urlToBanner;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlToPreviewImage() {
        return urlToPreviewImage;
    }

    public void setUrlToPreviewImage(String urlToPreviewImage) {
        this.urlToPreviewImage = urlToPreviewImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrlToBanner() {
        return urlToBanner;
    }

    public void setUrlToBanner(String urlToBanner) {
        this.urlToBanner = urlToBanner;
    }
}
