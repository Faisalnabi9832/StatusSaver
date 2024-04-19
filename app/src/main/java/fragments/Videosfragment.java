package fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.R;
import com.example.statussaver.VideoscreenActivity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapter.statusvideoadapter;

public class Videosfragment extends Fragment {

    private static final int REQUEST_CODE = 2;
    private RecyclerView recyclerView;
    private statusvideoadapter videoAdapter;

    public Videosfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videosfragment, container, false);

        recyclerView = rootView.findViewById(R.id.videoRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2)); // Grid layout with 2 columns

        // Retrieve WhatsApp status video paths
        List<String> videoPaths = getVideoPaths();

        if (videoPaths.isEmpty()) {
            // Show toast message indicating no videos found
            Toast.makeText(getActivity(), "No videos found", Toast.LENGTH_SHORT).show();
        } else {
            // Initialize the adapter
            videoAdapter = new statusvideoadapter(videoPaths, getActivity(), new statusvideoadapter.VideoClickListener() {
                @Override
                public void onVideoClicked(String videoPath) {
                    // Start the videoscreen activity with the clicked video path
                    Intent intent = new Intent(getActivity(), VideoscreenActivity.class);
                    intent.putExtra("videoPath", videoPath);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(videoAdapter);
        }

        return rootView;
    }

    private List<String> getVideoPaths() {
        List<String> videoPaths = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File statusDir = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/.Statuses");
            Log.d("Videosfragment", "Video paths: " + videoPaths.toString());

            File[] files = statusDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory() && isVideoFile(file)) {
                        videoPaths.add(file.getAbsolutePath());
                    }
                }
            }
        } else {
            // Request READ_EXTERNAL_STORAGE permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
        return videoPaths;
    }

    private boolean isVideoFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp4") || name.endsWith(".avi") || name.endsWith(".mov") || name.endsWith(".3gp");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission is granted, reload the RecyclerView with videos
                List<String> videoPaths = getVideoPaths();
                videoAdapter.setVideoPaths(videoPaths);
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
