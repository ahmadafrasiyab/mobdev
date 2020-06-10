package com.example.mobdev;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

/**
 * Provides UI for the view with Cards.
 */
public class MatchesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
       public ImageButton button;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_text);
            button = itemView.findViewById(R.id.share_button);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "You liked " + name.getText(), Toast.LENGTH_SHORT).show();
//                }
//
//            });

        }



    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private FirebaseTodoViewModel vm;
        private static final int LENGTH = 2;
        private final String[] mPlaces;
        private final String[] mPlaceDesc;
        private final Drawable[] mPlacePictures;
        private String [] names;
        private String [] images;
        ArrayList<TodoItem> todoItems = new ArrayList<>();


        public ContentAdapter(Context context) {


                vm = new FirebaseTodoViewModel();
                vm.getTodoItems((items) -> {
                todoItems = items;
                names = new String[todoItems.size()];
                images = new String[todoItems.size()];
                   for (int i=0; i < todoItems.size(); i++) {
                        names[i] = todoItems.get(i).getName();
                        images[i] = todoItems.get(i).getImageUrl();
                    }
                    notifyDataSetChanged();
                });

            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(names[position % names.length]);
            Picasso.get().load(images[position % images.length]).into(holder.picture);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Toast.makeText(v.getContext(), "You liked " + holder.name.getText(), Toast.LENGTH_SHORT).show();
                         TodoItem todoToUpdate = todoItems.get(position);
                         todoToUpdate.setLike(!todoToUpdate.getLiked());
                         vm.updateTodoItem(todoToUpdate);

                }
            });
        }
        @Override
        public int getItemCount() {
            return todoItems.size();
        }
    }
}