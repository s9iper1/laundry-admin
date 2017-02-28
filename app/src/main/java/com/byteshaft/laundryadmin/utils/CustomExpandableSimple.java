package com.byteshaft.laundryadmin.utils;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.byteshaft.laundryadmin.AppGlobals;
import com.byteshaft.laundryadmin.R;
import com.byteshaft.laundryadmin.WebServiceHelpers;
import com.byteshaft.laundryadmin.fragments.MapActivity;
import com.byteshaft.requests.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by shahid on 04/01/2017.
 */

public class CustomExpandableSimple extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Data> mItems;

    public CustomExpandableSimple(Context context, ArrayList<Data> items) {
        mContext = context;
        mItems = items;
    }

    static class ViewHolder {
        TextView headerTextView;
        ImageView collapseExpandIndicator;
    }

    static class SubItemsViewHolder {
        TextView pickUpAddress;

        TextView pickupLocation;

        // drop textViews
        TextView dropAddress;
        TextView dropLocation;
        RelativeLayout relativeLayout;

    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final SubItemsViewHolder holder;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_delegate, null);
            holder = new SubItemsViewHolder();

            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.drop_layout);
            // pickup textViews
            holder.pickUpAddress = (TextView) convertView.findViewById(R.id.pickup_city);
            holder.pickupLocation = (TextView) convertView.findViewById(R.id.pickup_location);

            // drop textViews
            holder.dropAddress = (TextView) convertView.findViewById(R.id.drop_city);
            holder.dropLocation = (TextView) convertView.findViewById(R.id.drop_location);
            convertView.setTag(holder);
        } else {
            holder = (SubItemsViewHolder) convertView.getTag();
        }
        Data data = (Data) getChild(groupPosition, childPosition);
        JSONObject addressJsonObject = data.getAddress();
        try {
            holder.pickUpAddress.setText("Pickup Address: \n" + addressJsonObject.getString("pickup_house_number")
                    + " " + addressJsonObject.getString("pickup_street") + " " +
                    addressJsonObject.getString("pickup_city") + " " +
                    addressJsonObject.getString("pickup_zip"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loc = null;
            try {
                loc = addressJsonObject.getString("location");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] pickDrop = loc.split("\\|");
            String removeLatLng = pickDrop[0].replaceAll("lat/lng: ", "").replace("(", "").replace(")", "");
            String[] latLng = removeLatLng.split(",");
            final double latitude = Double.parseDouble(latLng[0]);
            final double longitude = Double.parseDouble(latLng[1]);
            SpannableString pickLocation = new SpannableString(latitude + "," + longitude);
            pickLocation.setSpan(new UnderlineSpan(), 0, pickLocation.length(), 0);
            holder.pickupLocation.setText(pickLocation);
            holder.pickupLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                mContext.startActivity(intent);
                    Intent intent = new Intent(mContext, MapActivity.class);
                    intent.putExtra("lat", latitude);
                    intent.putExtra("lng", longitude);
                    mContext.startActivity(intent);
                }
            });

        boolean dropOnPickUpLocation = false;
        try {
            dropOnPickUpLocation = addressJsonObject.getBoolean("drop_on_pickup_location");
        } catch (JSONException e) {
            e.printStackTrace();
        }
            if (!dropOnPickUpLocation) {
                holder.relativeLayout.setVisibility(View.VISIBLE);
                try {
                    holder.dropAddress.setText("Drop Address: \n" + addressJsonObject.getString("drop_house_number") + " " +
                            addressJsonObject.getString("drop_street") + " " +
                            addressJsonObject.getString("drop_city") + " " +
                            addressJsonObject.getString("drop_zip"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String replaceLatLng = pickDrop[1].replaceAll("lat/lng: ", "").replace("(", "").replace(")", "");
                String[] dropLatLng = replaceLatLng.split(",");
                final double dropLatitude = Double.parseDouble(dropLatLng[0]);
                final double dropLongitude = Double.parseDouble(dropLatLng[1]);
                SpannableString content = new SpannableString(dropLatitude + "," + dropLongitude);
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                holder.dropLocation.setText(content);
                holder.dropLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", dropLatitude, dropLongitude);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                    mContext.startActivity(intent);
                        Intent intent = new Intent(mContext, MapActivity.class);
                        intent.putExtra("lat", dropLatitude);
                        intent.putExtra("lng", dropLongitude);
                        mContext.startActivity(intent);
                    }
                });
            } else {
                holder.relativeLayout.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        notifyDataSetChanged();
    }

    private void deleteLocation(int id, final int index) {
        HttpRequest request = new HttpRequest(AppGlobals.getContext());
        request.setOnReadyStateChangeListener(new HttpRequest.OnReadyStateChangeListener() {
            @Override
            public void onReadyStateChange(HttpRequest request, int readyState) {
                switch (readyState) {
                    case HttpRequest.STATE_DONE:
                        WebServiceHelpers.dismissProgressDialog();
                        Log.i("TAG", "" + request.getStatus());
                        switch (request.getStatus()) {
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                mItems.remove(index);
                                notifyDataSetChanged();
                                break;

                        }
                }
            }
        });
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpRequest request, int readyState, short error, Exception exception) {

            }
        });
        request.open("DELETE", String.format("%suser/addresses/%s", AppGlobals.BASE_URL, id));
        request.setRequestHeader("Authorization", "Token " +
                AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_TOKEN));
        request.send();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_header_delegate_simple, null);
            viewHolder = new ViewHolder();
            viewHolder.headerTextView = (TextView) convertView.findViewById(R.id.text_view_location_header);
            viewHolder.collapseExpandIndicator = (ImageView) convertView.findViewById(R.id.image_view_location_expand_collapse);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Data data = (Data) getGroup(groupPosition);
        JSONArray jsonArray = data.getOrderDetail();
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < jsonArray.length(); j++) {
            try {
                JSONObject itemDetails = jsonArray.getJSONObject(j);
                stringBuilder.append(itemDetails.getString("name"));
                stringBuilder.append(" (");
                stringBuilder.append(itemDetails.getString("quantity") + ")");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (j + 1 < jsonArray.length()) {
                stringBuilder.append(" , ");
            }
        }
        viewHolder.headerTextView.setAllCaps(true);
        viewHolder.headerTextView.setText(stringBuilder.toString());

        if (isExpanded) {
            viewHolder.collapseExpandIndicator.setImageResource(R.mipmap.ic_collapse);
        } else {
            viewHolder.collapseExpandIndicator.setImageResource(R.mipmap.ic_expand);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}