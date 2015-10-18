import React, { Component, PropTypes } from 'react';

class ResponseForm extends Component {

  static propTypes = {
    item: PropTypes.object.isRequired,
  }

  render() {
    return (
      <div className='modal-res'>
        <textarea cols='50' rows='4' className='textbox' id='message' placeholder='Message'/>
        <input type='text' className='textbox' id = 'bounty' placeholder = 'Bounty'/>
        <button type='button' className='offer'>Post</button>
      </div>
    );
  }

}

export default ResponseForm;
