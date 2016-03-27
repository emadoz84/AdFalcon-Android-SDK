package com.noqoush.adfalcon.adgallery.adapter;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noqoush.adfalcon.adgallery.R;
import com.noqoush.adfalcon.adgallery.helper.SiteIDHelper;
import com.noqoush.adfalcon.adgallery.model.SiteID;

public class SiteIDsAdapter extends BaseAdapter {

	Context context;
	Vector<SiteID> siteIDs;

	public SiteIDsAdapter(Context context, SiteIDHelper helper) {
		this.context = context;
		siteIDs = new Vector<SiteID>(helper.getSiteIDs());
	}

	@Override
	public int getCount() {

		return siteIDs.size();
	}

	@Override
	public SiteID getItem(int position) {

		return siteIDs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		SiteID siteID = getItem(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView != null) {
			view = convertView;
		} else {
			view = inflater.inflate(R.layout.custom_row_site_id, parent, false);
		}

		TextView nameTV = (TextView) view.findViewById(R.id.textViewName);
		TextView idTV = (TextView) view.findViewById(R.id.textViewSiteID);
		TextView texstingTV = (TextView) view
				.findViewById(R.id.textViewTesting);
		ImageView iconIV = (ImageView) view.findViewById(R.id.imageViewIcon);

		nameTV.setText(siteID.getName());
		idTV.setText(siteID.getSiteID());
		texstingTV.setText((siteID.isTesting() ? this.context
				.getString(R.string.testing) : this.context
				.getString(R.string.production)));
		
		if(siteID.isDefaultSiteID()){
			
			iconIV.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
		}else{
			
			iconIV.getLayoutParams().width = 0;
		}

		return view;
	}

	@Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

}
