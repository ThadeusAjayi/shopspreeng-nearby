package com.shopspreeng.nearby.hotspot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shopspreeng.nearby.R;

import java.util.ArrayList;

/**
 * Created by Thadeus-APMIS on 8/25/2018.
 */

public class HotspotAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Hotspot> hotspotList;
    private HotspotClickListener hotspotClickListener;

    HotspotAdapter (Context context, HotspotClickListener hotspotClickListener) {
        mContext = context;
        this.hotspotClickListener = hotspotClickListener;
        hotspotList = new ArrayList<>();
    }

    public void addHotspot (ArrayList<Hotspot> hotspotList) {
        this.hotspotList = hotspotList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return hotspotList.size();
    }

    @Override
    public Object getItem(int i) {
        return hotspotList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.hotspot_item, viewGroup, false);
        }

        final Hotspot hotspot = hotspotList.get(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotspotClickListener.onHotspotClick(hotspot);
            }
        });


        TextView hotspotName = convertView.findViewById(R.id.hotspot_name);
        TextView hotspotCreated = convertView.findViewById(R.id.hotspot_created);
        TextView hotspotMembers = convertView.findViewById(R.id.hotspot_member);

        hotspotName.setText(mContext.getString(R.string.hotspot_name, hotspot.getHotspotName()));
        hotspotCreated.setText(mContext.getString(R.string.created, hotspot.getCreatedAt()));
        hotspotMembers.setText(mContext.getString(R.string.hotspot_members, String.valueOf(hotspot.getHotspotMembers())));


        return convertView;
    }

    interface HotspotClickListener {
        void onHotspotClick (Hotspot hotspot);
    }

}
