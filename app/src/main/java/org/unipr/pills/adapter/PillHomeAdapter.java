package org.unipr.pills.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.unipr.pills.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class PillHomeAdapter extends RecyclerView.Adapter<PillHomeAdapter.ViewHolder> {

    private ArrayList<Integer> pillPhotoData = new ArrayList<>();
    private ArrayList<String> pillNameData = new ArrayList<>();
    private ArrayList<String> pillTime = new ArrayList<>();

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECK = "check";

    public boolean check = false;

    private Context context;

    public PillHomeAdapter(Context context, ArrayList<Integer> pillPhoto, ArrayList<String> pillName, ArrayList<String> pillTime) {
        pillPhotoData = pillPhoto;
        pillNameData = pillName;
        this.pillTime = pillTime;
        this.context = context;
    }

    @NonNull
    @Override
    public PillHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pills_home, parent, false);
        PillHomeAdapter.ViewHolder holder = new PillHomeAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final PillHomeAdapter.ViewHolder holder, final int position) {
        holder.tvPillName.setText(pillNameData.get(position));
        holder.imgPillPhoto.setImageResource(pillPhotoData.get(position));
        holder.imgPillPhoto.setCircleBackgroundColor(context.getColor(R.color.circle));
        holder.imgPillPhoto.setBorderWidth(25);
        holder.tvTime.setText(pillTime.get(position));
        if(check) {
            holder.home_items_parent_layout.setCardBackgroundColor(context.getColor(R.color.takenColor));
            holder.checked.setVisibility(View.VISIBLE);
        }
        holder.home_items_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.home_items_parent_layout.setCardBackgroundColor(context.getColor(R.color.takenColor));
                holder.checked.setVisibility(View.VISIBLE);
            }
        });
        saveData();
        loadData();

    }



    @Override
    public int getItemCount() {
        return pillNameData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPillName;
        CircleImageView imgPillPhoto;
        TextView tvTime;
        CardView home_items_parent_layout;
        ImageView checked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPillName = itemView.findViewById(R.id.txPillHomeName);
            imgPillPhoto = itemView.findViewById(R.id.imgPillHome);
            tvTime = itemView.findViewById(R.id.txtTimeHome);
            home_items_parent_layout = itemView.findViewById(R.id.home_items_parent_layout);
            checked = itemView.findViewById(R.id.checked);
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CHECK, check);

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        check = sharedPreferences.getBoolean(CHECK, false);
    }




}

