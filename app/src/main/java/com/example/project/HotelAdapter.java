package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    public interface OnHotelActionListener {
        void onViewDetails(HotelModel hotel);
        void onEditHotel(HotelModel hotel);
        void onDeleteHotel(HotelModel hotel);
    }

    private List<HotelModel> hotelList;
    private Context context;
    private OnHotelActionListener listener;

    public HotelAdapter(Context context, List<HotelModel> hotelList, OnHotelActionListener listener) {
        this.context = context;
        this.hotelList = hotelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_card, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        HotelModel hotel = hotelList.get(position);
        holder.tvHotelName.setText(hotel.getName());
        holder.tvHotelAddress.setText(hotel.getAddress());
        holder.tvRoomCount.setText("Số phòng: " + hotel.getRoomCount());

        holder.btnViewDetails.setOnClickListener(v -> {
            if (listener != null) listener.onViewDetails(hotel);
        });
        holder.btnEditHotel.setOnClickListener(v -> {
            if (listener != null) listener.onEditHotel(hotel);
        });
        holder.btnDeleteHotel.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteHotel(hotel);
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public void setHotelList(List<HotelModel> list) {
        this.hotelList = list;
        notifyDataSetChanged();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView tvHotelName, tvHotelAddress, tvRoomCount;
        Button btnViewDetails, btnEditHotel, btnDeleteHotel;
        CardView cardView;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.tvHotelName);
            tvHotelAddress = itemView.findViewById(R.id.tvHotelAddress);
            tvRoomCount = itemView.findViewById(R.id.tvRoomCount);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnEditHotel = itemView.findViewById(R.id.btnEditHotel);
            btnDeleteHotel = itemView.findViewById(R.id.btnDeleteHotel);
            cardView = (CardView) itemView;
        }
    }
}
