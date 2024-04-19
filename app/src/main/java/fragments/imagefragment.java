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
import com.example.statussaver.imagescreen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapter.statusimageadapter;

public class imagefragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private statusimageadapter imageAdapter;

    public imagefragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imagefragment, container, false);

        recyclerView = rootView.findViewById(R.id.imageRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2)); // Grid layout with 2 columns

        // Retrieve WhatsApp status image paths
        List<String> imagePaths = getImagePaths();
        // Initialize the adapter
        imageAdapter = new statusimageadapter(imagePaths, getActivity(), new statusimageadapter.ImageClickListener() {
            @Override
            public void onImageClicked(String imagePath) {
                // Start the imagescreen activity with the clicked image path
                Intent intent = new Intent(getActivity(), imagescreen.class);
                intent.putExtra("imagePath", imagePath);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(imageAdapter);
        return rootView;
    }

    private List<String> getImagePaths() {
        List<String> imagePaths = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File statusDir = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/.Statuses");
            Log.d("imagefragment", "Image paths: " + imagePaths.toString());

            File[] files = statusDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory() && isImageFile(file)) {
                        imagePaths.add(file.getAbsolutePath());
                    }
                }
            }
        } else {
            // Request READ_EXTERNAL_STORAGE permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
        return imagePaths;
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission is granted, reload the RecyclerView with images
                List<String> imagePaths = getImagePaths();
                imageAdapter.setImagePaths(imagePaths);
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}