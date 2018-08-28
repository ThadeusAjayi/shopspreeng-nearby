package com.shopspreeng.nearby.ads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shopspreeng.nearby.R;

import java.util.ArrayList;

/**
 * Created by Thadeus-APMIS on 8/26/2018.
 */

public class AdsAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Ads> adsList;
    private AdsClickListener mAdsClickListener;

    AdsAdapter(Context context, AdsClickListener adsClickListener) {
        mContext = context;
        mAdsClickListener = adsClickListener;
        adsList = new ArrayList<>();
    }

    void addAds(ArrayList<Ads> adsList) {
        this.adsList = adsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return adsList.size();
    }

    @Override
    public Object getItem(int i) {
        return adsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.ad_item, viewGroup, false);
        }

        final Ads ads = adsList.get(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdsClickListener.onAdsClick(ads);
            }
        });

        TextView adsTitle = convertView.findViewById(R.id.ad_title);
        TextView adsPreview = convertView.findViewById(R.id.ad_preview_text);
        ImageView adImage = convertView.findViewById(R.id.ad_image);

        adsTitle.setText(ads.getAdTitle());
        adsPreview.setText(ads.getAdBody());
        if (ads.getAdImage() == 0) {
            adImage.setVisibility(View.GONE);
        } else {
            adImage.setImageResource(ads.getAdImage());
        }


        return convertView;
    }

    interface AdsClickListener {
        void onAdsClick (Ads ads);
    }

}
