package cat.tecnocampus.projecte3catShorts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cat.tecnocampus.projecte3catShorts.domain.ShortVideo;

public class ShortVideoAdapter extends RecyclerView.Adapter<ShortVideoAdapter.ShortViewHolder> {

    private List<ShortVideo> videos;
    private Context context;

    public static VideoView activeVideoView = null;

    public ShortVideoAdapter(List<ShortVideo> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public ShortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.short_item, parent, false);
        return new ShortViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortViewHolder holder, int position) {
        ShortVideo shortVideo = videos.get(position);

        // Cargar video
        String videoFileName = "short" + shortVideo.getId() + ".mp4";
        holder.playVideoFromAssets(videoFileName);

        // Configurar likes
        holder.likesTextView.setText(String.valueOf(shortVideo.getLikes()));
        holder.likeButton.setOnClickListener(v -> {
            int currentLikes = Integer.parseInt(holder.likesTextView.getText().toString());
            if (shortVideo.isLiked()) {
                currentLikes--;
            }
            else{
                currentLikes++;
            }
            shortVideo.alterLiked();
            holder.likesTextView.setText(String.valueOf(currentLikes));

            // Cambiar el icono del botón de like
            holder.likeButton.setImageResource(shortVideo.isLiked() ? R.drawable.ic_like_pressed : R.drawable.ic_like);
        });

        // Configurar título y descripción
        holder.titleTextView.setText(shortVideo.getTitol());
        holder.descriptionTextView.setText(shortVideo.getDescripcio());

        // Configurar botón para ir a la serie
        holder.btnGoToUrl.setOnClickListener(v -> {
            // Obtén la URL de la imagen desde el short
            String imageUrl = shortVideo.getUrlSerie();

            // Crea un Intent para abrir la actividad de pantalla completa
            Intent intent = new Intent(context, WatchOriginalActivity.class);
            intent.putExtra("image_url", imageUrl);

            // Inicia la nueva actividad
            context.startActivity(intent);
        });


        holder.commentButton.setOnClickListener(v -> {
            holder.areCommentsVisible = !holder.areCommentsVisible;
            holder.commentsView.setVisibility(holder.areCommentsVisible ? View.VISIBLE : View.GONE);
        });

        holder.closeCommentsButton.setOnClickListener(v -> {
            holder.areCommentsVisible = false;
            holder.commentsView.setVisibility(View.GONE);
        });

        // Configurar comentarios
        CommentsAdapter commentsAdapter = new CommentsAdapter(shortVideo.getComentaris());
        holder.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.commentsRecyclerView.setAdapter(commentsAdapter);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ShortViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView likesTextView, titleTextView, descriptionTextView;
        ImageButton likeButton, commentButton, closeCommentsButton;
        boolean areCommentsVisible;
        RecyclerView commentsRecyclerView;

        LinearLayout commentsView, btnGoToUrl;


        public ShortViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            likesTextView = itemView.findViewById(R.id.likesTextView);
            likeButton = itemView.findViewById(R.id.likeButton);
            commentButton = itemView.findViewById(R.id.commentButton);
            commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
            commentsView = itemView.findViewById(R.id.commentsView);
            closeCommentsButton = itemView.findViewById(R.id.closeCommentsButton);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            btnGoToUrl = itemView.findViewById(R.id.btnGoToUrl);
            areCommentsVisible = false;
        }

        public void playVideoFromAssets(String fileName) {
            // Crea un archivo temporal en el directorio de caché
            File tempFile = new File(itemView.getContext().getCacheDir(), fileName);

            // Copia el archivo de video desde assets a la ubicación temporal
            try (InputStream input = itemView.getContext().getAssets().open(fileName);
                 FileOutputStream output = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(itemView.getContext(), "Error al cargar el video", Toast.LENGTH_SHORT).show();
                return; // Sale del método si hay un error
            }

            // Establece el URI del archivo temporal en el VideoView
            videoView.setVideoURI(Uri.fromFile(tempFile));

            // Inicia la reproducción del video
            videoView.setOnPreparedListener(mp -> {
                if (activeVideoView != null && activeVideoView != videoView) {
                    activeVideoView.pause();
                }

                // Actualizar la referencia del video activo
                activeVideoView = videoView;

                // Iniciar reproducción del video actual
                videoView.start();
            });
        }
    }
}

