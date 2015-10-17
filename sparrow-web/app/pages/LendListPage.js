import React, { Component } from 'react';
import DocumentTitle from 'react-document-title';

import ListingStore from '../stores/ListingStore';
import Listing from '../components/Listing';

class LendListPage extends Component {

  componentDidMount() {
    ListingStore.fetchListings();
  }

  render() {
    return (
      <DocumentTitle title='Sparrow'>
        <section className='home-page'>

          <div className='wrapper'>
            Coming soon!
          </div>

        </section>
      </DocumentTitle>
    );
  }

}

export default LendListPage;
