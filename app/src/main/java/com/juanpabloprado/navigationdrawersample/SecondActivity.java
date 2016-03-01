package com.juanpabloprado.navigationdrawersample;

import android.os.Bundle;

public class SecondActivity extends BaseDrawerActivity {

  protected int getSelfNavDrawerItem() {
    return NAVDRAWER_ITEM_GALLERY;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
  }
}
