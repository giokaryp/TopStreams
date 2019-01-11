package uom.gr.topstreams;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Karypidis on 30-Dec-18.
 */

public class JSONStreamParser {

    public static final String NAME="name";
    public static final String GAME="game";
    public static final String STATUS="status";
    public static final String VIEWS="viewers";
    public static final String IMAGE_URL="logo";
    public static final String PREVIEW_IMAGE_URL="large";
    public static final String FOLLOWERS="followers";
    public static final String TOTAL_VIEWS="views";
    public static final String DELAY="delay";
    public static final String FPS="average_fps";
    public static final String LANGUAGE="language";
    public static final String BANNER_IMAGE_URL="profile_banner";
    public static final String CHANNE_URL="url";

    private static final String TAG ="JSONStreamParser" ;


    private ArrayList<Stream> streams;

    public JSONStreamParser(){
        streams = new ArrayList<>();
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    public boolean parseStreams(String jsonData){
        try {

            JSONObject feed= new JSONObject(jsonData);
            JSONArray liveStreams = new JSONArray(feed.getString("streams"));

            for(int i=0; i<liveStreams.length();i++){
                JSONObject liveStream=liveStreams.getJSONObject(i);
                JSONObject channel=new JSONObject(liveStream.getString("channel"));
                JSONObject preview= new JSONObject(liveStream.getString("preview"));


                String name=channel.getString(NAME);
                String game=liveStream.getString(GAME);
                String status=channel.getString(STATUS);
                int viewers=liveStream.getInt(VIEWS);
                int followers=channel.getInt(FOLLOWERS);
                int totalViews=channel.getInt(TOTAL_VIEWS);
                int fps=liveStream.getInt(FPS);
                int delay=liveStream.getInt(DELAY);
                String url=channel.getString(CHANNE_URL);
                String urlToImage=channel.getString(IMAGE_URL);
                String urlToPreviewImage=preview.getString(PREVIEW_IMAGE_URL);
                String urlToBannerImage=channel.getString(BANNER_IMAGE_URL);
                String language=channel.getString(LANGUAGE);

                Stream s=new Stream();

                s.setName(name);
                s.setGame(game);
                s.setStatus(status);
                s.setViewers(viewers);
                s.setFollowers(followers);
                s.setTotalViews(totalViews);
                s.setFps(fps);
                s.setDelay(delay);
                s.setUrl(url);
                s.setUrlToImage(urlToImage);
                s.setUrlToPreviewImage(urlToPreviewImage);
                s.setUrlToBanner(urlToBannerImage);
                s.setLanguage(language);

                streams.add(s);
            }

        } catch (JSONException e) {
            Log.e(TAG, "parseStreams: Error parsing json data", e);
            return false;
        }
        return true;

    }
}
