const alt = require('../alt');
const ListingActions = require('../actions/ListingActions');
const ListingSource = require('../data/ListingSource');
const request = require('superagent');

class ListingStore {
  constructor() {
    this.listings = [];
    this.errorMessage = null;
    this.clicked = null;
    this.endPointURL = 'http://vohras.tk:9000';

    this.bindListeners({
      handleUpdateListings: ListingActions.UPDATE_LISTINGS,
      handleFetchListings: ListingActions.FETCH_LISTINGS,
      handleListingsFailed: ListingActions.LISTINGS_FAILED,
      handleGetListingById: ListingActions.GET_LISTING_BY_ID,
      handleGetListingByIds: ListingActions.GET_LISTING_BY_IDS,
      handleDeleteListingByID: ListingActions.DELETE_LISTING_BY_ID
    });

    this.exportAsync(ListingSource);
  }

  handleUpdateListings(listingsObj) {
    this.listings = listingsObj.listings;
    console.log(listingsObj);
    this.errorMessage = null;
    if (listingsObj.id) {
      this.handleGetListingById(listingsObj.id);
    }
  }

  handleFetchListings() {
    this.listings = [];
  }

  handleListingsFailed(errorMessage) {
    this.errorMessage = errorMessage;
  }

  handleGetListingByIds(listid) {
    let ret = []
    for(let li of this.listings) {
      for(let id of listid) {
        if(li.id == id) {
          ret.push(li);
          break;
        }
      }
    }
    this.clicked = ret;
  }

  getcookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return " ";
  }


  handleEditListingByID(id,listingInfo) {
    request
      .put(this.endPointURL + '/listings/')
      .end((err,res) => {
        request
          .put(this.endPointURL + '/listings/')
          .send(listingInfo)
          .set('Authentication',this.getcookie('username')+':'+this.getcookie('password'))
          .end((err, res) => {
            if (err) {
              console.error('delete error!');
              return;
            }
          });
      }

    )
  }

  handleDeleteListingByID(id) {
    request
      .get(this.endPointURL + '/listings/'+id)
      .end((err,res) => {
        //if (res.body.owner==this.getcookie('userid')) defect 20
        request
          .del(this.endPointURL + '/listings/'+id)
          .set('Authentication',this.getcookie('username')+':'+this.getcookie('password'))
          .end((err, res) => {
            if (err) {
              console.error('delete error!');
              return;
            }
          });
      //  else alert("You cannot delete others item");
      }

    )
  }

  handleGetListingById(id) {
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
