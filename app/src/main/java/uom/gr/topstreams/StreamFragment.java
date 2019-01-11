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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Karypidis on 01-Jan-19.
 */

public class StreamFragment extends Fragment {

    private StreamAdapter streamAdapter;
    ListView listView;
    ProgressBar loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);




        return inflater.inflate(R.layout.stream_list_fragment, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        listView = view.findViewById(R.id.streamList);
        loader=view.findViewById(R.id.loader);
        listView.setEmptyView(loader);





        streamAdapter = new StreamAdapter(getContext(), R.layout.stream_list_item, new ArrayList<Stream>());
        listView.setAdapter(streamAdapter);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(DownloadStreamData.isNetworkAvailable(getContext())) {
                    downloadDataFromUrl("https://api.twitch.tv/kraken/streams");
                }else{

                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

                }
                pullToRefresh.setRefreshing(false);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {



                Intent intent=new Intent(getContext(),StreamDetailsActivity.class);

                intent.putExtra("Stream",streamAdapter.getStream(i));


               startActivity(intent);

            }
        });


        if(DownloadStreamData.isNetworkAvailable(getContext())) {
            downloadDataFromUrl("https://api.twitch.tv/kraken/streams");
        }else{

            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }



    }

    private void downloadDataFromUrl(String url) {
        DownloadStreamData downloadStreamData = new DownloadStreamData(streamAdapter);
        downloadStreamData.execute(url);

    }
}

