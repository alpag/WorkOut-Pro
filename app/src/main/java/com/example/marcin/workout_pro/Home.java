package com.example.marcin.workout_pro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;


public class Home extends Fragment implements  View.OnClickListener{
    static final int EDIT_TRAINING_REQUEST = 1;
    static final int NEXT_TRAINING_REQUEST = 2;

    private FloatingActionButton newTrainingButton;
    private RecyclerView mRecycler;
    public TrainingAdapter mAdapter;
    private Training currentTraining;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newTrainingButton = view.findViewById(R.id.newTraining);
        newTrainingButton.setOnClickListener(this);

        mAdapter = new TrainingAdapter(Training.getTrainings(), new CustomTrainingClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                currentTraining = Training.getTrainings().get(position);
                TrainingDetails.STATUS_MODE = 4;
                ((MainActivity)getActivity()).previewTrainingDetails(currentTraining);

            }
        });
        mRecycler = view.findViewById(R.id.recycler_view);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.newTraining:
                if(Training.getTrainings().isEmpty())
                    TrainingDetails.STATUS_MODE = 1;
                else
                    TrainingDetails.STATUS_MODE = 3;
                createNewTraining();
                break;
        }
    }

    public void createNewTraining(){
        ((MainActivity)getActivity()).createNewTraining();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new TrainingAdapter(Training.getTrainings(), new CustomTrainingClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                currentTraining = Training.getTrainings().get(position);
                TrainingDetails.STATUS_MODE = 4;
                ((MainActivity)getActivity()).previewTrainingDetails(currentTraining);

            }
        });
        mRecycler.setAdapter(mAdapter);
    }
}
