import React, { Component, PropTypes } from 'react';
import imageResolver from 'utils/image-resolver';

let displayPic;
if (process.env.BROWSER) {
  displayPic = require('images/cat.png');
} else {
  displayPic = imageResolver('images/cat.png');
}

class RequestedItem extends Component {

  static propTypes = {
    item: PropTypes.object.isRequired
  }

  render() {
    return (
      <div className='list-wrapper'>
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
      </div>
    );
  }

}

export default RequestedItem;
