package uom.gr.topstreams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Karypidis on 01-Jan-19.
 */

public class GameFragment extends Fragment {
    private GameAdapter gameAdapter;
   GridView gridView;
   ProgressBar loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.game_grid_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gameGrid);
        loader=view.findViewById(R.id.loader);
        gridView.setEmptyView(loader);


        gameAdapter = new GameAdapter(getContext(), R.layout.game_grid_item, new ArrayList<Game>());
        gridView.setAdapter(gameAdapter);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(DownloadStreamData.isNetworkAvailable(getContext())) {
                    downloadDataFromUrl("https://api.twitch.tv/kraken/games/top?&limit=25");
                }else{

                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

                }
                pullToRefresh.setRefreshing(false);
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Game game = gameAdapter.getGame(i);
                String gameName=game.getName();

                Intent intent = new Intent(getContext(),GameStreamsActivity.class);



                intent.putExtra("gameName",gameName);
                startActivity(intent);
            }
        });

        if(DownloadStreamData.isNetworkAvailable(getContext())) {
            downloadDataFromUrl("https://api.twitch.tv/kraken/games/top?&limit=25");
        }else{

            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }


    }

    private void downloadDataFromUrl(String url) {
        DownloadGameData downloadGameData = new DownloadGameData(gameAdapter);
        downloadGameData.execute(url);

    }
}
