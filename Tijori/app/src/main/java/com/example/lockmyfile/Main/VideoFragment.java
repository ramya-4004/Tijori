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

public class VideoFragment extends Fragment {

    private List<FileDetails> videoFiles;
    RecyclerView recyclerView;

    RecyclerAdapter adapter;
    GridLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View videoView = inflater.inflate(R.layout.fragment_video, container, false);

        recyclerView = videoView.findViewById(R.id.video_recycler_view);

        manager = new GridLayoutManager(getContext(), 4);

        return videoView;
    }

    public void updateUI() {

        videoFiles = ((MainActivity) getActivity()).getVideos();
        adapter = new RecyclerAdapter(getContext(), videoFiles, 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        Log.i(VideoFragment.class.getName(), String.valueOf(videoFiles.size()));
    }
}