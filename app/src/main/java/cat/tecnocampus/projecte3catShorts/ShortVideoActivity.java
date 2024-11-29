package cat.tecnocampus.projecte3catShorts;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cat.tecnocampus.projecte3catShorts.domain.ShortVideo;

public class ShortVideoActivity extends AppCompatActivity {

    private List<ShortVideo> shortVideos;
    private RecyclerView shortRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_video);

        shortRecyclerView = findViewById(R.id.shortRecyclerView);
        shortVideos = loadAllShortVideos();

        ShortVideoAdapter adapter = new ShortVideoAdapter(shortVideos, this);
        shortRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        shortRecyclerView.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(shortRecyclerView);

        shortRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    LinearLayoutManager layoutManager = (LinearLayoutManager) shortRecyclerView.getLayoutManager();
                    if (layoutManager != null) {
                        int visiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                        if (visiblePosition != RecyclerView.NO_POSITION) {

                            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(visiblePosition);
                            if (viewHolder instanceof ShortVideoAdapter.ShortViewHolder) {
                                ShortVideoAdapter.ShortViewHolder shortViewHolder = (ShortVideoAdapter.ShortViewHolder) viewHolder;

                                if (ShortVideoAdapter.activeVideoView != null &&
                                        ShortVideoAdapter.activeVideoView != shortViewHolder.videoView) {
                                    ShortVideoAdapter.activeVideoView.pause();
                                }

                                ShortVideoAdapter.activeVideoView = shortViewHolder.videoView;
                                shortViewHolder.videoView.start();
                            }
                        }
                    }
                }
            }
        });


    }

    private List<ShortVideo> loadAllShortVideos() {
        List<ShortVideo> videos = new ArrayList<>();
        try {
            String[] files = getAssets().list("");
            for (String file : files) {
                if (file.endsWith(".json")) {
                    ShortVideo video = loadShortVideoFromJson(file);
                    if (video != null) {
                        videos.add(video);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videos;
    }

    private ShortVideo loadShortVideoFromJson(String fileName) {
        try {
            InputStream inputStream = getAssets().open(fileName);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            ShortVideo shortVideo = gson.fromJson(reader, ShortVideo.class);
            reader.close();

            System.out.println("Short video loaded");
            System.out.println(shortVideo);
            return shortVideo;
        } catch (IOException e) {
            System.out.println("Error loading short video");
            e.printStackTrace();
            return null;
        }


    }



}

