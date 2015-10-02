const mockData = [
  {
    name: 'birdy',
    time: '1 min ago',
    text: 'Can I please borrow a nest?',
    duration: '2 hours',
    price: '$0-$20'
  },
  {
    name: 'eagle',
    time: '3 mins ago',
    text: 'Need a glasses',
    duration: '1 day',
    price: 'free'
  },
  {
    name: 'albatross',
    time: '2 days later',
    text: 'I don\'t need you',
    duration: '1 second',
    price: '$30,000'
  }
];

const ListingActions = require('../actions/ListingActions');

const ListingSource = {
  fetchListings() {
    return {
      remote() {
        return new Promise(function(resolve, reject) {
          // simulate an asynchronous flow where data is fetched on
          // a remote server somewhere.
          setTimeout(function() {
            // change this to `false` to see the error action being handled.
            if (mockData.length) {
              // resolve with some mock data
              resolve(mockData);
            } else {
              reject('Things have broken');
            }
          }, 250);
        });
      },
      local() {
        // Never check locally, always fetch remotely.
        return null;
      },

      success: ListingActions.updateListings,
      error: ListingActions.listingsFailed,
      loading: ListingActions.fetchListings
    };
  }
};

module.exports = ListingSource;
