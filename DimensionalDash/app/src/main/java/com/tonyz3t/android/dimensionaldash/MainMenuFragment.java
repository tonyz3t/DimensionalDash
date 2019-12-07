package com.tonyz3t.android.dimensionaldash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

class MainMenuFragment extends Fragment {
    private static final String TAG = "MainMenuFragment";

    private Button mPlayGameButton;
    private Button mStatsButton;
    private Button mStoreButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        mPlayGameButton = (Button) v.findViewById(R.id.playgame_button);
        mStatsButton = (Button) v.findViewById(R.id.stats_button);
        mStoreButton = (Button) v.findViewById(R.id.stats_button);

        mPlayGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the game.
            }
        });

        return v;
    }
}
