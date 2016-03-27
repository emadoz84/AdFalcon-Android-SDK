package com.noqoush.adfalcon.adgallery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.noqoush.adfalcon.adgallery.adfhelper.ADFAdsPosition;
import com.noqoush.adfalcon.adgallery.adfhelper.ADFNativeAdAdapter;
import com.noqoush.adfalcon.adgallery.adfhelper.ADFNativeAdAdapterModel;
import com.noqoush.adfalcon.adgallery.model.AdGroup;
import com.noqoush.adfalcon.adgallery.model.ItemModel;
import com.noqoush.adfalcon.adgallery.model.SiteID;
import com.noqoush.adfalcon.android.sdk.nativead.assets.ADFAssetsBinder;

public class NativeAdListViewerActivity extends Activity {

    private static final int AD_SPACING = 20;
    private static final int ADS_COUNT = 25;
    private static final int AD_START = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        if (getActionBar() != null)
        	getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_native_ad_list_viewer);
        setTitle();

        setListViewAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            setListViewAdapter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListViewAdapter() {
        ListView nativeLV = (ListView) findViewById(R.id.native_list_view);
        CustomAdapter adapter = new CustomAdapter(this, R.layout.list_item_layout, getLocales());
        ADFNativeAdAdapterModel adfNativeAdAdapterModel = new ADFNativeAdAdapterModel(nativeLV, adapter,
                getSiteID().getSiteID(), getAssetBinder(), getSiteID().isTesting());
        ADFAdsPosition position = new ADFAdsPosition();
        position.setStart(AD_START);
        position.setSpace(AD_SPACING);
        position.setNumberOfAds(ADS_COUNT);
        adfNativeAdAdapterModel.setAdsPosition(position);

        ADFNativeAdAdapter adfNativeAdAdapter = new ADFNativeAdAdapter(adfNativeAdAdapterModel);
        nativeLV.setAdapter(adfNativeAdAdapter);
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

    private ADFAssetsBinder getAssetBinder() {

        ADFAssetsBinder.Builder binder = new ADFAssetsBinder.Builder(this, R.layout.list_item_template_layout);
        binder.addIconImageView(R.id.icon_template, 50, 50);
        binder.addTitleTextView(R.id.title_template, 25);

        return binder.build();
    }

    private ArrayList<ItemModel> getLocales() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<ItemModel> countries = new ArrayList<>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            String countryCode = locale.getCountry();
            int id = getResources().getIdentifier("drawable/" + countryCode.toLowerCase(), null, getPackageName());
            ItemModel item = new ItemModel(countryCode, country, locale.getDisplayLanguage(), id);
            if (country.trim().length() > 0 && !countries.contains(item) && id != 0) {
                countries.add(item);
            }
        }

        Collections.sort(countries, Collections.reverseOrder());
        return countries;
    }

    private class CustomAdapter extends ArrayAdapter<ItemModel> {

        public CustomAdapter(Context context, int resource, List<ItemModel> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            try {
                if (convertView == null) {
                    view = getLayoutInflater().inflate(R.layout.list_item_layout, parent, false);
                } else {
                    view = convertView;
                }

                ItemModel model = getItem(position);
                TextView titleTV = (TextView) view.findViewById(R.id.title_text);
                ImageView iconIV = (ImageView) view.findViewById(R.id.icon_image);

                titleTV.setText(model.getTitle());
                iconIV.setImageResource(model.getResourceId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return view;
        }
    }
}
