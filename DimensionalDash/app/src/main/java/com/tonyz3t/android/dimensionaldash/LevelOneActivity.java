package com.tonyz3t.android.dimensionaldash;

import androidx.fragment.app.Fragment;

public class LevelOneActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LevelOneFragment();
    }
}
