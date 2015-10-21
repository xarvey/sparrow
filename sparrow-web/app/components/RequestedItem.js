import React, { Component, PropTypes } from 'react';
import { Link }        from 'react-router';
import imageResolver from 'utils/image-resolver';
import ListingActions from '../actions/ListingActions';
import ResponseForm from '../components/ResponseForm';

let displayPic;
if (process.env.BROWSER) {
  displayPic = require('images/cat.png');
} else {
  displayPic = imageResolver('images/cat.png');
}

class RequestedItem extends Component {

  static propTypes = {
    item: PropTypes.object.isRequired,
    showDescription: PropTypes.object
  }

  state = {
    showModal: false,
  }

  render() {
    let modal, des;
    if (this.state.showModal) {
      modal = <ResponseForm item={ this.props.item } hideFunc={ this.hideModal.bind(this) }/>
    }

    if (this.props.showDescription) {
      des = <span className='des'>{ this.props.item.description }</span>
    }
    const detailsLink = '/listing/'+this.props.item.id;

    return (
      <div className='outer-list-wrapper'>
      <Link to={ detailsLink } className='list-wrapper'>
        <left>
          <div className='user-info'>
            <img src={ displayPic } alt='display picture' className='user-pic' />
            <div className='user-text'>
              <h3>{ this.props.item.owner }</h3>
              <p>{ this.props.item.time }</p>
            </div>
          </div>
          <div className='list-text'>{ this.props.item.title }</div>

        </left>
        <right>
          <div className='list-details'>
            <div className='price'>
              { this.props.item.bounty }
            </div>
          </div>
          <button className='btn-lend'>LEND</button>
        </right>
      </Link>
      { des }
      </div>
    );
  }

  hideModal() {
    this.setState({showModal: false, showComments: false})
  }

}

export default RequestedItem;
