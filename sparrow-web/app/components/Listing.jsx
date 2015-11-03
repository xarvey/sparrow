import React, { Component, PropTypes } from 'react';
import RequestedItem from '../components/RequestedItem';

class Listing extends Component {

  static propTypes = {
    listings: PropTypes.array.isRequired,
    itemtype: PropTypes.object.isRequired
  }

  render() {
    return (
      <div className='list-container'>
        {
          this.props.listings.filter((item) => {
            console.log('item',item.type, this.props.itemtype);
            return item.type == this.props.itemtype;
          }).map((item) => {
            return <RequestedItem item={ item }/>;
          })
        }
      </div>
    );
  }
}

export default Listing;
