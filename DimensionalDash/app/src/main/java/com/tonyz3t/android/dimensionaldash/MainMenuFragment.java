package com.tonyz3t.android.dimensionaldash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainMenuFragment extends Fragment {
    private static final String TAG = "MainMenuFragment";

    private Button mPlayGameButton;
    private Button mStatsButton;
    private Button mStoreButton;
    private Button mSignOutButton;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        mPlayGameButton = (Button) v.findViewById(R.id.playgame_button);
        mStatsButton = (Button) v.findViewById(R.id.stats_button);
        mStoreButton = (Button) v.findViewById(R.id.stats_button);
        mSignOutButton = (Button) v.findViewById(R.id.signout_button_menu);

        mPlayGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LevelOneActivity.class);
                startActivity(intent);
            }
        });

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                getActivity().finish();
            }
        });



        return v;
    }
}
