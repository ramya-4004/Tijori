package com.example.lockmyfile.Main;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lockmyfile.Files.FileDetails;
import com.example.lockmyfile.R;

import java.util.List;

public class ImageFragment extends Fragment {


    private List<FileDetails> imageFiles;
    RecyclerView recyclerView;

    RecyclerAdapter adapter;
    GridLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View imageView = inflater.inflate(R.layout.fragment_image, container, false);

        recyclerView = imageView.findViewById(R.id.image_recycler_view);
        manager = new GridLayoutManager(getContext(), 4);


        return imageView;
    }

    public void updateUI() {

        imageFiles = ((MainActivity) getActivity()).getImages();
        adapter = new RecyclerAdapter(getContext(), imageFiles, 1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        Log.i(ImageFragment.class.getName(), String.valueOf(imageFiles.size()));
    }


}