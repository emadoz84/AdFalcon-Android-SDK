package com.noqoush.adfalcon.adgallery;


import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.noqoush.adfalcon.adgallery.helper.InterstitialHelper;
import com.noqoush.adfalcon.adgallery.model.AdGroup;
import com.noqoush.adfalcon.adgallery.model.SiteID;

public class InterstitialActivity extends Activity {

    private TextView logTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        if (getActionBar() != null)
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle();

        logTV = (TextView) findViewById(R.id.interstitial_log);
        logTV.setMovementMethod(new ScrollingMovementMethod());

        InterstitialHelper ih = new InterstitialHelper(this, getSiteID(), getGroup(), logTV);
        ih.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions_ad_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            InterstitialHelper ih = new InterstitialHelper(this, getSiteID(), getGroup(), logTV);
            ih.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitle() {
        if (getActionBar() == null)
            return;
        String title = getIntent().getStringExtra("title");
        getActionBar().setTitle(title);
        SiteID siteID = getSiteID();
        getActionBar().setSubtitle(getString(R.string.site_id) + " " + siteID.getTitle());
    }

    AdGroup getGroup() {
        return getIntent().getParcelableExtra("adGroup");
    }

    SiteID getSiteID() {
        SiteID siteID = getIntent().getParcelableExtra("siteID");
        if (getGroup().getSiteID() != null) {
            if (siteID.isDefaultSiteID()) {
                String siteIDStr;
                if (siteID.isTesting()) {
                    siteIDStr = getString(R.string.ivideo_site_id_test);
                } else {
                    siteIDStr = getString(R.string.ivideo_site_id_pro);
                }
                return new SiteID(siteIDStr);
            }

        }
        return siteID;
    }
}
