package com.noqoush.adfalcon.adgallery.fragments.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noqoush.adfalcon.adgallery.R;
import com.noqoush.adfalcon.adgallery.model.AdGroup;
import com.noqoush.adfalcon.adgallery.model.AdsModel;

public class AdUnitSizesAdapter extends BaseAdapter {

	private Context context;

	public AdUnitSizesAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		int count = AdsModel.getInstance(this.context).GROUPS.size();
		/*
		 * for (AdGroup group : AdsModel.GROUPS) { count +=
		 * group.getSizes().size(); }
		 */
		return count;
	}

	@Override
	public Object getItem(int position) {
		/*
		 * int index = 0; for (AdGroup group : AdsModel.GROUPS) { if (position
		 * == index++) { return group; } for (AdUnitSize size :
		 * group.getSizes()) { if (position == index++) { return size; } } }
		 */
		return AdsModel.getInstance(this.context).GROUPS.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		AdGroup group = (AdGroup) getItem(position);
		String title = "";
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		view = inflater
				.inflate(R.layout.custom_row_ad_unit_size, parent, false);
		title = group.getTitle();

		TextView textView = (TextView) view.findViewById(R.id.textViewTitle);
		textView.setText(title);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageViewIcon);
		imageView.setImageResource(group.getResID());
		return view;
	}

	@Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

}
