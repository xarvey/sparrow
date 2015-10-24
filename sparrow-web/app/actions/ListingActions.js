const alt = require('../alt');

class ListingActions {
  updateListings(listingsObj) {
    this.dispatch(listingsObj);
  }

  fetchListings() {
    this.dispatch();
  }

  listingsFailed(errorMessage) {
    this.dispatch(errorMessage);
  }

  getListingById(id) {
    this.dispatch(id);
  }

  getListingByIds(ids) {
    this.dispatch(ids);
  }

}

module.exports = alt.createActions(ListingActions);
