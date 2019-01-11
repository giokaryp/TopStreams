package uom.gr.topstreams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Karypidis on 19-Dec-18.
 */

class DownloadStreamData extends AsyncTask<String, Void, String> {


    private static final String TAG ="geo" ;
    private static final String ClientID = "l5szdfxei5kb3n9q186o0as6x84ia1";

    private StreamAdapter streamAdapter;
    private JSONStreamParser parser=new JSONStreamParser();

    public DownloadStreamData(StreamAdapter streamAdapter){
        this.streamAdapter= streamAdapter;
    }

    public static boolean isNetworkAvailable(Context context)
    {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    @Override
    protected String doInBackground(String... strings) {

        Log.d(TAG, "doInBackground starts with: " + strings[0]);
        String streamData = downloadJSON(strings[0]);
        return streamData;
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
        Log.d(TAG, "onPostExecute parameter is " + jsonData);

        parser.parseStreams(jsonData);

        ArrayList<Stream> streams=parser.getStreams();
        streamAdapter.setStreams(streams);


    }

    private String downloadJSON(String urlPath) {
        StringBuilder sb = new StringBuilder();

        try {

            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application/vnd.twitchtv.v5+json");
            connection.setRequestProperty("Client-ID",ClientID);
            //connection.setRequestProperty("Authorization","OAuth ze239b0w3qg5jlxew1906rsbqz6ibu");

            int responseCode = connection.getResponseCode();
            Log.d(TAG, "downloadJSON: Response code was " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line =reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            reader.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            Log.e(TAG, "downloadJSON: not correct URL: " + urlPath, e);
        } catch (IOException e) {
            Log.e(TAG, "downloadJSON: io error ", e);
        }
        return sb.toString();
    }

    public JSONStreamParser getParser() {
        return parser;
    }
}
