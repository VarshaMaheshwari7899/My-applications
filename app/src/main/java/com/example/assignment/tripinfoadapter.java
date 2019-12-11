package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class tripinfoadapter extends RecyclerView.Adapter<tripinfoadapter.ViewHolder> {

    Context context;
    private List<tripinfo> tripinfoList;


    public tripinfoadapter(Context context, List<tripinfo> tripinfoList) {
        this.context = context;
        this.tripinfoList = tripinfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.triplistlayout,parent,false);
        return new tripinfoadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String startLocation = tripinfoList.get(position).getStartLocation();
        String startLocationTime = tripinfoList.get(position).getStartLocationTime();
        String EndLocation = tripinfoList.get(position).getEndLocation();
        String EndLocationTime = tripinfoList.get(position).getEndLocationTime();
        String Amt = tripinfoList.get(position).getAmt();
        String CurrencySymbol = tripinfoList.get(position).getCurrency();
        String Traveltime = tripinfoList.get(position).getTotalTime();

        holder.setStartLocation(startLocation);
        holder.setStartLocationTime(startLocationTime);
        holder.setEndLocation(EndLocation);
        holder.setEndLocationTime(EndLocationTime);
        holder.setCurrencySymbol(CurrencySymbol);
        holder.setTotalAmt(Amt);
        holder.setTraveltime(Traveltime);

    }

    @Override
    public int getItemCount() {
        return tripinfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView StartLocation,StartLocationTime,EndLocation,EndLocationTime,TotalAmt,Traveltime,CurrencySymbol;



        public void setStartLocation(String startLocation) {
            StartLocation.setText(startLocation);
        }


        public void setStartLocationTime(String startLocationTime) {
            StartLocationTime.setText(startLocationTime);
        }



        public void setEndLocation(String endLocation) {
            EndLocation.setText(endLocation);
        }



        public void setEndLocationTime(String endLocationTime) {
            EndLocationTime.setText(endLocationTime);
        }



        public void setTotalAmt(String totalAmt) {
            TotalAmt.setText(totalAmt);
        }



        public void setTraveltime(String traveltime) {
            Traveltime.setText("Travel Time : " + traveltime);
        }



        public void setCurrencySymbol(String currencySymbol) {
            CurrencySymbol.setText(currencySymbol);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            StartLocation = itemView.findViewById(R.id.StartLocation);
            StartLocationTime = itemView.findViewById(R.id.StartLocationTime);
            EndLocation = itemView.findViewById(R.id.endLocation);
            EndLocationTime = itemView.findViewById(R.id.endLocationtime);
            TotalAmt = itemView.findViewById(R.id.TravelAmt);
            Traveltime = itemView.findViewById(R.id.TravelTime);
            CurrencySymbol = itemView.findViewById(R.id.TravelAmtSymbol);

        }
    }
}
