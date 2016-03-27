package com.noqoush.adfalcon.adgallery;

import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.noqoush.adfalcon.adgallery.model.Ad;
import com.noqoush.adfalcon.adgallery.model.AdGroup;
import com.noqoush.adfalcon.adgallery.model.AdUnitSize;
import com.noqoush.adfalcon.adgallery.model.SiteID;
import com.noqoush.adfalcon.android.sdk.ADFAd;
import com.noqoush.adfalcon.android.sdk.ADFListener;
import com.noqoush.adfalcon.android.sdk.ADFTargetingParams;
import com.noqoush.adfalcon.android.sdk.ADFView;
import com.noqoush.adfalcon.android.sdk.constant.ADFAdSize;
import com.noqoush.adfalcon.android.sdk.constant.ADFErrorCode;

public class AdViewerActivity extends Activity implements ADFListener,
        OnItemSelectedListener {

    private Spinner mSizesSpnr;
    private ADFView mAdview;
    private TextView mTracing;
    private TextView mLogTV;
    private MenuItem mRefreshMI;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_ad_viewer);
        mTracing = (TextView) findViewById(R.id.textViewTracing);
        mSizesSpnr = (Spinner) findViewById(R.id.spinnerSizes);
        mLogTV = (TextView) findViewById(R.id.text_view_log);
        mLogTV.setMovementMethod(new ScrollingMovementMethod());
        mLayout = ((RelativeLayout) findViewById(R.id.linearLayoutAd));
        ArrayAdapter<AdUnitSize> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getGroup()
                .getSizes());
        mSizesSpnr.setAdapter(adapter);
        mSizesSpnr.setOnItemSelectedListener(this);
        setTitle();
    }

    @Override
    protected void onDestroy() {
        mAdview.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions_ad_viewer, menu);
        mRefreshMI = menu.findItem(R.id.action_refresh);
        mRefreshMI.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh)
            if (mAdview != null) {
                loadAd();
            }
        return super.onOptionsItemSelected(item);
    }

    private void setTitle() {
        if (getActionBar() == null)
            return;
        String title = getIntent().getStringExtra("title");
        getActionBar().setTitle(title);
        SiteID siteID = getSiteID();
        getActionBar().setSubtitle(
                getString(R.string.site_id) + " " + siteID.getTitle());
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

    public void setAdView() {
        if (mAdview == null) {
            mAdview = new ADFView(this);
        } else {
            mLayout.removeView(mAdview);
        }
         mAdview.setTestMode(getSiteID().isTesting());
        loadAd();
    }

    private void loadAd() {
        if (mRefreshMI != null)
            mRefreshMI.setVisible(false);
        setProgressBarIndeterminateVisibility(true);
        mLayout.removeView(mAdview);
        mAdview.setTestMode(getSiteID().isTesting());
        mAdview.initialize(getSiteID().getSiteID(),
                ADFAdSize.AD_UNIT_AUTO_BANNER, getParams(), this, true);
        mTracing.setText(getString(R.string.will_load));
    }

    public Ad getAdUnitSize() {
        int index = mSizesSpnr.getSelectedItemPosition();
        return getGroup().getSizes().get(index);
    }

    public ADFTargetingParams getParams() {
        ADFTargetingParams params = new ADFTargetingParams();
        params.getAdditionalInfo().put(getGroup().getType().getServerKey(),
                getGroup().getType().getServerValue());
        params.getAdditionalInfo().put(getAdUnitSize().getServerKey(),
                getAdUnitSize().getServerValue());
        return params;
    }

    @Override
    public void onLoadAd(ADFAd ad) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mLayout.addView(mAdview, params);
        finishLoading("");
        writeOnLogTextView("onLoadAd");
    }

    @Override
    public void onError(ADFAd ad, ADFErrorCode error, String message) {
        finishLoading(message);
        writeOnLogTextView("onError");
    }

    private void finishLoading(String message) {
        mTracing.setText(message);
        if (mRefreshMI != null)
            mRefreshMI.setVisible(true);
        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void onPresentAdScreen(ADFAd ad) {
        writeOnLogTextView("onPresentAdScreen");
    }

    @Override
    public void onDismissAdScreen(ADFAd ad) {
        writeOnLogTextView("onDismissAdScreen");
    }

    @Override
    public void onLeaveApplication() {
        writeOnLogTextView("onLeaveApplication");
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        setAdView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    private void writeOnLogTextView(String text) {

        long milliSecond = System.currentTimeMillis();
        Date date = new Date(milliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        mLogTV.append(text + "   " + dateFormat.format(date) + System.getProperty("line.separator"));
    }
}
