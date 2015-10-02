import React, { Component } from 'react';
import DocumentTitle from 'react-document-title';

import RequestedItem from '../components/RequestedItem';
import ListingStore from '../stores/ListingStore';
import Listing from '../components/Listing';
const AltContainer = require('alt/AltContainer');

class BorrowListPage extends Component {

  componentDidMount() {
    ListingStore.fetchListings();
  }

  mockList() {
    return (
      <div className='list-container'>
        {
          this.state.mockData.map((item) => {
            return <RequestedItem item={ item }/>;
          })
        }
      </div>
    );
  }

  render() {
    return (
      <DocumentTitle title='Sparrow'>
        <section className='home-page'>

          <div className='wrapper'>
            <AltContainer store={ ListingStore }>
              <Listing/>
            </AltContainer>
          </div>

        </section>
      </DocumentTitle>
    );
  }

}

export default BorrowListPage;
