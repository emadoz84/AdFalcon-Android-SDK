package com.noqoush.adfalcon.adgallery.helper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noqoush.adfalcon.adgallery.R;
import com.noqoush.adfalcon.adgallery.model.SiteID;
import com.noqoush.adfalcon.adgallery.util.JSONUtil;
import com.noqoush.adfalcon.adgallery.util.SharedReferenceUtil;

public class SiteIDHelper {

	public static final String REFERENCES_SITE_IDS = "SITE_IDS";

	private Context context;
	private Vector<SiteID> siteIDs = new Vector<SiteID>();
	private Vector<String> titleSiteIDs = new Vector<String>();

	public SiteIDHelper(Context context) {
		setContext(context);
	}

	public boolean removeSiteID(SiteID pSiteID) {
		try {
			Vector<SiteID> ids = getSiteIdsFromPreferences();
			if (ids.contains(pSiteID)) {
				ids.remove(pSiteID);
			} else {
				return false;
			}

			Type listType = new TypeToken<List<SiteID>>() {
			}.getType();
			String dataAsJson = new Gson().toJson(ids, listType);
			SharedReferenceUtil.put(getContext(), REFERENCES_SITE_IDS,
					dataAsJson);
			
			resetList();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}

	private void resetList() {
		siteIDs.clear();
		titleSiteIDs.clear();
	}

	public boolean saveSiteID(SiteID pSiteID) {
		try {
			Vector<SiteID> ids = getSiteIdsFromPreferences();
			if (ids.contains(pSiteID)) {
				ids.remove(pSiteID);
				ids.insertElementAt(pSiteID, 0);
			} else {
				ids.add(pSiteID);
			}

			Type listType = new TypeToken<List<SiteID>>() {
			}.getType();
			String dataAsJson = new Gson().toJson(ids, listType);
			SharedReferenceUtil.put(getContext(), REFERENCES_SITE_IDS,
					dataAsJson);
			resetList();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}

	public Vector<SiteID> getSiteIdsFromPreferences() {
		Vector<SiteID> ids = new Vector<SiteID>();
		try {
			String json = SharedReferenceUtil.getValue(context,
					REFERENCES_SITE_IDS);
			Type listType = new TypeToken<List<SiteID>>() {
			}.getType();
			List<SiteID> results = JSONUtil.fromJSON(json, listType);
			if(results != null)
			for (SiteID sid : results) {
				ids.add(sid);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ids;
	}

	public List<SiteID> getSiteIDs() {
		fillSiteIDs();
		return this.siteIDs;
	}

	private void fillSiteIDs() {
		if (this.siteIDs.size() == 0) {
			String[] ids = getContext().getResources().getStringArray(
					R.array.site_ids);
			for (String id : ids) {
				SiteID siteID = new SiteID(id);
				this.siteIDs.add(siteID);
				this.titleSiteIDs.add(siteID.getTitle());
			}
			
			//Interactive video pro
			SiteID siteID = new SiteID(getContext().getString(R.string.ivideo_site_id_pro));
			this.siteIDs.add(siteID);
			this.titleSiteIDs.add(siteID.getTitle());
			
			//Interactive video test
			siteID = new SiteID(getContext().getString(R.string.ivideo_site_id_test));
			this.siteIDs.add(siteID);
			this.titleSiteIDs.add(siteID.getTitle());

			
			Vector<SiteID> siteids = getSiteIdsFromPreferences();
			for (SiteID siteid : siteids) {
				this.siteIDs.add(siteid);
				this.titleSiteIDs.add(siteid.getTitle());
			}

		}
	}

	public List<String> getTitleSiteIDs() {
		fillSiteIDs();
		return this.titleSiteIDs;
	}

	private Context getContext() {
		return context;
	}

	private void setContext(Context context) {
		this.context = context;
	}

}
