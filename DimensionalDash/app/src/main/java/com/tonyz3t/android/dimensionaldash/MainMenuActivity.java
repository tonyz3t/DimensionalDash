package com.tonyz3t.android.dimensionaldash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainMenuActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainMenuFragment();
    }
}
