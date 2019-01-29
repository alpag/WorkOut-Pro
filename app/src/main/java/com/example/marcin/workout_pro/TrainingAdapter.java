package com.example.marcin.workout_pro;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

    private ArrayList<Training> mTraining;

    public TrainingAdapter(ArrayList<Training> trainingList) {
        mTraining = trainingList;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.training_item, viewGroup, false);
        TrainingViewHolder rvh = new TrainingViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        Training currentItem = mTraining.get(position);

        holder.mTrainingDate.setText(String.valueOf(currentItem.getDate()));
    }

    @Override
    public int getItemCount() {
        if(mTraining == null)
            return 0;
        return mTraining.size();
    }

    public static class TrainingViewHolder extends RecyclerView.ViewHolder {
        public TextView mTrainingDate;

        public TrainingViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrainingDate = itemView.findViewById(R.id.trainingDate);

        }
    }
}
