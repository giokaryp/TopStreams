package uom.gr.topstreams;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Karypidis on 30-Dec-18.
 */

public class JSONGameParser {

    public static final String NAME="name";
    public static final String VIEWS="viewers";
    public static final String IMAGE_URL="large";

    private static final String TAG ="JSONStreamParser" ;


    private ArrayList<Game> games;

    public JSONGameParser(){
        games = new ArrayList<>();
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public boolean parseGames(String jsonData){
        try {
            JSONObject feed= new JSONObject(jsonData);
            JSONArray topGames=new JSONArray(feed.getString("top"));

            for(int i=0; i<topGames.length();i++){
                JSONObject topGame=topGames.getJSONObject(i);
                JSONObject game=new JSONObject(topGame.getString("game"));
                JSONObject image= new JSONObject(game.getString("box"));


                String name=game.getString(NAME);
                int viewers=topGame.getInt(VIEWS);
                String urlToImage=image.getString(IMAGE_URL);

                Game g=new Game();

                g.setName(name);
                g.setViewers(viewers);
                g.setUrlToImage(urlToImage);

                games.add(g);
            }

        } catch (JSONException e) {
            Log.e(TAG, "parseStreams: Error parsing json data", e);
            return false;
        }
        return true;

    }
}
