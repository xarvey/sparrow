import React, { Component } from 'react';
import DocumentTitle from 'react-document-title';

import ListingStore from '../stores/ListingStore';
import Listing from '../components/Listing';
import ResponseForm from '../components/ResponseForm';
const AltContainer = require('alt/AltContainer');

class BorrowListPage extends Component {

  componentDidMount() {
      ListingStore.fetchListings();
  }

  render() {
    let listing = <Listing itemtype='borrow'/>
    if(this.props.type)
      listing = <Listing itemtype='lend'/>
    return (
      <DocumentTitle title='Sparrow'>
        <section className='home-page'>

          <div className='wrapper'>
            <AltContainer store={ ListingStore }>
              {listing}
            </AltContainer>
          </div>

        </section>
      </DocumentTitle>
    );
  }

}

export default BorrowListPage;
