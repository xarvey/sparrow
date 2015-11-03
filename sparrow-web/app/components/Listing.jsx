import React, { Component, PropTypes } from 'react';
import RequestedItem from '../components/RequestedItem';

class Listing extends Component {

  static propTypes = {
    listings: PropTypes.array.isRequired
  }

//defect
  render() {
    if (this.props.listings.length<1)
    return (
      <div className='list-container'>
          No Listing Yet !!
      </div>
    );

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
