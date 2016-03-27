package com.noqoush.adfalcon.adgallery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.noqoush.adfalcon.adgallery.AdViewerActivity;
import com.noqoush.adfalcon.adgallery.InterstitialActivity;
import com.noqoush.adfalcon.adgallery.NativeAdListViewerActivity;
import com.noqoush.adfalcon.adgallery.NativeAdViewerActivity;
import com.noqoush.adfalcon.adgallery.R;
import com.noqoush.adfalcon.adgallery.fragments.adapter.AdUnitSizesAdapter;
import com.noqoush.adfalcon.adgallery.model.AdGroup;
import com.noqoush.adfalcon.adgallery.model.AdsModel;
import com.noqoush.adfalcon.adgallery.model.SiteID;

public class AdsFragment extends Fragment implements OnItemClickListener {

    private SiteID mSiteID;
    public ListView mAdUnitSizeLV;
    public AdUnitSizesAdapter mAdapter;
    public static final String ARG_SITE_ID = "site_id";

    public AdsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_ad_sizes,
                container, false);
        mSiteID = getArguments().getParcelable(ARG_SITE_ID);
        mAdUnitSizeLV = (ListView) rootView.findViewById(R.id.listViewAds);
        mAdapter = new AdUnitSizesAdapter(getActivity());
        mAdUnitSizeLV.setAdapter(mAdapter);
        mAdUnitSizeLV.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Object object = mAdapter.getItem(arg2);
        if (object instanceof AdGroup) {
            AdGroup group = (AdGroup) object;

            if (group == AdsModel.getInstance(getActivity()).GROUP_INTERSTITIAL || group == AdsModel.getInstance(getActivity()).GROUP_INTERSTITIAL_IVIDEO) {

                Intent adViewer = new Intent(getActivity(),
                        InterstitialActivity.class);
                adViewer.putExtra("siteID", mSiteID);
                adViewer.putExtra("adGroup", group);
                adViewer.putExtra("title", group.getTitle());
                getActivity().startActivity(adViewer);

            } else if (group == AdsModel.getInstance(getActivity()).GROUP_NATIVE_AD) {

                Intent adViewer = new Intent(getActivity(),
                        NativeAdViewerActivity.class);
                adViewer.putExtra("siteID", mSiteID);
                adViewer.putExtra("adGroup", group);
                adViewer.putExtra("title", group.getTitle());
                getActivity().startActivity(adViewer);

            } else if (group == AdsModel.getInstance(getActivity()).GROUP_NATIVE_AD_LIST) {

                Intent adViewer = new Intent(getActivity(),
                        NativeAdListViewerActivity.class);
                adViewer.putExtra("siteID", mSiteID);
                adViewer.putExtra("adGroup", group);
                adViewer.putExtra("title", group.getTitle());
                getActivity().startActivity(adViewer);

            } else {

                Intent adViewer = new Intent(getActivity(),
                        AdViewerActivity.class);
                adViewer.putExtra("siteID", mSiteID);
                adViewer.putExtra("adGroup", group);
                adViewer.putExtra("title", group.getTitle());
                getActivity().startActivity(adViewer);

            }
        }
    }

}
