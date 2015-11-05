import React, { Component } from 'react';
import DocumentTitle from 'react-document-title';

import ListingStore from '../stores/ListingStore';
import Listing from '../components/Listing';
import BorrowListPage from '../pages/BorrowListPage'

class LendListPage extends Component {


  render() {
    return (
      <BorrowListPage type='lend'/>
    );
  }

}

export default LendListPage;
