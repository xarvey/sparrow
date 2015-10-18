import React, { Component, PropTypes } from 'react';
import RequestedItem from '../components/RequestedItem';
import ListingActions from '../actions/ListingActions';
import ResponseForm from '../components/ResponseForm';
import ListingStore from '../stores/ListingStore';

class ListingDetails extends Component {

  static propTypes = {
    params: PropTypes.object.isRequired
  }

  state = ListingStore.getState();

  componentWillMount() {
    ListingStore.listen(this._onChange);
    ListingActions.getListingById(this.props.params.id);
    this.state = ListingStore.getState();
  }

  _onChange() {
    console.log('change!');
    this.state = ListingStore.getState();
    console.log(this.state.clicked);
  }

  render() {
    console.log("here", this.state);
    let item;
    if(this.state.clicked) {
      item = <RequestedItem item={this.state.clicked} />
    }
    return (
      <div className='comments-wrapper'>
        <h2>All lending offers for this listing</h2>
        <div className='modal'>
          { item }
          <div className='comment-container'>

          </div>
        </div>
      </div>
    );
  }

}

export default ListingDetails;
