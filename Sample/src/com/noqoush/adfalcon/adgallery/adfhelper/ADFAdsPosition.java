package com.noqoush.adfalcon.adgallery.adfhelper;


public class ADFAdsPosition {

	private int numberOfAds = 3;
	private int start = 5;
	private int space = 20;
	private boolean inverse = false;


	public int getNumberOfAds() {
		return numberOfAds;
	}

	public void setNumberOfAds(int numberOfAds) {
		this.numberOfAds = numberOfAds;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public boolean isInverse() {
		return inverse;
	}

	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

}
