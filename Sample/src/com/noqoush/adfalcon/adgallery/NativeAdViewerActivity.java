package com.noqoush.adfalcon.adgallery;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.noqoush.adfalcon.adgallery.model.AdGroup;
import com.noqoush.adfalcon.adgallery.model.SiteID;
import com.noqoush.adfalcon.android.sdk.constant.ADFErrorCode;
import com.noqoush.adfalcon.android.sdk.nativead.ADFNativeAd;
import com.noqoush.adfalcon.android.sdk.nativead.ADFNativeAdListener;
import com.noqoush.adfalcon.android.sdk.nativead.assets.ADFAssetsBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class NativeAdViewerActivity extends Activity implements ADFNativeAdListener {

    private ADFNativeAd mAdfNativeAd;
    private TextView mTracing;
    private MenuItem mRefreshMI;
    private TextView mLogTV;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        if (getActionBar() != null)
        	getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_ad_viewer);
        mLogTV = (TextView) findViewById(R.id.text_view_log);
        mLogTV.setMovementMethod(new ScrollingMovementMethod());
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSizes);
        spinner.setVisibility(View.GONE);
        mTracing = (TextView) findViewById(R.id.textViewTracing);
        mLayout = ((RelativeLayout) findViewById(R.id.linearLayoutAd));
        setTitle();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setAdView();
    }

    @Override
    protected void onDestroy() {
        mAdfNativeAd.destroy();
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
            if (mAdfNativeAd != null) {
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
        if (mAdfNativeAd == null) {
            mAdfNativeAd = new ADFNativeAd(this);
            mAdfNativeAd.setTesting(getSiteID().isTesting());
        } else {
            mLayout.removeView(mAdfNativeAd);
        }
        loadAd();
    }

    private void loadAd() {
        if (mRefreshMI != null)
            mRefreshMI.setVisible(false);
        setProgressBarIndeterminateVisibility(true);
        mLayout.removeView(mAdfNativeAd);

        mAdfNativeAd = new ADFNativeAd(this);
        mAdfNativeAd.setTesting(getSiteID().isTesting());
        mAdfNativeAd.setListener(this);
        mAdfNativeAd.loadAd(getSiteID().getSiteID(), null, getAssetBinder());

        mTracing.setText(getString(R.string.will_load));
    }

    private ADFAssetsBinder getAssetBinder() {

        return new ADFAssetsBinder.Builder(this, R.layout.template_layout).
                addIconImageView(R.id.icon_template, 50, 50).
                addTitleTextView(R.id.title_template, 25).
                addDescriptionTextView(R.id.description_template, 100).
                addMainAssetRelativeLayout(R.id.container_template, 50, 50).
                addStarRatingBar(R.id.rating_template).
                addExtraDataTextView(ADFAssetsBinder.DATA_ID_SPONSORED, R.id.sponsored_template, -1).
                addActionButton(R.id.action_button_template, 10).build();
    }

    private void finishLoading(String message) {
        mTracing.setText(message);
        if (mRefreshMI != null)
            mRefreshMI.setVisible(true);

        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void onLoadAd(ADFNativeAd adfNativeAd) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mLayout.addView(mAdfNativeAd, params);
        finishLoading("");
        writeOnLogTextView("onLoadAd");
    }

    @Override
    public void onFail(ADFNativeAd v, ADFErrorCode code, String errorMessage) {

        finishLoading(errorMessage);
        writeOnLogTextView("onFail");
    }

    @Override
    public boolean handleCustomAction(String s) {
        writeOnLogTextView("handleCustomAction");
        return false;
    }

    @Override
    public void renderExtraData(View view, Hashtable<Integer, String> hashtable) {
        writeOnLogTextView("renderExtraData");
    }

    private void writeOnLogTextView(String text) {

        long milliSecond = System.currentTimeMillis();
        Date date = new Date(milliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        mLogTV.append(text + "   " + dateFormat.format(date) + System.getProperty("line.separator"));
    }

	@Override
	public void onDismissAdScreen(ADFNativeAd arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentAdScreen(ADFNativeAd arg0) {
		// TODO Auto-generated method stub
		
	}
}
