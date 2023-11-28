package com.example.smartirrigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.smartirrigation.data.ReadingData;


import java.util.ArrayList;
import java.util.List;

public class ItemListRecyclerViewAdapter extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private List<ReadingData> itemList;
    private List<ReadingData> filterItemList;
    private String userRole;
    //private ItemListItemClickListener mListener;
    private ImageLoader mImageLoader;

    public ItemListRecyclerViewAdapter(Context mContext,List<ReadingData> items) {
        this.mContext = mContext;
       // this.userRole = userRole;
        this.itemList = items;
        this.filterItemList = items;
        //this.mListener = listener;

    }

    public void setUsersMenuList(List<ReadingData> items) {
        this.itemList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.listitem_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dd = filterItemList.get(position).getDd();
        String mm = filterItemList.get(position).getMm();
        String yy = filterItemList.get(position).getYy();

        String soilvalue = filterItemList.get(position).getSoilvalue();
        String tempvalue = filterItemList.get(position).getTempvalue();
        String humidityvalue = filterItemList.get(position).getHumidityvalue();
        String rainvalue = filterItemList.get(position).getRainvalue();

        holder.setDate(dd+"-"+mm+"-"+yy);
        holder.setSoil("Moisture:"+soilvalue);
        holder.setTemp("Temp:"+tempvalue);
        holder.setHumidity("Humidity:"+humidityvalue);
        holder.setRain("Rain:"+rainvalue);


    }

    @Override
    public int getItemCount() {
        return filterItemList == null || filterItemList.isEmpty() ? 0 : filterItemList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterItemList = itemList;
                } else {
                    List<ReadingData> filteredList = new ArrayList<>();
                    for (ReadingData row : itemList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (
                                row.getDd().toLowerCase().contains(charString.toLowerCase())
                                ) {
                            filteredList.add(row);
                        }
                    }
                    filterItemList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterItemList = (ArrayList<ReadingData>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date, soil,temp,humidity,rain;


        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.date = (TextView) itemView.findViewById(R.id.txt_date);
            this.soil = (TextView) itemView.findViewById(R.id.txt_soil);
            this.temp = (TextView) itemView.findViewById(R.id.txt_temp);
            this.humidity = (TextView) itemView.findViewById(R.id.txt_humidity);
            this.rain = (TextView) itemView.findViewById(R.id.txt_rain);
        }

        public View getView() {
            return view;
        }


        public TextView getDate() {
            return date;
        }

        public void setDate(String dt) {
            this.date.setText(dt);
        }

        public void setSoil(String soil) {
            this.soil.setText(soil);
        }

        public TextView getSoil() {
            return soil;
        }


        public TextView getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp.setText(temp);
        }

        public TextView getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity.setText(humidity);
        }

        public TextView getRain() {
            return rain;
        }

        public void setRain(String rain) {
            this.rain.setText(rain);
        }

        public void setView(View view) {
            this.view = view;
        }
    }
}
