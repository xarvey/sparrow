import React, { Component, PropTypes } from 'react';
import RequestedItem from '../components/RequestedItem';

class Listing extends Component {

  static propTypes = {
    listings: PropTypes.array.isRequired
  }

  render() {
    return (
      <div className='list-container'>
        {
          this.props.listings.map((item) => {
            return <RequestedItem item={ item }/>;
          })
        }
      </div>
    );
  }
}

export default Listing;
