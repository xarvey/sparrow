import React, { Component, PropTypes } from 'react';
import RequestedItem from '../components/RequestedItem';
import ListingActions from '../actions/ListingActions';
import FinishedResponse from '../components/FinishedResponse';
import ListingStore from '../stores/ListingStore';
import Offer from '../components/Offer';
const request = require('superagent');

class FinishPage extends Component {

  static propTypes = {
    params: PropTypes.object.isRequired,
  }

  state = {
    offerer: {
      name: ''
    },
    item: ''
  }

  componentDidMount() {
    const endPointURL = 'http://vohras.tk:9000'
    request
      .get(endPointURL+'/users/'+this.props.params.id.split(';')[1])
      .end((err, res) => {
        if (err) {
          console.error('fail to fetch users!');
          return;
        }
        this.setState({offerer: res.body});
      });
  }

  render() {
    console.log("here", this.state);
    let item, form;

    console.log('props', this.props)

    //item = <RequestedItem item={this.props.} showDescription={true} />
    console.log('useridfucking',this.props.params.id.split(';')[1]);
    form = <FinishedResponse id={this.props.params.id.split(';')[1]}/>

    return (
      <div className='comments-wrapper'>
        <div className='modal'>
          <h2>The email has been sent to {this.state.offerer.name}</h2>
          <span>You can contact the person at the email {this.state.offerer.email}</span>
          { form }
        </div>
      </div>
    );
  }

}

export default FinishPage;
