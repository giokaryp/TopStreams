package uom.gr.topstreams;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;


public class MyStreamsFragment extends Fragment {

    private static final String TAG = "MyStreams";

    private ListView fStreamsListView;
    private MyStreamsAdapter fStreamsAdapter;
    private ArrayList<Stream> fStreams = new ArrayList<>();








    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStreamsListView=view.findViewById(R.id.fStreamListView);

        fStreamsAdapter= new MyStreamsAdapter(getContext(),R.layout.my_streams_list_item,getFollowedStreams());

        initiateListView();


    }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){

            return inflater.inflate(R.layout.fragment_my_streams, container, false);
        }

    private void initiateListView() {


        fStreamsListView.setAdapter(fStreamsAdapter);
        fStreamsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fStreamsAdapter.getStream(i).getUrl())));
            }
        });

    }

    protected ArrayList<Stream> getFollowedStreams(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

                 db.collection("streams")
                .whereEqualTo("userId",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("followers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                Log.d(TAG, "onComplete: query task completed");
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){
                        Stream fStream = document.toObject(Stream.class);
                        fStreams.add(fStream);
                    }

                }
                else{
                    Toast.makeText(getContext(), "Query Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return  fStreams;


    }


    }