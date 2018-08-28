package com.shopspreeng.nearby.hotspot;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shopspreeng.nearby.R;
import com.shopspreeng.nearby.utils.NearbyUtils;

import java.util.ArrayList;

public class HotspotFragment extends Fragment implements HotspotAdapter.HotspotClickListener {

    HotspotAdapter hotspotAdapter;
    ListView hotspotListView;
    ArrayList<Hotspot> hotspotList;
    ViewGroup emptyView;
    ViewGroup searchingView;
    FloatingActionButton searchHotspot;
    TextView searchMessageTextView;
    FloatingActionButton createHotspot;

    //Nearby Functions
    NearbyUtils nearbyUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotspot, container, false);
        hotspotListView = rootView.findViewById(R.id.hotspot_list);
        emptyView = rootView.findViewById(R.id.empty_view);
        searchingView = rootView.findViewById(R.id.searching_view);
        searchHotspot = rootView.findViewById(R.id.search_hotspot);
        searchMessageTextView = rootView.findViewById(R.id.search_message);
        createHotspot = rootView.findViewById(R.id.create_hotspot);

        nearbyUtils = new NearbyUtils(getActivity()).getInstance();


        hotspotList = new ArrayList<>();
        hotspotList.add(new Hotspot("Thadeus", "20, minutes ago", 25));
        hotspotList.add(new Hotspot("APMIS", "1, hour ago", 5));
        hotspotList.add(new Hotspot("HOME", "40, secs ago", 9));
        hotspotList.add(new Hotspot("General", "4, minutes ago", 50));

        hotspotAdapter = new HotspotAdapter(getContext(), this);
        hotspotListView.setAdapter(hotspotAdapter);
        hotspotListView.setEmptyView(emptyView);

        searchHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyView.setVisibility(View.GONE);
                hotspotListView.setEmptyView(searchingView);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (hotspotList != null) {
                            hotspotAdapter.addHotspot(hotspotList);
                        } else {
                            searchMessageTextView.setText("No Hotspots found, create or search again");
                            hotspotListView.setEmptyView(emptyView);
                            searchingView.setVisibility(View.GONE);
                        }
                    }
                }, 5000);
            }
        });

        createHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO create hotspot process launches
                //This line below is for clients that are allowed to advertise
                nearbyUtils.startAdvertising("me");
                //TODO create method to handle clients that can create hotspots but can't advertise
            }
        });


        return rootView;
    }

    @Override
    public void onHotspotClick(Hotspot hotspot) {
        Toast.makeText(getActivity(), hotspot.toString(), Toast.LENGTH_SHORT).show();
    }
}
