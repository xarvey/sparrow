import React, { Component, PropTypes } from 'react';
import RequestedItem from '../components/RequestedItem';
import ListingActions from '../actions/ListingActions';
import ResponseForm from '../components/ResponseForm';
import ListingStore from '../stores/ListingStore';
import Offer from '../components/Offer';
const request = require('superagent');

class ListingDetails extends Component {

  static propTypes = {
    params: PropTypes.object.isRequired
  }

  state = {}

  componentWillMount() {
    ListingStore.listen(this._onChange.bind(this));
    ListingActions.getListingById(this.props.params.id);
    //this.state = ListingStore.getState();
  }

  componentDidMount() {
    console.log('now',this.state.listings);
    if(this.state.listings.length == 0) {
      console.log('before call', this.props.params.id);
      ListingStore.fetchListings(this.props.params.id);
    }
  }

  componentWillUnmount() {
    ListingStore.unlisten(this._onChange.bind(this));
  }

  _onChange() {
    console.log('change!');
    this.setState(ListingStore.getState());
    console.log(this.state.clicked);
    //this.forceUpdate();
  }

  offerClicked() {
    console.log('wtf');
  }

  renderComments() {
    if(!this.state.clicked)
      return;
    let rawComments = this.state.clicked.comments;
    let itemid = this.props.params.id;
    console.log(itemid);
    return (
      <div className='comments-container'>
        {
          rawComments.map( (c) => {
            console.log('itemid',itemid);
            return <Offer offer={c} item={itemid} owner={this.state.clicked.owner}/>;
          })
        }
      </div>
    );
  }

  render() {
    console.log("here", this.state);
    let item, form;
    if(this.state.clicked) {
      item = <RequestedItem item={this.state.clicked} showDescription={true} />
      form = <ResponseForm item={ this.state.clicked }/>
    }
    return (
      <div className='comments-wrapper'>
        <h2>All lending offers for this listing</h2>
        <div className='modal'>
          <button onClick={this.delete.bind(this)}>Delete</button>
          { item }
          { this.renderComments() }
          { form }
        </div>
      </div>
    );
  }

  delete() {
    ListingActions.deleteListingById(this.props.params.id);
    window.location.href = '/borrow';
  }

}

export default ListingDetails;
