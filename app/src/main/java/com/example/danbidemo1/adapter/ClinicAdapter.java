package com.example.danbidemo1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.danbidemo1.controllers.SystemController;
import com.example.danbidemo1.data.CounsellingCenter;
import com.example.danbidemo1.OnClinicCenterClickListner;
import com.example.danbidemo1.R;
import java.util.ArrayList;

/** 상담센터 리사이클러뷰를 이용하기 위한 어댑터 */
public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicViewHolder> implements OnClinicCenterClickListner {
    private ArrayList<CounsellingCenter> arrayList;
    private Context context;
    OnClinicCenterClickListner listener;
    public ClinicAdapter(ArrayList<CounsellingCenter> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClinicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinic_item, parent, false);
        ClinicViewHolder holder = new ClinicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_clinicName.setText(arrayList.get(position).getName());
        holder.tv_clinicExpertise.setText(SystemController.addTag(arrayList.get(position).getExpert()));
        holder.tv_clinicAddress.setText(arrayList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ClinicViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_clinicName;
        TextView tv_clinicExpertise;
        TextView tv_clinicAddress;

        public ClinicViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile_img);
            this.tv_clinicName = itemView.findViewById(R.id.clinic_name_text);
            this.tv_clinicExpertise = itemView.findViewById(R.id.clinic_expertise_text);
            this.tv_clinicAddress = itemView.findViewById(R.id.clinic_address_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String title = arrayList.get(position).getName();
                    if(listener != null){
                        listener.OnItemClick(ClinicViewHolder.this, view, position, title);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnClinicCenterClickListner listener){
        this.listener = listener;
        Log.d("test", "SetOnItemClick");
    }

    @Override
    public void OnItemClick(ClinicViewHolder holder, View view, int position, String title) {
        if(listener != null){
            listener.OnItemClick(holder, view, position, title);
            Log.d("hello", "OnItemClickSet");
        }
    }
}
