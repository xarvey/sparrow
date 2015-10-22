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

class Offer extends Component {

  static propTypes = {
    offer: PropTypes.object.isRequired,
  }

  render() {

    return (
      <div className='offer-wrapper'>
        <div>
          <span className='user'>{this.props.offer.user}</span>
          <span className='comment'>{this.props.offer.comment}</span>
        </div>
        <div>
          <span className='bounty'>{this.props.offer.bounty}  </span>
        </div>
      </div>
    );
  }

}

export default Offer;
