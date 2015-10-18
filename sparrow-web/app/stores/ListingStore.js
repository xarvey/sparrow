const alt = require('../alt');
const ListingActions = require('../actions/ListingActions');
const ListingSource = require('../data/ListingSource');

class ListingStore {
  constructor() {
    this.listings = [];
    this.errorMessage = null;
    this.clicked = null;

    this.bindListeners({
      handleUpdateListings: ListingActions.UPDATE_LISTINGS,
      handleFetchListings: ListingActions.FETCH_LISTINGS,
      handleListingsFailed: ListingActions.LISTINGS_FAILED,
      handleGetListingById: ListingActions.GET_LISTING_BY_ID
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

  handleGetListingById(id) {
    //console.log(this.listings);
    for(let li of this.listings) {
      if(li.id == id) {
        console.log('yes ', li);
        this.clicked = li;
        break;
      }
    }
  }
}

module.exports = alt.createStore(ListingStore, 'ListingStore');
