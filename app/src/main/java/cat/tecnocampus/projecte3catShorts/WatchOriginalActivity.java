package cat.tecnocampus.projecte3catShorts;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class WatchOriginalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_original);

        // Obtiene la URL de la imagen desde el Intent
        String imageUrl = getIntent().getStringExtra("image_url");

        // Obtiene el ImageView para mostrar la imagen
        ImageView imageView = findViewById(R.id.fullscreenImageView);

        // Usa Glide para cargar la imagen en el ImageView
        Glide.with(this)
                .load("file:///android_asset/" + imageUrl + ".jpg")
                .into(imageView);

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            finish();
        });

    }
}
