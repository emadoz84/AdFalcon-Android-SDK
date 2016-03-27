package com.noqoush.adfalcon.adgallery.adfhelper;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.noqoush.adfalcon.android.sdk.constant.ADFErrorCode;
import com.noqoush.adfalcon.android.sdk.nativead.ADFNativeAd;
import com.noqoush.adfalcon.android.sdk.nativead.ADFNativeAdListener;
import com.noqoush.adfalcon.android.sdk.nativead.ADFNativeAdStatus;

import java.util.Hashtable;
import java.util.Vector;

public class ADFNativeAdAdapter extends BaseAdapter implements
        ADFNativeAdListener {

    private static final int MAX_POOL_SIZE = 5;

    private DataSetObserver dataSetObserver;
    private Vector<Cell> cells = new Vector<>();
    private Vector<AdView> ads = new Vector<>();
    private ADFNativeAdAdapterModel model;
    private boolean noAds = false;

    public ADFNativeAdAdapter(ADFNativeAdAdapterModel model) {

        this.dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                resetCells();
                notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                notifyDataSetInvalidated();
            }
        };

        this.setModel(model);
        this.getModel().getAdapter()
                .registerDataSetObserver(this.dataSetObserver);
        loadAds();
    }

    public void destroy() {
        try {
            for (AdView ad : this.ads) {
                ad.ad.destroy();
            }

            this.getModel().getAdapter()
                    .unregisterDataSetObserver(this.dataSetObserver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	/*
     * Helping methods
	 */

    private void resetCells() {

        try {

            this.cells.removeAllElements();
            int addedAds = 0;

            if (getModel().getAdsPosition().isInverse()) {
                int count = getModel().getAdapter().getCount();
                for (int i = count - 1; i >= 0; i--) {

                    int strt = getModel().getAdsPosition().getStart();
                    int spc = (getModel().getAdsPosition().getSpace() * addedAds);
                    int indexOfAd = count - (strt + spc) - 1;
                    if (!isNoAds()
                            && i == indexOfAd
                            && addedAds < getModel().getAdsPosition()
                            .getNumberOfAds() && this.ads.size() > 0) {
                        Cell cell = new Cell();
                        cell.view = ads.get(addedAds++ % ads.size());
                        this.cells.insertElementAt(cell, 0);
                    }

                    Cell cell = new Cell();
                    cell.originalPosition = i;
                    cell.id = this.getModel().getAdapter().getItemId(i);
                    this.cells.insertElementAt(cell, 0);

                }
            } else {
                for (int i = 0; i < getModel().getAdapter().getCount(); i++) {

                    int indexOfAd = getModel().getAdsPosition().getStart()
                            + (this.getModel().getAdsPosition().getSpace() * addedAds);
                    if (!isNoAds()
                            && i == indexOfAd
                            && addedAds < getModel().getAdsPosition()
                            .getNumberOfAds() && this.ads.size() > 0) {
                        Cell cell = new Cell();
                        cell.view = ads.get(addedAds++ % ads.size());
                        this.cells.add(cell);
                    }

                    Cell cell = new Cell();
                    cell.originalPosition = i;
                    cell.id = getModel().getAdapter().getItemId(i);
                    this.cells.add(cell);

                }
            }

        } catch (Exception ex) {
            Log.e("AdFalconTest", "ADFNativeAdAdapter->resetCells :" + ex.getMessage());
        }
    }

    public boolean isAd(int position) {
        return (this.cells.get(position).view != null);
    }

    public int getOriginalPosition(int position) {
        if (isAd(position))
            return -1;
        return this.cells.get(position).originalPosition;
    }

    public int getUnoriginalPosition(int originalPosition) {
        for (int position = 0; position < this.cells.size(); position++) {
            Cell cell = this.cells.get(position);
            if (cell.view == null && cell.originalPosition == originalPosition)
                return position;
        }
        return -1;
    }

    public Object getOriginalItem(int position) {
        if (isAd(position))
            return null;
        return this.getModel().getAdapter()
                .getItem(getOriginalPosition(position));
    }

    public long getOriginalItemId(int position) {
        if (isAd(position))
            return -1;

        return this.getModel().getAdapter()
                .getItemId(getOriginalPosition(position));
    }

	/*
	 * Ads management
	 */

    public void clearAds() {
        this.ads.clear();
        setNoAds(true);
        resetCells();
        notifyDataSetChanged();

    }

    public void refreshAds() {
        for (int i = 0; i < ads.size(); i++) {
            ADFNativeAd ad = this.ads.get(i).ad;
            loadAd(ad);
        }
    }

    public void loadAds() {

        setNoAds(false);
        setAdsToPool();
        resetCells();
        notifyDataSetChanged();
    }

    private void setAdsToPool() {
        try {

            int count = Math.min(MAX_POOL_SIZE, this.getModel().getAdsPosition()
                    .getNumberOfAds());
            for (int i = ads.size(); i < count; i++) {
                AdView adView = new AdView();
                adView.ad = getAdView(this.getModel().getListView().getContext());
                if (getModel().getAdsInfo().getAdListener() != null) {
                    adView.loadingView = getModel().getAdsInfo().getAdListener()
                            .onNativeAdBeingLoaded(adView.ad);
                }
                loadAd(adView.ad);
                this.ads.add(adView);
            }
        } catch (Exception ex) {
            Log.e("AdFalconTest", "ADFNativeAdAdapter->setAdsToPool: " + ex.getMessage());
        }
    }

    private AdView getAdView(ADFNativeAd ad) {
        try {
            for (AdView adView : this.ads) {
                if (adView.ad == ad) {
                    return adView;
                }
            }
        } catch (Exception ex) {
            Log.e("AdFalconTest", "ADFNativeAdAdapter->getAdView: " + ex.getMessage());
        }

        return null;
    }

    private ADFNativeAd getAdView(Context context) {

        ADFNativeAd ad = new ADFNativeAd((Activity) context);
        try {

            ad.setLayoutParams(new ListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ad.setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ad;
    }

    private void loadAd(ADFNativeAd ad) {
        try {
            ad.setTesting(this.getModel().getAdsInfo().isTesting());
            ad.loadAd(this.getModel().getAdsInfo().getSiteId(), this.getModel()
                    .getAdsInfo().getParams(), this.getModel().getAdsInfo()
                    .getBinder());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class Cell {
        int originalPosition;
        long id;
        AdView view;
    }

    private class AdView {
        ADFNativeAd ad;
        View failedView;
        View loadingView;

        View getView() {
            if (failedView != null
                    && ad.getStatus() == ADFNativeAdStatus.failed) {
                return failedView;
            } else if (loadingView != null
                    && ad.getStatus() == ADFNativeAdStatus.loading) {
                return loadingView;
            } else {
                return ad;
            }
        }

    }

	/*
	 * Listeners
	 */

    public void setOnItemClickListener(final OnItemClickListener listener) {

        if (listener == null)
            return;

        this.getModel().getListView()
                .setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        if (!isAd(position)) {
                            listener.onItemClick(parent, view,
                                    getOriginalPosition(position), id);
                        }
                    }

                });
    }

    public void setOnItemLongClickListener(
            final OnItemLongClickListener listener) {

        if (listener == null)
            return;

        this.getModel().getListView()
                .setOnItemLongClickListener(new OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {

                        return isAd(position)
                                || listener.onItemLongClick(parent, view,
                                getOriginalPosition(position), id);

                    }
                });
    }

    public void setOnItemSelectedListener(final OnItemSelectedListener listener) {

        if (listener == null)
            return;

        this.getModel().getListView()
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        if (!isAd(position)) {
                            listener.onItemSelected(parent, view,
                                    getOriginalPosition(position), id);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        listener.onNothingSelected(parent);

                    }
                });
    }

	/*
	 * Base Adapter overriden methods
	 */

    public boolean hasStableIds() {
        return this.getModel().getAdapter().hasStableIds();
    }

    public boolean areAllItemsEnabled() {
        return this.getModel().getAdapter().areAllItemsEnabled();
    }

    public boolean isEnabled(int position) {
        return isAd(position)
                || this.getModel().getAdapter()
                .isEnabled(getOriginalPosition(position));
    }

    public int getItemViewType(int position) {
        if (isAd(position))
            return IGNORE_ITEM_VIEW_TYPE;
        return this.getModel().getAdapter()
                .getItemViewType(getOriginalPosition(position));
    }

    public int getViewTypeCount() {
        return this.getModel().getAdapter().getViewTypeCount();
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public int getCount() {
        return this.cells.size();
    }

    @Override
    public Cell getItem(int position) {
        return this.cells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.cells.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cell cell = getItem(position);
        if (cell.view != null) {

            if (getModel().getAdsInfo().getAdListener() != null) {
                getModel().getAdsInfo().getAdListener()
                        .onViewBeingDisplayed(cell.view.getView());
            }

            return cell.view.getView();
        } else {
            return this.getModel().getAdapter()
                    .getView(cell.originalPosition, convertView, parent);
        }

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

	/*
	 * List Methods
	 */

    public void setSelection(int originalPosition) {
        if (this.getModel().getListView() == null || originalPosition < 0
                || originalPosition >= this.getModel().getAdapter().getCount()) {
            return;
        }

        this.getModel().getListView()
                .setSelection(getUnoriginalPosition(originalPosition));
    }

    public void smoothScrollToPosition(int originalPosition) {
        if (this.getModel().getListView() == null || originalPosition < 0
                || originalPosition >= this.getModel().getAdapter().getCount()) {
            return;
        }

        this.getModel()
                .getListView()
                .smoothScrollToPosition(getUnoriginalPosition(originalPosition));
    }

    public Object getItemAtPosition(int position) {

        if (position < 0)
            return null;

        Cell cell = (Cell) getModel().getListView().getItemAtPosition(position);

        if (cell == null || cell.view != null)
            return null;

        int originalPosition = cell.originalPosition;
        return getModel().getAdapter().getItem(originalPosition);

    }

    public long getItemIdAtPosition(int position) {

        if (position < 0)
            return ListView.INVALID_ROW_ID;

        Cell cell = (Cell) getModel().getListView().getItemAtPosition(position);

        if (cell == null || cell.view != null)
            return ListView.INVALID_ROW_ID;

        int originalPosition = cell.originalPosition;
        return getModel().getAdapter().getItemId(originalPosition);
    }

    public int getPositionForView(View view) {

        int headerViewsCount = getModel().getListView().getHeaderViewsCount();
        int position = getModel().getListView().getPositionForView(view);
        try {

            int adapterPos = position - headerViewsCount;

            if (isAd(adapterPos)) {
                return ListView.INVALID_POSITION;
            }

            return getOriginalPosition(adapterPos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return position;
    }

    public View getChildAt(int index) {

        int noAdsCount = 0;
        int testIndex = 0;

        while (noAdsCount < (index + 1)
                && testIndex < getModel().getListView().getChildCount()) {
            View v = getModel().getListView().getChildAt(testIndex++);
            if (!(v instanceof ADFNativeAd))
                noAdsCount++;
        }

        return getModel().getListView().getChildAt(testIndex - 1);

    }

    public int getFirstVisiblePosition() {

        int headerViewsCount = getModel().getListView().getHeaderViewsCount();
        int position = getModel().getListView().getFirstVisiblePosition();
        int adapterPos = position - headerViewsCount;

        while (isAd(adapterPos) && adapterPos < this.cells.size()) {
            adapterPos++;
        }
        return getOriginalPosition(adapterPos) + headerViewsCount;
    }

    public int getLastVisiblePosition() {

        int footerViewsCount = getModel().getListView().getFooterViewsCount();
        int headerViewsCount = getModel().getListView().getHeaderViewsCount();
        int position = getModel().getListView().getLastVisiblePosition();

        int adapterPos = position - headerViewsCount - footerViewsCount;

        while (isAd(adapterPos) && adapterPos >= 0) {
            adapterPos--;
        }

        return getOriginalPosition(adapterPos) + footerViewsCount
                + headerViewsCount;
    }

	/*
	 * getter/setter
	 */

    public ADFNativeAdAdapterModel getModel() {
        return model;
    }

    public void setModel(ADFNativeAdAdapterModel model) {
        this.model = model;

    }

    @Override
    public void onLoadAd(ADFNativeAd ad) {
        if (getModel().getAdsInfo().getAdListener() != null) {
            getModel().getAdsInfo().getAdListener().onNativeAdLoaded(ad);
        }
        notifyDataSetChanged();
    }

    @Override
	public void onFail(ADFNativeAd adfNativeAd, ADFErrorCode errorCode, String errorMessage) {

        if (getModel().getAdsInfo().getAdListener() != null) {
            AdView adView = getAdView(adfNativeAd);
            if (adView != null) {
                adView.failedView = getModel().getAdsInfo().getAdListener()
                        .onNativeAdFailed(adfNativeAd, errorMessage);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean handleCustomAction(String data) {
        return getModel().getAdsInfo().getAdListener() != null && getModel().getAdsInfo().getAdListener().handleCustomAction(data);

    }

    @Override
    public void renderExtraData(View tamplateView,
                                Hashtable<Integer, String> extraData) {
        for (Integer k : extraData.keySet()) {
            String v = extraData.get(k);
            Log.d("AdFalconTest", "key: " + k + ", value: " + v);
        }
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

    private boolean isNoAds() {
        return noAds;
    }

    private void setNoAds(boolean noAds) {
        this.noAds = noAds;
    }

	


}