package com.alllinkshare.core.navigator;

import android.os.Parcelable;

public interface CatalogNavigator extends Parcelable {
    void navigateToCategory(int categoryId, boolean loadAll);
    void navigateToListings(int categoryId);
    void navigateToListing(int listingId, int categoryId);
    void navigateToListingCategories(String type, int listingId, int categoryId);
}