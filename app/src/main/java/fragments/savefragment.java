package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.R;

import java.util.ArrayList;
import java.util.List;

import adapter.statusimageadapter;

public class savefragment extends Fragment implements statusimageadapter.ImageClickListener {

    private RecyclerView recyclerView;
    private List<String> savedImagePaths; // List to hold saved image paths
    private statusimageadapter adapter; // Adapter for the RecyclerView

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedImagePaths = new ArrayList<>(); // Initialize the list
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_savefragment, container, false);
        recyclerView = rootView.findViewById(R.id.saveRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set up your adapter and RecyclerView here
        adapter = new statusimageadapter(savedImagePaths, getActivity(), this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onImageClicked(String imagePath) {
        // Add the clicked image path to the list
        savedImagePaths.add(imagePath);
        // Notify the adapter about the change
        adapter.notifyDataSetChanged();
    }
}
