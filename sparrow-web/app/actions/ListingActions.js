const alt = require('../alt');

class ListingActions {
  updateListings(listings) {
    this.dispatch(listings);
  }

  fetchListings() {
    this.dispatch();
  }

  listingsFailed(errorMessage) {
    this.dispatch(errorMessage);
  }

}

module.exports = alt.createActions(ListingActions);
