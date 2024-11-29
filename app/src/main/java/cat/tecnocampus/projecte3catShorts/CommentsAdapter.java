package cat.tecnocampus.projecte3catShorts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import cat.tecnocampus.projecte3catShorts.domain.Comentari;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Comentari> comentaris;

    public CommentsAdapter(List<Comentari> comentarios) {
        this.comentaris = comentarios;
        for (Comentari comentario : comentarios) {
            System.out.println(comentario.getUsuari());
            System.out.println(comentario.getContingut());
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comentari comentario = comentaris.get(position);
        holder.userTextView.setText(comentario.getUsuari());
        holder.commentTextView.setText(comentario.getContingut());
    }

    @Override
    public int getItemCount() {
        return comentaris.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView, commentTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.userTextView);
            commentTextView = itemView.findViewById(R.id.commentTextView);
        }
    }
}

