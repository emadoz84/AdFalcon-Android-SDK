package com.noqoush.adfalcon.adgallery.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.noqoush.adfalcon.adgallery.R;
import com.noqoush.adfalcon.adgallery.model.AdGroup;
import com.noqoush.adfalcon.adgallery.model.SiteID;
import com.noqoush.adfalcon.android.sdk.ADFAd;
import com.noqoush.adfalcon.android.sdk.ADFInterstitial;
import com.noqoush.adfalcon.android.sdk.ADFListener;
import com.noqoush.adfalcon.android.sdk.ADFTargetingParams;
import com.noqoush.adfalcon.android.sdk.constant.ADFErrorCode;

public class InterstitialHelper implements ADFListener {
    private Activity activity;
    private SiteID siteID;
    private AdGroup adGroup;
    private TextView logTV;
    private Toast mToast;

    public InterstitialHelper(Activity activity, SiteID siteID, AdGroup adGroup) {
        setActivity(activity);
        setSiteID(siteID);
        setAdGroup(adGroup);
    }

    public InterstitialHelper(Activity activity, SiteID siteID, AdGroup adGroup, TextView logTextView) {
        setActivity(activity);
        setSiteID(siteID);
        setAdGroup(adGroup);
        setLogTV(logTextView);
    }

    public void show() {
        ADFInterstitial interstitial = new ADFInterstitial(getActivity(),
                getFinalSiteID().getSiteID(), this, getParams());
		interstitial.setTestMode(getSiteID().isTesting());
        interstitial.loadInterstitialAd();
        mToast = Toast.makeText(getActivity(),
                getActivity().getString(R.string.will_load), Toast.LENGTH_LONG);
        mToast.show();
    }

    public ADFTargetingParams getParams() {
        ADFTargetingParams params = new ADFTargetingParams();
        params.getAdditionalInfo().put(getAdGroup().getType().getServerKey(),
                getAdGroup().getType().getServerValue());
        return params;
    }

    private SiteID getFinalSiteID() {
        SiteID siteID = getSiteID();
        if (getAdGroup().getSiteID() != null) {
            if (siteID.isDefaultSiteID()) {
                String siteIDStr;
                if (siteID.isTesting()) {
                    siteIDStr = getActivity().getString(
                            R.string.ivideo_site_id_test);
                } else {
                    siteIDStr = getActivity().getString(
                            R.string.ivideo_site_id_pro);
                }
                return new SiteID(siteIDStr);
            }

        }
        return siteID;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public SiteID getSiteID() {
        return siteID;
    }

    public void setSiteID(SiteID siteID) {
        this.siteID = siteID;
    }

    public AdGroup getAdGroup() {
        return adGroup;
    }

    public void setAdGroup(AdGroup adGroup) {
        this.adGroup = adGroup;
    }

    @Override
    public void onLoadAd(ADFAd ad) {
        mToast.cancel();
        ADFInterstitial interstitial = (ADFInterstitial) ad;
        writeOnLogTextView("onLoadAd");
        if (interstitial.isReady()) {
            interstitial.showInterstitialAd();
        }
    }

    @Override
    public void onError(ADFAd ad, ADFErrorCode error, String message) {
        mToast.cancel();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        writeOnLogTextView("onError");
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

    public TextView getLogTV() {
        return logTV;
    }

    public void setLogTV(TextView logTV) {
        this.logTV = logTV;
    }

    private void writeOnLogTextView(String text) {

        if (getLogTV() == null)
            return;

        long milliSecond = System.currentTimeMillis();
        Date date = new Date(milliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        getLogTV().append(text + "   " + dateFormat.format(date) + System.getProperty("line.separator"));
    }
}
