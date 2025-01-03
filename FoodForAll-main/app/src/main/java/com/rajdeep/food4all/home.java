package com.rajdeep.food4all;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class home extends Fragment {

    LinearLayout donationCard,fundsCard,chatCard,charityCard;
    View view;
    private donateFoodFormActivity donateFoodFormActivity;
    TextView userEmailTextView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    public home() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        donationCard = view.findViewById(R.id.donationCard);
        donationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), donationFoodForm2Activity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        fundsCard = view.findViewById(R.id.fundsCard);
        fundsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), donateFundsActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        chatCard = view.findViewById(R.id.chatCard);
        chatCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), chatActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });

        charityCard = view.findViewById(R.id.charityCard);
        charityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), charityActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });

        return view;

    }

}