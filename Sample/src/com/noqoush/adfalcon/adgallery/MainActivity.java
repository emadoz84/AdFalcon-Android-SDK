package com.noqoush.adfalcon.adgallery;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.noqoush.adfalcon.adgallery.fragments.AdsFragment;
import com.noqoush.adfalcon.adgallery.helper.SiteIDHelper;
import com.noqoush.adfalcon.adgallery.util.SharedReferenceUtil;

public class MainActivity extends FragmentActivity implements
        ActionBar.OnNavigationListener {

    private static final String LAST_SELECTED_INDEX = "LAST_SELECTED_INDEX";
    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    //private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private int lastSelectedIndex = 0;
    SiteIDHelper siteIDHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        // Set up the site ids
        this.siteIDHelper = new SiteIDHelper(this);

        // Set up the dropdown list navigation in the action bar.
        actionBar
                .setListNavigationCallbacks(
                        // Specify a SpinnerAdapter to populate the dropdown
                        // list.
                        new ArrayAdapter<>(actionBar.getThemedContext(),
                                android.R.layout.simple_list_item_1,
                                android.R.id.text1, this.siteIDHelper
                                .getTitleSiteIDs()), this);
        readLastIndex();
        getActionBar().setSelectedNavigationItem(lastSelectedIndex);
    }

    private void readLastIndex() {
        try {
            lastSelectedIndex = Integer.parseInt(SharedReferenceUtil.getValue(
                    this, LAST_SELECTED_INDEX));
            if (lastSelectedIndex >= this.siteIDHelper.getTitleSiteIDs().size()) {
                lastSelectedIndex = 0;
            }
        } catch (Exception ex) {
            lastSelectedIndex = 0;
        }
    }

    /*
     * @Override public void onRestoreInstanceState(Bundle savedInstanceState) {
     * // Restore the previously serialized current dropdown position. if
     * (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
     * getActionBar().setSelectedNavigationItem(
     * savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM)); } }
     *
     * @Override public void onSaveInstanceState(Bundle outState) { // Serialize
     * the current dropdown position.
     * outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
     * .getSelectedNavigationIndex()); }
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, ManageSiteIDsActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        Fragment fragment = new AdsFragment();
        Bundle args = new Bundle();
        lastSelectedIndex = getActionBar().getSelectedNavigationIndex();
        args.putParcelable(AdsFragment.ARG_SITE_ID, this.siteIDHelper
                .getSiteIDs().get(lastSelectedIndex));
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
        SharedReferenceUtil.put(this, LAST_SELECTED_INDEX, ""
                + lastSelectedIndex);
        return true;
    }
}
