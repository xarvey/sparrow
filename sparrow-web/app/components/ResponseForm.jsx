import React, { Component, PropTypes } from 'react';
import RequestedItem from './RequestedItem';


class ResponseForm extends Component {

  static propTypes = {
    item: PropTypes.object.isRequired,
    hideFunc: PropTypes.object
  }

  render() {
    return (
      <div className='res-wrapper'>
        <h2>Lend an item</h2>
        <div className='modal'>
          <RequestedItem item={ this.props.item } />
          <textarea cols='50' rows='4' className='textbox' id='message' placeholder='Message'/>
          <input type='text' className='textbox' id = 'bounty' placeholder = 'Bounty'/>
          <div className="row">
            <button type='button' className='offer'>Post</button>
            <button type='button' className='cancel-offer' onClick={ this.props.hideFunc }>Cancel</button>
          </div>
        </div>
      </div>
    );
  }

}

export default ResponseForm;
