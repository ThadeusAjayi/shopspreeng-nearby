package com.shopspreeng.nearby.ads;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.shopspreeng.nearby.R;
import com.shopspreeng.nearby.utils.NearbyUtils;

import java.util.ArrayList;

public class AdsFragment extends Fragment implements AdsAdapter.AdsClickListener{

    ListView adsListView;
    ArrayList<Ads> adsList;
    AdsAdapter adsAdapter;
    TextView emptyStateText;
    ViewGroup emptyView;
    ProgressBar progressBar;
    ImageView failedImage;

    //Nearby functions
    NearbyUtils nearbyUtils;
    MessageListener messageListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ads, container, false);
        adsListView = rootView.findViewById(R.id.ad_list);
        emptyStateText = rootView.findViewById(R.id.empty_state_text);
        emptyView = rootView.findViewById(R.id.empty_view);
        progressBar = rootView.findViewById(R.id.empty_progress);
        failedImage = rootView.findViewById(R.id.failed_image);

        adsList = new ArrayList<>();

        nearbyUtils = new NearbyUtils(getActivity()).getInstance();
        nearbyUtils.startDiscovery();
        messageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                Log.d("Found message", "");
            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
            }
        };

        adsList.add(new Ads("Peppa Pig", "this is the ad preview, this it the ad preview, this it the add preview, this it the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview this is the ad preview"));
        adsList.add(new Ads("M & S", "this is the ad preview, this it the ad preview, this it the add preview, this it the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview this is the ad preview", R.drawable.ic_members));
        adsList.add(new Ads("M & S", "this is the ad preview, this it the ad preview, this it the add preview, this it the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview, this is the ad preview this is the ad preview", R.mipmap.ic_members));


        adsAdapter = new AdsAdapter(getActivity(), this);
        adsListView.setAdapter(adsAdapter);
        adsListView.setEmptyView(emptyView);

        //TODO while searching for nearby filter results that have nearby ID uniquely

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adsList != null) {
                    adsAdapter.addAds(adsList);
                } else {
                    //TODO hide progress bar and show failed image
                    progressBar.setVisibility(View.GONE);
                    failedImage.setVisibility(View.VISIBLE);
                    emptyStateText.setText("Sorry, ShopspreeNg is not nearby !!! \n Make sure we've marked this place");
                }
            }
        }, 5000);



        return rootView;
    }

    @Override
    public void onAdsClick(Ads ads) {
        Toast.makeText(getActivity(), ads.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Nearby.getMessagesClient(getActivity()).subscribe(messageListener);
    }

    @Override
    public void onStop() {
        Nearby.getMessagesClient(getActivity()).unsubscribe(messageListener);
        super.onStop();
    }
}
