package com.example.smartirrigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.smartirrigation.utils.AppConstants;
import com.example.smartirrigation.utils.ResponseData;
import com.example.smartirrigation.volley.JsonRequest;
import com.example.smartirrigation.volley.VolleySingleton;
import com.google.gson.Gson;

public class ReadingListActivity extends AppCompatActivity {
    private RecyclerView rc_itemList;
    private ItemListRecyclerViewAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list);

        rc_itemList = findViewById(R.id.rc_ItemList);
        //initToolbar();
        loadItemListData();
    }
    private void loadItemListData() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView
                .VERTICAL, false);
        rc_itemList.setLayoutManager(manager);


        JsonRequest ItemListRequest = new JsonRequest(
                Request.Method.GET,
                AppConstants.READING_HISTORY_DATA, null,
                response -> {
                    Gson gson = new Gson();
                    ResponseData responseData = gson.fromJson(response.toString(), ResponseData.class);
                    //delayedProgressDialog.cancel();
                    if (responseData.getMessage().equals(AppConstants.RESPONSE_SUCCESS)) {
                        itemListAdapter = new ItemListRecyclerViewAdapter(ReadingListActivity.this, responseData.getDataList());
                        rc_itemList.setAdapter(itemListAdapter);
                    } else {
                        Toast.makeText(ReadingListActivity.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    //delayedProgressDialog.cancel();
                    Toast.makeText(ReadingListActivity.this, VolleySingleton.getErrorMessage(error), Toast.LENGTH_LONG).show();
                });
        ItemListRequest.setRetryPolicy(VolleySingleton.getDefaultRetryPolice());
        ItemListRequest.setShouldCache(false);
        VolleySingleton.getInstance().addToRequestQueue(ItemListRequest, "ItemListRequest");
    }
}