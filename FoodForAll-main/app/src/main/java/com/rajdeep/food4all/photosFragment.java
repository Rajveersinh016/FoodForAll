package com.rajdeep.food4all;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


public class photosFragment extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<ParentModelClass> parentModelClassArrayList;
    private ArrayList<ChildModelClass> childModelClassArrayList;
    private ArrayList<ChildModelClass> favoriteList;
    private ArrayList<ChildModelClass> recentlyWatchedList;
    private ArrayList<ChildModelClass> latestList;

    View view;

    public photosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photos, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.rv_parent);
        childModelClassArrayList = new ArrayList<>();
        favoriteList = new ArrayList<>();
        recentlyWatchedList = new ArrayList<>();
        latestList = new ArrayList<>();
        parentModelClassArrayList = new ArrayList<>();

        latestList.add(new ChildModelClass(R.drawable.l));
        latestList.add(new ChildModelClass(R.drawable.m));
        latestList.add(new ChildModelClass(R.drawable.c));
        latestList.add(new ChildModelClass(R.drawable.d));
        latestList.add(new ChildModelClass(R.drawable.e));
        latestList.add(new ChildModelClass(R.drawable.f));
        latestList.add(new ChildModelClass(R.drawable.g));
        latestList.add(new ChildModelClass(R.drawable.h));
        latestList.add(new ChildModelClass(R.drawable.i));
        latestList.add(new ChildModelClass(R.drawable.k));
        latestList.add(new ChildModelClass(R.drawable.l));
        latestList.add(new ChildModelClass(R.drawable.m));

        parentModelClassArrayList.add(new ParentModelClass("NGO >", latestList));

        recentlyWatchedList.add(new ChildModelClass(R.drawable.l));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.m));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.l));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.k));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.m));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.h));

        parentModelClassArrayList.add(new ParentModelClass("NGO >", recentlyWatchedList));

        // Set up RecyclerView
        ParentAdapter parentAdapter = new ParentAdapter(parentModelClassArrayList, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(parentAdapter);
        parentAdapter.notifyDataSetChanged();

    }
}