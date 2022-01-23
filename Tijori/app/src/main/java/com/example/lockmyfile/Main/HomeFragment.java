package com.example.lockmyfile.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.lockmyfile.Files.FileChooserFragment;
import com.example.lockmyfile.PrivateBrowser.BrowserActivity;
import com.example.lockmyfile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    // Floating Action Button to lock a file
    FloatingActionButton addFab;

    // fragment to check permission and browse through files
    FileChooserFragment fragment;

    // ImageView to start Web browser
    ImageView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        webView = view.findViewById(R.id.openWebImageView);

        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BrowserActivity.class));
            }
        });

        addFab = view.findViewById(R.id.add_fab);


        FragmentManager manager = getActivity().getSupportFragmentManager();
        fragment = (FileChooserFragment) manager.findFragmentById(R.id.fragment_fileChooser);

        addFab.setOnClickListener(addAFile);

        return view;
    }

    // Setting on click listener for the button
    // asking all permissions here
    View.OnClickListener addAFile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean status = fragment.getFragmentWorking();

            if(!status){
                Toast.makeText(getContext().getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    };
}