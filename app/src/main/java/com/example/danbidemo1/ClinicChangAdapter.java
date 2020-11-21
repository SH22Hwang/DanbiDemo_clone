package com.example.danbidemo1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ClinicChangAdapter extends RecyclerView.Adapter<ClinicChangAdapter.ClinicChangViewHolder>{
    private ArrayList<ClinicChang> arrayList;
    private Context context;

    public ClinicChangAdapter(ArrayList<ClinicChang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClinicChangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinic_item, parent, false);
        ClinicChangAdapter.ClinicChangViewHolder holder = new ClinicChangAdapter.ClinicChangViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicChangViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_clinicName.setText(arrayList.get(position).getClinic_name());
        holder.tv_clinicExpertise.setText(arrayList.get(position).getClinic_expertise() + "");
        holder.tv_clinicAddress.setText(arrayList.get(position).getClinic_address());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ClinicChangViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_profile;
        TextView tv_clinicName;
        TextView tv_clinicExpertise;
        TextView tv_clinicAddress;


        public ClinicChangViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile_img);
            this.tv_clinicName = itemView.findViewById(R.id.clinic_name_text);
            this.tv_clinicExpertise = itemView.findViewById(R.id.clinic_expertise_text);
            this.tv_clinicAddress = itemView.findViewById(R.id.clinic_address_text);
        }
    }
}
