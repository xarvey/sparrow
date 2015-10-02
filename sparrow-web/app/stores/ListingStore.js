const alt = require('../alt');
const ListingActions = require('../actions/ListingActions');
const ListingSource = require('../data/ListingSource');

class ListingStore {
  constructor() {
    this.listings = [];
    this.errorMessage = null;

    this.bindListeners({
      handleUpdateListings: ListingActions.UPDATE_LISTINGS,
      handleFetchListings: ListingActions.FETCH_LISTINGS,
      handleListingsFailed: ListingActions.LISTINGS_FAILED
    });

    this.exportPublicMethods({
      getListing: this.getListing
    });

    this.exportAsync(ListingSource);
  }

  handleUpdateListings(listings) {
    this.listings = listings;
    this.errorMessage = null;
  }

  handleFetchListings() {
    this.listings = [];
  }

  handleListingsFailed(errorMessage) {
    this.errorMessage = errorMessage;
  }

  getListing(name) {
    const { listings } = this.getState();
    for (let i = 0; i < listings.length; i += 1) {
      if (listings[i].name === name) {
        return listings[i];
      }
    }
    return null;
  }
}

module.exports = alt.createStore(ListingStore, 'ListingStore');
