package com.example.lockmyfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FileDetailsAdapter extends ArrayAdapter<FileDetails> {

    public FileDetailsAdapter(@NonNull Context context, int resource, @NonNull List<FileDetails> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        FileDetails fileDetails = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.files_list, parent, false);
        }

        TextView fileNameTextView = view.findViewById(R.id.fileNameTextView);
        fileNameTextView.setText(fileDetails.getFileName());
        return view;
    }
}
