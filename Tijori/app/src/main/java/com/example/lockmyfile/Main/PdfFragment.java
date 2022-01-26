package com.example.lockmyfile.Main;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lockmyfile.Files.FileDetails;
import com.example.lockmyfile.R;

import java.util.List;

public class PdfFragment extends Fragment {

    private List<FileDetails> pdfFiles;
    RecyclerView recyclerView;

    RecyclerAdapter adapter;
    LinearLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pdfView = inflater.inflate(R.layout.fragment_pdf, container, false);
        recyclerView = pdfView.findViewById(R.id.pdf_recycler_view);

        manager = new LinearLayoutManager(getContext());

        return pdfView;
    }

    public void updateUI() {

        pdfFiles = ((MainActivity) getActivity()).getPdf();
        adapter = new RecyclerAdapter(getContext(), pdfFiles, 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        Log.i(PdfFragment.class.getName(), String.valueOf(pdfFiles.size()));
    }
}