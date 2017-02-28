package com.byteshaft.laundryadmin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.byteshaft.laundryadmin.AppGlobals;
import com.byteshaft.laundryadmin.R;
import com.byteshaft.laundryadmin.utils.CustomExpandableSimple;
import com.byteshaft.laundryadmin.utils.Data;
import com.byteshaft.requests.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by s9iper1 on 2/21/17.
 */

public class Express extends Fragment implements HttpRequest.OnReadyStateChangeListener {

    private View mBaseView;
    public ArrayList<Data> arrayList;
    public CustomExpandableSimple listAdapter;
    public ExpandableListView expListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.express, container, false);
        expListView = (ExpandableListView) mBaseView.findViewById(R.id.un_approved_list);
        getUnApproved();
        return mBaseView;
    }

    private void getUnApproved() {
        HttpRequest mRequest = new HttpRequest(AppGlobals.getContext());
        mRequest.setOnReadyStateChangeListener(this);
        mRequest.open("GET", String.format("http://178.62.87.25/api/laundry/requests/filter?" +
                "laundry_type=express", ""));
        String token = AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_TOKEN);
        mRequest.setRequestHeader("Authorization", "Token " + token);
        mRequest.send();
    }

    @Override
    public void onReadyStateChange(HttpRequest request, int readyState) {
        switch (readyState) {
            case HttpRequest.STATE_DONE:
                try {
                    JSONArray jsonArray = new JSONArray(request.getResponseText());
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.i("TAG", "added " + jsonObject);
                        Data data = new Data();
                        data.setId(jsonObject.getInt("id"));
                        data.setServiceDone(jsonObject.getBoolean("service_done"));
                        JSONObject jsonObjectAddress = jsonObject.getJSONObject("address");
                        JSONArray serviceItems = jsonObject.getJSONArray("service_items");
                        data.setCreatedTime(jsonObject.getString("pickup_time"));
                        data.setDropTime(jsonObject.getString("drop_time"));
                        data.setApprovedForProcessing(jsonObject.getBoolean("approved_for_processing"));
                        data.setPickUpAddress(jsonObjectAddress.getString("pickup_street") + " "+
                                jsonObjectAddress.getString("pickup_city") +  " "+
                                jsonObjectAddress.getString("pickup_zip"));
                        data.setDropAddress(jsonObjectAddress.getString("drop_street") + " "+
                                jsonObjectAddress.getString("drop_city") + " "+
                                jsonObjectAddress.getString("drop_zip"));
                        data.setDropHouseNumber(jsonObjectAddress.getString("drop_house_number"));
                        data.setHouseNumber(jsonObjectAddress.getString("pickup_house_number"));
                        data.setDropOnPickLocation(jsonObjectAddress.getBoolean("drop_on_pickup_location"));
                        Log.i("TAG", "id " + jsonObject.getInt("id")+ " drop on pick" + jsonObjectAddress.getBoolean("drop_on_pickup_location"));
                        data.setLocation(jsonObjectAddress.getString("location"));
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < serviceItems.length(); j++) {
                            JSONObject itemDetails = serviceItems.getJSONObject(j);
                            stringBuilder.append(itemDetails.getString("name"));
                            stringBuilder.append(" (");
                            stringBuilder.append(itemDetails.getString("quantity") +")");
                            if (j+1 < serviceItems.length()) {
                                stringBuilder.append(" , ");
                            }
                        }
                        data.setOrderDetail(stringBuilder.toString());
                        arrayList.add(data);
                    }
                    listAdapter = new CustomExpandableSimple(AppGlobals.getContext(),
                            arrayList);
                    listAdapter.notifyDataSetChanged();
                    expListView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
