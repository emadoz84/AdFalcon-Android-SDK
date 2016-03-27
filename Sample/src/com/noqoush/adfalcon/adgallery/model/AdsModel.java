package com.noqoush.adfalcon.adgallery.model;

import java.util.Vector;

import android.content.Context;

import com.noqoush.adfalcon.adgallery.R;

public class AdsModel {

    // Pre-defined site ids
    public String INTERACTIVE_VIDEO_SITE_ID;

    // Ad TYPES
    public AdType TYPE_BANNER;
    public AdType TYPE_TEXT;
    public AdType TYPE_PLAIN_HTML;
    public AdType TYPE_RICH_MEDIA;
    public AdType TYPE_ALL;

    public Vector<AdType> TYPES = new Vector<>();

    // Ad unit sizes
    public AdUnitSize SIZE_AUTO;
    public AdUnitSize SIZE_320x50;
    public AdUnitSize SIZE_300x50;
    public AdUnitSize SIZE_216x36;
    public AdUnitSize SIZE_168x28;
    public AdUnitSize SIZE_120x20;
    public AdUnitSize SIZE_468x60;
    public AdUnitSize SIZE_728x90;
    public AdUnitSize SIZE_300x250;
    public AdUnitSize SIZE_120x600;
    public AdUnitSize SIZE_INTERSTITIAL_PHONE;
    public AdUnitSize SIZE_INTERSTITIAL_TABLET;
    public AdUnitSize SIZE_INTERSTITIAL_AUTO;

    public Vector<AdUnitSize> SIZES_TEXT;
    public Vector<AdUnitSize> SIZES_BANNER;
    public Vector<AdUnitSize> SIZES_BANNER_RM;
    public Vector<AdUnitSize> SIZES_BANNER_IVIDEO;
    public Vector<AdUnitSize> SIZES_INTERSTITIAL;

    // Groups
    public AdGroup GROUP_BANNER;
    public AdGroup GROUP_TEXT;
    public AdGroup GROUP_HTML;
    public AdGroup GROUP_NATIVE_AD;
    public AdGroup GROUP_RM;
    public AdGroup GROUP_IVIDEO;
    public AdGroup GROUP_INTERSTITIAL;
    public AdGroup GROUP_INTERSTITIAL_IVIDEO;
    public AdGroup GROUP_NATIVE_AD_LIST;

    public Vector<AdGroup> GROUPS = new Vector<>();

    private void init() {

        TYPES = new Vector<>();
        SIZES_TEXT = new Vector<>();
        SIZES_BANNER = new Vector<>();
        SIZES_BANNER_RM = new Vector<>();
        SIZES_BANNER_IVIDEO = new Vector<>();
        SIZES_INTERSTITIAL = new Vector<>();

        // Pre-defined site ids
        INTERACTIVE_VIDEO_SITE_ID = getString(R.string.ivideo_site_id_pro);

        // Ad TYPES
        TYPE_BANNER = new AdType(getString(R.string.banner), "b");
        TYPE_TEXT = new AdType(getString(R.string.text), "t");
        TYPE_PLAIN_HTML = new AdType(getString(R.string.plain_html), "ph");
        TYPE_RICH_MEDIA = new AdType(getString(R.string.rich_media), "rm");
        TYPE_ALL = new AdType(getString(R.string.all), "b,t,ph,rm");

        {
            TYPES.add(TYPE_BANNER);
            TYPES.add(TYPE_TEXT);
            TYPES.add(TYPE_PLAIN_HTML);
            TYPES.add(TYPE_RICH_MEDIA);
        }

        // Ad unit sizes
        SIZE_AUTO = new AdUnitSize(getString(R.string.auto), "");
        SIZE_320x50 = new AdUnitSize(getString(R.string.size_320x50), "10");
        SIZE_300x50 = new AdUnitSize(getString(R.string.size_300x50), "2");
        SIZE_216x36 = new AdUnitSize(getString(R.string.size_216x36), "3");
        SIZE_168x28 = new AdUnitSize(getString(R.string.size_168x28), "4");
        SIZE_120x20 = new AdUnitSize(getString(R.string.size_120x20), "5");
        SIZE_468x60 = new AdUnitSize(getString(R.string.size_468x60), "6");
        SIZE_728x90 = new AdUnitSize(getString(R.string.size_728x90), "7");
        SIZE_300x250 = new AdUnitSize(getString(R.string.size_300x250), "8");
        SIZE_120x600 = new AdUnitSize(getString(R.string.size_120x600), "9");
        SIZE_INTERSTITIAL_PHONE = new AdUnitSize(getString(R.string.phone),
                "11");
        SIZE_INTERSTITIAL_TABLET = new AdUnitSize(getString(R.string.tablet),
                "12");
        SIZE_INTERSTITIAL_AUTO = new AdUnitSize(getString(R.string.auto), "13");

        {

            SIZES_BANNER.add(SIZE_AUTO);
            SIZES_BANNER.add(SIZE_320x50);
            SIZES_BANNER.add(SIZE_300x50);
            SIZES_BANNER.add(SIZE_216x36);
            SIZES_BANNER.add(SIZE_168x28);
            SIZES_BANNER.add(SIZE_120x20);
            SIZES_BANNER.add(SIZE_468x60);
            SIZES_BANNER.add(SIZE_728x90);
            SIZES_BANNER.add(SIZE_300x250);
            SIZES_BANNER.add(SIZE_120x600);

            SIZES_TEXT.add(SIZE_AUTO);
            SIZES_TEXT.add(SIZE_320x50);
            SIZES_TEXT.add(SIZE_300x50);
            SIZES_TEXT.add(SIZE_216x36);
            SIZES_TEXT.add(SIZE_168x28);
            SIZES_TEXT.add(SIZE_120x20);
            SIZES_TEXT.add(SIZE_468x60);
            SIZES_TEXT.add(SIZE_728x90);
            SIZES_TEXT.add(SIZE_300x250);

            SIZES_INTERSTITIAL.add(SIZE_INTERSTITIAL_AUTO);

            SIZES_BANNER_RM.add(SIZE_AUTO);
            SIZES_BANNER_RM.add(SIZE_320x50);
            SIZES_BANNER_RM.add(SIZE_300x50);
            SIZES_BANNER_RM.add(SIZE_468x60);
            SIZES_BANNER_RM.add(SIZE_728x90);
            SIZES_BANNER_RM.add(SIZE_300x250);

            SIZES_BANNER_IVIDEO.add(SIZE_AUTO);
            SIZES_BANNER_IVIDEO.add(SIZE_320x50);
            SIZES_BANNER_IVIDEO.add(SIZE_300x50);
            SIZES_BANNER_IVIDEO.add(SIZE_468x60);

			/*
             * for (AdUnitSize size : SIZES_BANNER) { size.setTypes(TYPES); }
			 * 
			 * for (AdUnitSize size : SIZES_INTERSTITIAL) {
			 * size.setTypes(TYPES); }
			 */
        }

        // Groups
        GROUP_BANNER = new AdGroup(getString(R.string.banner), TYPE_BANNER, R.drawable.ic_banner);
        GROUP_TEXT = new AdGroup(getString(R.string.text), TYPE_TEXT, R.drawable.ic_text_ad);
        GROUP_HTML = new AdGroup(getString(R.string.plain_html),
                TYPE_PLAIN_HTML, R.drawable.ic_html);
        GROUP_RM = new AdGroup(getString(R.string.expandable_banner),
                TYPE_RICH_MEDIA, R.drawable.ic_expandable);
        GROUP_IVIDEO = new AdGroup(getString(R.string.ivideo), TYPE_RICH_MEDIA,
                INTERACTIVE_VIDEO_SITE_ID, R.drawable.ic_ivideo);
        GROUP_INTERSTITIAL = new AdGroup(getString(R.string.interstitial),
                TYPE_ALL, R.drawable.ic_interstitial);
        GROUP_INTERSTITIAL_IVIDEO = new AdGroup(
                getString(R.string.interstitial_ivideo), TYPE_RICH_MEDIA,
                INTERACTIVE_VIDEO_SITE_ID, R.drawable.ic_interactive_ivideo);
        GROUP_NATIVE_AD = new AdGroup(getString(R.string.native_ad), TYPE_ALL, R.drawable.ic_native_ad);
        GROUP_NATIVE_AD_LIST = new AdGroup(getString(R.string.native_ad_list), TYPE_ALL, R.drawable.ic_native_list);

        {
            GROUP_BANNER.setSizes(SIZES_BANNER);
            GROUP_TEXT.setSizes(SIZES_TEXT);
            GROUP_HTML.setSizes(SIZES_BANNER);
            GROUP_RM.setSizes(SIZES_BANNER_RM);
            GROUP_IVIDEO.setSizes(SIZES_BANNER_IVIDEO);
            GROUP_INTERSTITIAL.setSizes(SIZES_INTERSTITIAL);
            GROUP_INTERSTITIAL_IVIDEO.setSizes(SIZES_INTERSTITIAL);

            GROUPS.add(GROUP_BANNER);
            GROUPS.add(GROUP_TEXT);
            GROUPS.add(GROUP_HTML);
            GROUPS.add(GROUP_RM);
            GROUPS.add(GROUP_IVIDEO);
            GROUPS.add(GROUP_INTERSTITIAL);
            GROUPS.add(GROUP_INTERSTITIAL_IVIDEO);
            GROUPS.add(GROUP_NATIVE_AD);
            GROUPS.add(GROUP_NATIVE_AD_LIST);
        }

    }

    private Context activity;

    public AdsModel(Context activity) {
        setActivity(activity);
        init();
    }

    private Context getActivity() {
        return activity;
    }

    private void setActivity(Context activity) {
        this.activity = activity;
    }

    private static AdsModel INSTANCE;

    public static AdsModel getInstance(Context activity) {
        if (INSTANCE == null) {
            INSTANCE = new AdsModel(activity);
        }
        INSTANCE.setActivity(activity);
        return INSTANCE;
    }

    private String getString(int id) {
        return getActivity().getString(id);
    }

}
