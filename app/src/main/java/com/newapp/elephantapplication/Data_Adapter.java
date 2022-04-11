package com.newapp.elephantapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

public class Data_Adapter  extends  RecyclerView.Adapter<Data_Adapter.DataViewHolder>  {

    Context context ;
    ArrayList<Data_Model> list;



    public Data_Adapter(Context context, ArrayList<Data_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Data_Model data_model = list.get(position);

        holder.IdElephant.setText("ELE00001");
        holder.Daily_Log_Date.setText(data_model.getDaily_Log_Date());
        holder.f_latitude.setText(Double.valueOf(data_model.getF_latitude()).toString());
        holder.f_longitude.setText(Double.valueOf(data_model.getF_longitude()).toString());
        Log.e("longitude", "longitude= " + Double.valueOf(data_model.getF_longitude()).toString());

        grantPermission();
    }
    private  void grantPermission(){


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public  static class  DataViewHolder extends RecyclerView.ViewHolder {
        TextView FullName,LastName,Age;
        TextView IdElephant,Daily_Log_Date,f_latitude,f_longitude;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            IdElephant = itemView.findViewById(R.id.Idelephant_tv);
            Daily_Log_Date = itemView.findViewById(R.id.date_tv);
            f_latitude = itemView.findViewById(R.id.lat_tv);
            f_longitude = itemView.findViewById(R.id.long_tv);
            //Age = itemView.findViewById(R.id.tvAge);

        }
    }
}
