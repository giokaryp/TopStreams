package uom.gr.topstreams;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StreamDetailsActivity extends AppCompatActivity {


    private static final String TAG ="StreamDetailsActivity" ;

    private DocumentSnapshot mLastQueriedDocument;
    private ArrayList<Stream> fStreams = new ArrayList<>();

    private ImageView logoImage;
    private ImageView bannerImage;
    private TextView nameTextView;
    private TextView statusTextView;
    private TextView gameTextView;
    private TextView viewsTextView;
    private TextView followsTextView;
    private TextView totalViewsTextView;
    private TextView avgFpsTextView;
    private TextView delayTextView;
    private TextView languageTextView;
    private  Button watchButton;
    private  Button shareButton;
    private ToggleButton followButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_details);



        logoImage = findViewById(R.id.logoImageView);
        bannerImage = findViewById(R.id.bannerImageView);
        nameTextView = findViewById(R.id.nameTextView);
        statusTextView = findViewById(R.id.statusTextView);
        gameTextView = findViewById(R.id.gameTextView);
        viewsTextView = findViewById(R.id.viewersTextView);
        followsTextView = findViewById(R.id.followsTextView);
        totalViewsTextView = findViewById(R.id.totalViewsTextView);
        avgFpsTextView = findViewById(R.id.avgFpsTextView);
        delayTextView = findViewById(R.id.delayTextView);
        languageTextView = findViewById(R.id.langTextView);
        watchButton = findViewById(R.id.watchButton);
        shareButton = findViewById(R.id.shareButton);
        followButton=findViewById(R.id.followButton);

        Intent intent = getIntent();
        final Stream stream = intent.getParcelableExtra("Stream");


        Picasso.get().load(stream.getUrlToImage()).into(logoImage);
        Picasso.get().load(stream.getUrlToBanner()).into(bannerImage);
        nameTextView.setText(stream.getName());
        statusTextView.setText(stream.getStatus());
        gameTextView.setText(stream.getGame());
        viewsTextView.setText(String.format("%,d",stream.getViewers()));
        followsTextView.setText(String.format("%,d",stream.getFollowers()));
        totalViewsTextView.setText(String.format("%,d",stream.getTotalViews()));
        avgFpsTextView.setText(String.valueOf(stream.getFps()));
        delayTextView.setText(String.format("%,d",stream.getDelay())+" sec");
        languageTextView.setText(stream.getLanguage());

        gameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(StreamDetailsActivity.this,GameStreamsActivity.class);
                intent.putExtra("gameName",gameTextView.getText());
                startActivity(intent);
            }
        });

        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(stream.getUrl())));
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, stream.getUrl());
                startActivity(Intent.createChooser(share, "Share Stream"));
            }
        });

        if(isFollowed(stream)){
            followButton.setChecked(true);
        }

        followButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    addStreamToFollowed(stream);
                }else{
                  unfollowStream(stream);
                }
            }
        });


    }
    public void addStreamToFollowed(Stream stream) {



        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference newStreamRef = db
                .collection("streams")
                .document();

        Stream fStream = new Stream();

        fStream.setName(stream.getName());
        fStream.setGame(stream.getGame());
        fStream.setFollowers(stream.getFollowers());
        fStream.setUrlToImage(stream.getUrlToImage());
        fStream.setUrl(stream.getUrl());
        fStream.setStatus(stream.getStatus());
        fStream.setFollowers(stream.getFollowers());
        fStream.setStreamId(newStreamRef.getId());
        fStream.setUserId(userId);

        newStreamRef.set(fStream).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(StreamDetailsActivity.this, "Failed to follow the Stream...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public Boolean isFollowed(Stream currStream){
        ArrayList<Stream> streams=getFollowedStreams();

        for(Stream stream: streams){
            if(stream.getName().equals(currStream.getName())){
                return true;
            }
        }
        return false;

    }

    protected ArrayList<Stream> getFollowedStreams(){


        CollectionReference streamsCollectionRef = db
                .collection("streams");

        Query streamsQuery = null;
        if(mLastQueriedDocument != null){
            streamsQuery = streamsCollectionRef
                    .whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .startAfter(mLastQueriedDocument);
        }
        else{
            streamsQuery = streamsCollectionRef
                    .whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .orderBy("followers", Query.Direction.DESCENDING);
        }
        streamsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "onComplete: query task completed");
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){
                        Stream fStream = document.toObject(Stream.class);
                        fStreams.add(fStream);
                    }

                    if(task.getResult().size() != 0){
                        mLastQueriedDocument = task.getResult().getDocuments()
                                .get(task.getResult().size() -1);
                    }
                }
                else{
                    Toast.makeText(StreamDetailsActivity.this, "Query Failed. Check Logs.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return  fStreams;


    }
    public void unfollowStream(final Stream fStream) {


        DocumentReference streamRef = db
                .collection("notes")
                .document(fStream.getStreamId());

        streamRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(StreamDetailsActivity.this, "Unfollowed "+fStream.getName(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(StreamDetailsActivity.this, "Failed to unfollow", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
