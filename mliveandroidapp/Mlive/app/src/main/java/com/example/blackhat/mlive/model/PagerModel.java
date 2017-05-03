package com.example.blackhat.mlive.model;

import com.example.blackhat.mlive.R;

/**
 * Created by DigitalDesk on 4/1/2017.
 */

public enum PagerModel {

    RED(0, R.layout.activity_main),
    BLUE(1, R.layout.activity_second_script_list);


    private int mTitleResId;
    private int mLayoutResId;

    PagerModel(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }


}
