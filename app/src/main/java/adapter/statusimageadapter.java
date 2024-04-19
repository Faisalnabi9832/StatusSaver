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

public class statusimageadapter extends RecyclerView.Adapter<statusimageadapter.ImageViewHolder> {

    private List<String> imagePaths;
    private Context context;
    private ImageClickListener listener;

    public statusimageadapter(List<String> imagePaths, Context context, ImageClickListener listener) {
        this.imagePaths = imagePaths;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imagePath = imagePaths.get(position);
        Glide.with(context)
                .load(imagePath)
                .centerCrop()
                .into(holder.imageView);

        // Set OnClickListener for the ImageView
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onImageClicked(imagePath);
                }
            }
        });

        // Set OnClickListener for the download button
        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onImageClicked(imagePath);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    public interface ImageClickListener {
        void onImageClicked(String imagePath);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView downloadBtn;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.statusImage);
            downloadBtn = itemView.findViewById(R.id.downloadText);
        }
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
        notifyDataSetChanged();
    }
}