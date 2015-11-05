const request = require('superagent');

const mockData = [
  {
    id: 1,
    owner: 'birdy',
    time: '1 min ago',
    title: 'Can I please borrow a nest?',
    duration: '2 hours',
    bounty: '$0-$20',
    tags: ['nest', 'halp', 'boo' ],
    comments: [
      {
        comment: 'come to Minar Forest!',
        bounty: '$10',
        user: 'eagle',
        userid: 1
      },
      {
        comment: 'I won\'t charge you bruh',
        bounty: '$200',
        user: 'albatross',
        userid: 3
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
    tags: ['glasses', 'vision'],
    comments: [
      {
        comment: 'come to Minar Forest!',
        bounty: '$10',
        user: 'albatross',
        userid: 3
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
    tags: ['glasses', 'vision'],
    comments: [
      {
        comment: 'come to Minar Forest!',
        bounty: '$10',
        user: 'eagle',
        userid: 2
      }
    ],
    description: 'I just lost my house due to the recent wildfire. Please halp.'
  }
];

const ListingActions = require('../actions/ListingActions');
const endPoint='http://vohras.tk:9000';
const ListingSource = {
  fetchListings() {
    return {
      remote(state, id) {
        console.log('before return', id);
        return new Promise(function(resolve, reject) {
          // simulate an asynchronous flow where data is fetched on
          // a remote server somewhere.
          //setTimeout(function() {

            // change this to `false` to see the error action being handled.
            let realData=[];

            request
              .get(endPoint+'/frontpage/borrow/0')
              .end((err, res) => {
                if (err) {
                  console.error('fail to fetch listings!');
                  return;
                }
                realData=res.body;

            request
              .get(endPoint+'/frontpage/lend/0')
              .end((err, res) => {
                if (err) {
                  console.error('fail to fetch listings!');
                  return;
                }
                console.log('concat',res.body);
                realData = realData.concat(res.body);
                console.log('real doeta', realData)
                if (realData.length) {
                  // resolve with some mock data
                  console.log('real doeta', realData)
                  let count = 0
                  for (let i=0; i<realData.length; i++)
                    {
                      let x = i;
                      request
                        .get(endPoint+'/users/'+realData[x].owner)
                        .end((err, res) => {
                          console.log(x,realData);
                          if (err) {
                            console.error('fail to fetch listings!');
                            return;
                          }
                          realData[x].ownerName=res.body.name;
                          count++;
                          if(count == realData.length)
                            resolve({ listings: realData, id: id } );
                        });
                    }
                    console.log(realData);
                } else {
                  reject('No data');
                }
              });
            });
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
