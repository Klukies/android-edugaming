package be.howest.nma.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

import be.howest.nma.R;
import be.howest.nma.api.URLs;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Reservation> mData;
    private Context ctx;
    private String accessToken;

    public RecyclerViewAdapter(Context ctx, List<Reservation> reservations, String token) {
        this.ctx = ctx;
        mData = reservations;
        this.accessToken = token;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        view = mInflater.inflate(R.layout.reservation_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.reservationData.setText(getUserdata(position));
        holder.cancelButton.setText(ctx.getString(R.string.Cancel));
        holder.confirmationButton.setText(ctx.getString(R.string.Confirm));
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mData.get(position).getUsername();
                String reservation_time = mData.get(position).getReservation_time();
                cancelReservation(username, reservation_time);
            }
        });
        //todo: set user_id, and use onclicklisteners to send cancel/confirm to node
    }

    private void cancelReservation(String username, String reservation_time) {
        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("reservation_time", reservation_time);
        Ion.with(ctx)
                .load("POST", URLs.CANCEL_RESERVATION_URL)
                .setHeader("x-access-token", accessToken)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            if (e.getMessage() == null) {
                                makeToaster("Couldn't connect to api");
                            } else {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            makeToaster(result.get("success").getAsString());
                        }
                    }
                });
    }

    private void makeToaster(String message) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
        toast.show();
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
