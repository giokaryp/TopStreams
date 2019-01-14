package uom.gr.topstreams;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;



/**
 * Created by Karypidis on 30-Dec-18.
 */

@IgnoreExtraProperties
public class Stream implements Parcelable {

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
    private String userId;
    private String streamId;



    public Stream(){}


    protected Stream(Parcel in) {
        name = in.readString();
        game = in.readString();
        status = in.readString();
        viewers = in.readInt();
        totalViews = in.readInt();
        followers = in.readInt();
        fps = in.readInt();
        delay = in.readInt();
        language = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        urlToPreviewImage = in.readString();
        urlToBanner = in.readString();
        userId=in.readString();
        streamId=in.readString();
    }

    public static final Creator<Stream> CREATOR = new Creator<Stream>() {
        @Override
        public Stream createFromParcel(Parcel in) {
            return new Stream(in);
        }

        @Override
        public Stream[] newArray(int size) {
            return new Stream[size];
        }
    };

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(game);
        dest.writeString(status);
        dest.writeInt(viewers);
        dest.writeInt(totalViews);
        dest.writeInt(followers);
        dest.writeInt(fps);
        dest.writeInt(delay);
        dest.writeString(language);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(urlToPreviewImage);
        dest.writeString(urlToBanner);
        dest.writeString(userId);
        dest.writeString(streamId);
    }
}
