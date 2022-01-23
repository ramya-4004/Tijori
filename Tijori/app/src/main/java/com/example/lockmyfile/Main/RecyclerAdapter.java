package com.example.lockmyfile.Main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lockmyfile.Files.FileDetails;
import com.example.lockmyfile.R;
import com.example.lockmyfile.ViewFiles.ViewImagesActivity;
import com.example.lockmyfile.ViewFiles.ViewPdfActivity;
import com.example.lockmyfile.ViewFiles.ViewVideoActivity;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // creating a variable for our context and array list.
    private final Context context;

    //list of files inside the app
    private List<FileDetails> filesPathArrayList;

    // file type
    private int fileType;

    public RecyclerAdapter(Context context, List<FileDetails> filesPathArrayList, int fileType) {
        this.context = context;
        this.filesPathArrayList = filesPathArrayList;
        this.fileType = fileType;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RecyclerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.files_list, parent, false));
    }

    // function to  find which activity to start according to type
    private Intent getIntent(){
        Intent intent = null;

        switch (fileType){
            case 1:
                intent = new Intent(context, ViewImagesActivity.class);
                break;

            case 2:
                intent = new Intent(context, ViewVideoActivity.class);
                break;

            case 3:
                intent = new Intent(context, ViewPdfActivity.class);

        }
        return intent;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        TextView textView = myViewHolder.textView;
        textView.setText(filesPathArrayList.get(position).getFileName());

        FileDetails fileDetails = filesPathArrayList.get(position);

        final File file = new File(fileDetails.getFileName());

        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("Path", file.getPath());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filesPathArrayList.size();
    }


    // View Holder Class to handle images in Recycler View.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private final TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            textView = itemView.findViewById(R.id.files_text_view);
        }
    }
}
