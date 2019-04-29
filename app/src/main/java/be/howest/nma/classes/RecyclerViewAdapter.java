package be.howest.nma.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import be.howest.nma.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Reservation> mData;
    private Context ctx;

    public RecyclerViewAdapter(Context ctx, List<Reservation> reservations) {
        this.ctx = ctx;
        mData = reservations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        view = mInflater.inflate(R.layout.reservation_row,parent,false);
        // click listener here
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.reservationData.setText(getUserdata(position));
        holder.cancelButton.setText(ctx.getString(R.string.Cancel));
        holder.confirmationButton.setText(ctx.getString(R.string.Confirm));
        //todo: set user_id, and use onclicklisteners to send cancel/confirm to node
    }

    private String getUserdata(final int position) {
        String username = mData.get(position).getUsername().split("\"")[1];
        String reservation_time = mData.get(position).getReservation_time().split("\"")[1];
        reservation_time = reservation_time.split("T")[0] + " " + reservation_time.split("T")[1].substring(0, 5);
        return username + " at " + reservation_time;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView reservationData;
        Button cancelButton;
        Button confirmationButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            reservationData = (TextView) itemView.findViewById(R.id.reservationData);
            cancelButton = (Button) itemView.findViewById(R.id.cancelButton);
            confirmationButton = (Button) itemView.findViewById(R.id.confirmButton);
        }
    }
}
