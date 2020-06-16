package com.example.mobdev;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

/**
 * Provides UI for the view with Cards.
 */
public class MatchesFragment extends Fragment {
    private SettingsViewModel svm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        svm = new ViewModelProvider(this).get(SettingsViewModel.class);
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

        }



    }

    public static boolean isDistanceLessThanTenMiles(double lat1,
                                                     double lat2, double lon1,
                                                     double lon2, double searchRadius)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 3956;
        double g = 0;
        g = r*c;

        // calculate the result
        return(c * r) < searchRadius ;
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private FirebaseTodoViewModel vm;
        private String [] names;
        private String [] images;
        ArrayList<TodoItem> todoItems;

        LocationManager locationManager;
        double longitudeNetwork, latitudeNetwork;

        private boolean checkLocation() {
            if(!isLocationEnabled()) {
                showAlert();
            }
            return isLocationEnabled();
        }

        private boolean isLocationEnabled() {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        private void showAlert() {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle(R.string.enable_location)
                    .setMessage(R.string.location_message)
                    .setPositiveButton(R.string.location_settings, (paramDialogInterface, paramInt) -> {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    })
                    .setNegativeButton(R.string.location_cancel, (paramDialogInterface, paramInt) -> {});
            dialog.show();
        }

        public void toggleNetworkUpdates() {
            if(!checkLocation()) {
                return;
            }
            else if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 1000, 10, locationListenerNetwork);
                    Toast.makeText(getContext(), R.string.network_provider_started_running, Toast.LENGTH_LONG).show();
                }
            }

        private final LocationListener locationListenerNetwork = new LocationListener() {
            String email;

            public void onLocationChanged(Location location) {
                longitudeNetwork = location.getLongitude();
                latitudeNetwork = location.getLatitude();
                vm = new FirebaseTodoViewModel();
                // Create the observer which updates the UI.

                final Observer<List<com.example.mobdev.Settings>> getSettingsObserver = newSettings -> {
                    if (newSettings == null || newSettings.size() <= 0) {
                        return;
                    }

                    com.example.mobdev.Settings settings = newSettings.get(0);

                    if (settings == null) {
                        return;
                    }
                    double searchRadius = Double.parseDouble(settings.getMaximumDistanceSearch());
                    vm.getTodoItems((items) -> {
                        todoItems = new ArrayList<>();
                        for (int i = 0; i < items.size(); i++) {
                            if (MatchesFragment.isDistanceLessThanTenMiles(Double.parseDouble(items.get(i).getLat()), latitudeNetwork,Double.parseDouble(items.get(i).getLongitude()), longitudeNetwork, searchRadius)) {
                                todoItems.add(items.get(i));
                            }
                        }
                        names = new String[todoItems.size()];
                        images = new String[todoItems.size()];
                        for (int i = 0; i < getItemCount(); i++) {
                            names[i] = todoItems.get(i).getName();
                            images[i] = todoItems.get(i).getImageUrl();
                        }
                        notifyDataSetChanged();
                    });
                };

                Bundle b = getArguments();

                if (b != null) {

                    if (b.containsKey(Constants.KEY_EMAIL)) {
                        email = b.getString(Constants.KEY_EMAIL);
                    }
                }
                String[] settingsIds = {email};
                svm.loadAllByIds(getContext(), settingsIds).observe(getViewLifecycleOwner(), getSettingsObserver );

            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };


        public ContentAdapter(Context context) {

                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                toggleNetworkUpdates();
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
                         todoToUpdate.setLiked(!todoToUpdate.getLiked());
                         vm.updateTodoItem(todoToUpdate);
                }
            });
        }
        @Override
        public int getItemCount() {
            if(todoItems!=null)
            {
                return todoItems.size();
            }
            return 0;
        }
    }
}