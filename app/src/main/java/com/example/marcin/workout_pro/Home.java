package com.example.marcin.workout_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;


public class Home extends Fragment implements  View.OnClickListener{
    private FloatingActionButton newTrainingButton;
    private RecyclerView mRecycler;
    TrainingAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newTrainingButton = view.findViewById(R.id.newTraining);
        newTrainingButton.setOnClickListener(this);

        mAdapter = new TrainingAdapter(Training.getTrainings());
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
                createNewTraining(v);
                break;
        }
    }

    public void createNewTraining(View v){
        Intent intent = new Intent(getActivity().getApplicationContext(), TrainingDetails.class);
        startActivity(intent);
        getActivity().finish();
    }



}
