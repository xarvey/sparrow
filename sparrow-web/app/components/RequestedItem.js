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
    item: PropTypes.object.isRequired
  }

  state = {
    showModal: false,
    showComments: false
  }

  render() {
    let modal;
    if (this.state.showModal) {
      modal = <ResponseForm item={ this.props.item } hideFunc={ this.hideModal.bind(this) }/>
    }

    const detailsLink = '/listing/'+this.props.item.id;

    return (
      <Link to={ detailsLink } className='list-wrapper' onClick={this.clickItem.bind(this)}>
        { modal }
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
            <button className='btn-lend' onClick={ this.clickLend.bind(this) }>LEND</button>
          </right>
      </Link>
    );
  }

  clickItem(e) {

  }

  clickLend(event) {
    event.stopPropagation();
    this.setState({showModal: true})
  }

  hideModal() {
    this.setState({showModal: false, showComments: false})
  }

}

export default RequestedItem;
