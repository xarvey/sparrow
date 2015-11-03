import React, { Component, PropTypes } from 'react';
import RequestedItem from '../components/RequestedItem';
import ListingActions from '../actions/ListingActions';
import FinishedResponse from '../components/FinishedResponse';
import ListingStore from '../stores/ListingStore';
import Offer from '../components/Offer';
const request = require('superagent');
const $ = require('jquery');


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
        let item = this.props.params.id.split(';')[0];
        this.setState({offerer: res.body});
        let x = localStorage.getItem('deleted')
        if(x)
          localStorage.setItem('deleted', x.push(item));
        else {
          localStorage.setItem('deleted', [item])
        }

        $.ajax({
          type: "POST",
          url: "https://mandrillapp.com/api/1.0/messages/send.json",
          data: {
            'key': 'YySUIA7atER6dWvzIgHCiw',
            'message': {
              'from_email': 'Sparrow@purdue.edu',
              'to': [
                  {
                    'email': this.state.offerer.email,
                    'type': 'to'
                  }
                ],
              'autotext': 'true',
              'subject': 'Someone has posted on your listings',
              'html': 'Please check at http://localhost:3002/listing/'+item
            }
          }
        });
      });
    }


  render() {
    console.log("here", this.state);
    let item, form;

    console.log('props', this.props)

    //item = <RequestedItem item={this.props.} showDescription={true} />
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
