package com.juanpabloprado.navigationdrawersample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.BindString;
import com.squareup.picasso.Picasso;
import timber.log.Timber;

public class BaseDrawerActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
  @Bind(R.id.nav_view) NavigationView navigationView;
  @Nullable @Bind(R.id.main_content) View mainContent;

  @BindString(R.string.user_profile_photo) String profilePhoto;

  // delay to launch nav drawer item, to allow close animation to play
  private static final int NAVDRAWER_LAUNCH_DELAY = 250;

  protected static final int NAVDRAWER_ITEM_INVALID = -1;

  protected static final int NAVDRAWER_ITEM_CAMERA = R.id.nav_camera;
  protected static final int NAVDRAWER_ITEM_GALLERY = R.id.nav_gallery;

  // fade in and fade out durations for the main content when switching between
  // different Activities of the app through the Nav Drawer
  private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;

  private static final int MAIN_CONTENT_FADEIN_DURATION = 250;

  /**
   * Returns the navigation drawer item that corresponds to this Activity. Subclasses
   * of BaseActivity override this to indicate what nav drawer item corresponds to them
   * Return NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
   */
  protected int getSelfNavDrawerItem() {
    return NAVDRAWER_ITEM_INVALID;
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentViewWithoutInject(R.layout.activity_drawer);
    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_frame);
    LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
    bindViews();
    setupHeader();

    navigationView.setNavigationItemSelectedListener(this);

    if (getSelfNavDrawerItem() != NAVDRAWER_ITEM_INVALID) {
      navigationView.getMenu().findItem(getSelfNavDrawerItem()).setChecked(true);
    }
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    if (mainContent != null) {
      mainContent.setAlpha(0);
      mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
    } else {
      Timber.w("No view with ID main_content to fade in.");
    }
  }

  @Override protected void setupToolbar() {
    super.setupToolbar();
    if (toolbar != null) {
      toolbar.setNavigationIcon(R.drawable.ic_menu_white);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          drawerLayout.openDrawer(GravityCompat.START);
        }
      });
    }
  }

  private void setupHeader() {
    View headerView = navigationView.getHeaderView(0);
    ImageView avatarView = (ImageView) headerView.findViewById(R.id.avatar);

    Picasso.with(BaseDrawerActivity.this).load(profilePhoto).into(avatarView);
  }

  @Override public boolean onNavigationItemSelected(final MenuItem item) {

    item.setChecked(true);

    if (getSelfNavDrawerItem() != item.getItemId()) {

      Handler mHandler = new Handler();
      mHandler.postDelayed(new Runnable() {
        @Override public void run() {
          Intent intent;
          switch (item.getItemId()) {
            case NAVDRAWER_ITEM_CAMERA:
              intent = new Intent(BaseDrawerActivity.this, FirstActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
              startActivity(intent);
              break;
            case NAVDRAWER_ITEM_GALLERY:
              intent = new Intent(BaseDrawerActivity.this, SecondActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
              startActivity(intent);
              break;
          }
        }
      }, NAVDRAWER_LAUNCH_DELAY);

      if (mainContent != null) {
        mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
      }
    }

    drawerLayout.closeDrawers();
    return true;
  }
}
