package com.example.danbidemo1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.danbidemo1.controllers.SystemController;
import com.example.danbidemo1.data.Counselor;
import com.example.danbidemo1.OnClinicCounselorClickListener;
import com.example.danbidemo1.R;
import java.util.ArrayList;

/** 상담사 리사이클러뷰를 이용하기 위한 어댑터 */
public class CounselorAdapter extends RecyclerView.Adapter<CounselorAdapter.CounselorViewHolder> implements OnClinicCounselorClickListener {
    private ArrayList<Counselor> arrayList;
    private Context context;
    OnClinicCounselorClickListener listener;
    public CounselorAdapter(ArrayList<Counselor> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CounselorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.counselor_item, parent, false);
        CounselorViewHolder holder = new CounselorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CounselorViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_counselorName.setText(arrayList.get(position).getCounselorName());
        holder.tv_counselorExpert.setText(SystemController.addTag(arrayList.get(position).getExpert()));
        holder.rating_counselor.setRating(arrayList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CounselorViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_counselorName;
        TextView tv_counselorExpert;
        RatingBar rating_counselor;

        public CounselorViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.counselor_img);
            this.tv_counselorName = itemView.findViewById(R.id.counselor_name_text);
            this.tv_counselorExpert = itemView.findViewById(R.id.counselor_expert_text);
            this.rating_counselor = itemView.findViewById(R.id.counselor_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String title = arrayList.get(position).getCounselorName();
                    if(listener != null){
                        listener.OnItemClick(CounselorViewHolder.this, view, position, title);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnClinicCounselorClickListener listener){
        this.listener = listener;
        Log.d("test", "SetOnItemClick");
    }

    @Override
    public void OnItemClick(CounselorViewHolder holder, View view, int position, String title) {
        if(listener != null){
            listener.OnItemClick(holder, view, position, title);
            Log.d("hello", "OnItemClickSet");
        }
    }
}
