package com.juanpabloprado.navigationdrawersample;

import android.os.Bundle;

public class FirstActivity extends BaseDrawerActivity {

  protected int getSelfNavDrawerItem() {
    return NAVDRAWER_ITEM_CAMERA;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first);
  }
}
