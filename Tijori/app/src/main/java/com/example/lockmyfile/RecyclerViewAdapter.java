package com.example.lockmyfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.CancellationSignal;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // creating a variable for our context and array list.
    private final Context context;

    //list of files inside the app
    private List<FileDetails> filesPathArrayList;

    //List with imageFile extensions
    List<String> imageExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");

    //List with videoFile extensions
    List<String> videoExtensions = Arrays.asList("mp4", "mov", "wmv", "avi", "mkv", "mpeg-2");

    // String to store pdf extension
    private final String pdfExtension = "pdf";

    /**
     * 1 -> imageFile
     * 2 -> videoFile
     * 3 -> pdfFile
     */

    @Override
    public int getItemViewType(int position) {
        String extension = FilenameUtils.getExtension(filesPathArrayList.get(position).getFileName());
        int type = -1;
        if(imageExtensions.contains(extension))
            type = 1;
        else if(videoExtensions.contains(extension))
            type = 2;
        else if(pdfExtension.equals(extension))
            type =  3;

        return type;
    }

    // on below line we have created a constructor.
    public RecyclerViewAdapter(Context context, List<FileDetails> filesPathArrayList) {
        this.context = context;
        this.filesPathArrayList = filesPathArrayList;
    }

    public void setData(List<FileDetails> data) {
        filesPathArrayList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.files_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        ImageView view = recyclerViewHolder.imageIV;
        int height = view.getMaxHeight();
        int width = view.getMaxWidth();
        Bitmap thumbImage;

        int fileType = holder.getItemViewType();

        final File file = new File(filesPathArrayList.get(position).getFileName());
        try {
            switch (fileType) {
                case 1:
                    Glide.with(context).load(file).into(view);
                    thumbImage = ThumbnailUtils.createImageThumbnail(file, new Size(height, width), new CancellationSignal());
                    view.setImageBitmap(thumbImage);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // inside on click listener we are creating a new intent
                            Intent imageIntent = new Intent(context, ViewImagesActivity.class);

                            // on below line we are passing the image path to our new activity.
                            imageIntent.putExtra("imgPath", file.getPath());

                            // at last we are starting our activity.
                            context.startActivity(imageIntent);
                        }
                    });
                    break;

                case 2:
                    Glide.with(context).load(file).into(view);
                    view.setForeground(ContextCompat.getDrawable(context, R.drawable.ic_play));

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // intent to the ViewVideo activity
                            Intent videoIntent = new Intent(context, ViewVideoActivity.class);

                            // sending the path of corresponding video file to the intent
                            videoIntent.putExtra("videoPath", file.getPath());

                            // start the ViewVideoActivity
                            context.startActivity(videoIntent);
                        }
                    });
                    break;

                case 3:
                    view.setImageResource(R.raw.pdf_icon);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // inside on click listener we are creating a new intent
                            Intent pdfIntent = new Intent(context, ViewPdfActivity.class);

                            // on below line we are passing the image path to our new activity.
                            pdfIntent.putExtra("pdfPath", file.getPath());

                            // at last we are starting our activity.
                            context.startActivity(pdfIntent);
                        }
                    });
                    break;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        // this method returns
        // the size of recyclerview
        return filesPathArrayList.size();
    }

    // View Holder Class to handle images in Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private final ImageView imageIV;

        // variable to hold type of file (Image, Video or PDF)
        int fileType;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            imageIV = itemView.findViewById(R.id.idIVImage);
        }
    }
}
