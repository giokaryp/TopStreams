package uom.gr.topstreams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class GameStreamsActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar loader;
    private StreamAdapter streamAdapter;
    private String baseUrl = "https://api.twitch.tv/kraken/streams?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stream_list_fragment);

        String gameName= getIntent().getStringExtra("gameName");
        setTitle(gameName);

        final String url = baseUrl +"&game="+ gameName;

        listView = findViewById(R.id.streamList);
        loader=findViewById(R.id.loader);
        listView.setEmptyView(loader);

        streamAdapter = new StreamAdapter(this, R.layout.stream_list_item, new ArrayList<Stream>());
        listView.setAdapter(streamAdapter);

        final SwipeRefreshLayout pullToRefresh =findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(DownloadStreamData.isNetworkAvailable(getApplicationContext())) {
                    downloadDataFromUrl(url);
                }else{

                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

                }
                pullToRefresh.setRefreshing(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(GameStreamsActivity.this, StreamDetailsActivity.class);
                intent.putExtra("Stream", streamAdapter.getStream(i));
                startActivity(intent);
            }
        });

        if (DownloadStreamData.isNetworkAvailable(getApplicationContext())) {
            downloadDataFromUrl(url);
        } else {

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }


    }

    private void downloadDataFromUrl(String url) {
        DownloadStreamData downloadStreamData = new DownloadStreamData(streamAdapter);
        downloadStreamData.execute(url);

    }
}
