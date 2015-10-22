const mockData = [
  {
    id: 1,
    owner: 'birdy',
    time: '1 min ago',
    title: 'Can I please borrow a nest?',
    duration: '2 hours',
    bounty: '$0-$20',
    comments: [
      {
        comment: 'come to Minar Forest!',
        bounty: '$10',
        user: 'Chief Tatamo'
      },
      {
        comment: 'I won\'t charge you bruh',
        bounty: '$200',
        user: 'Real bro'
      }
    ],
    description: 'I just lost my house due to the recent wildfire. Please halp.'
  },
  {
    id: 2,
    owner: 'eagle',
    time: '3 mins ago',
    title: 'Need a glasses',
    duration: '1 day',
    bounty: 'free',
    comments: [
      {
        comment: 'come to Minar Forest!',
        bounty: '$10',
        user: 'Real bro'
      }
    ],
    description: 'I just lost my house due to the recent wildfire. Please halp.'
  },
  {
    id: 3,
    owner: 'albatross',
    time: '2 days later',
    title: 'I don\'t need you',
    duration: '1 second',
    bounty: '$30,000',
    comments: [
      {
        comment: 'come to Minar Forest!',
        bounty: '$10',
        user: 'Real bro'
      }
    ],
    description: 'I just lost my house due to the recent wildfire. Please halp.'
  }
];

const ListingActions = require('../actions/ListingActions');

const ListingSource = {
  fetchListings() {
    return {
      remote(state, id) {
        console.log('before return', id);
        return new Promise(function(resolve, reject) {
          // simulate an asynchronous flow where data is fetched on
          // a remote server somewhere.
          setTimeout(function() {
            // change this to `false` to see the error action being handled.
            if (mockData.length) {
              // resolve with some mock data
              resolve({ listings: mockData, id: id } );
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
