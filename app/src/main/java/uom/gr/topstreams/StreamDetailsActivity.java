package uom.gr.topstreams;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class StreamDetailsActivity extends AppCompatActivity {


    ImageView logoImage;
    ImageView bannerImage;
    TextView nameTextView;
    TextView statusTextView;
    TextView gameTextView;
    TextView viewsTextView;
    TextView followsTextView;
    TextView totalViewsTextView;
    TextView avgFpsTextView;
    TextView delayTextView;
    TextView languageTextView;
    Button watchButton;
    Button shareButton;


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

        Intent intent = getIntent();
        final Stream stream = (Stream) intent.getSerializableExtra("Stream");

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


    }

}
