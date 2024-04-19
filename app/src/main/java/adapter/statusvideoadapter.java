package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.statussaver.R;

import java.util.List;

public class statusvideoadapter extends RecyclerView.Adapter<statusvideoadapter.VideoViewHolder> {

    private List<String> videoPaths;
    private Context context;
    private VideoClickListener listener;

    public statusvideoadapter(List<String> videoPaths, Context context, VideoClickListener listener) {
        this.videoPaths = videoPaths;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String videoPath = videoPaths.get(position);
        // Display a placeholder image for the video thumbnail
        Glide.with(context)
                .load(videoPath)
                .into(holder.videoThumbnail);

        // Set OnClickListener for the video item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onVideoClicked(videoPath); // Pass the clicked video path
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoPaths.size();
    }

    public interface VideoClickListener {
        void onVideoClicked(String videoPath);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
        }
    }

    public void setVideoPaths(List<String> videoPaths) {
        this.videoPaths = videoPaths;
        notifyDataSetChanged();
    }
}
