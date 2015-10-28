import React, { Component, PropTypes } from 'react';
import { Link }        from 'react-router';
import imageResolver from 'utils/image-resolver';
import ListingActions from '../actions/ListingActions';
import ResponseForm from '../components/ResponseForm';

const request = require('superagent');

let displayPic;
if (process.env.BROWSER) {
  displayPic = require('images/cat.png');
} else {
  displayPic = imageResolver('images/cat.png');
}

class Offer extends Component {

  static propTypes = {
    offer: PropTypes.number.isRequired,
  }

  state = {
    offer: {
      owner: '',
      text: '',
      bounty: ''
    },
    owner: {
      name: ''
    },
    error: false
  }

  componentDidMount() {
    this.getComment(this.props.offer);
  }

  render() {
    if(this.state.error) return <div></div>;
    console.log('refresh');
    return (
      <div className='offer-wrapper'>
        <div>
          <span className='user'>{this.state.owner.name}</span>
          <span className='comment'>{this.state.offer.text}</span>
        </div>
        <div>
          <span className='bounty'>{this.state.offer.bounty}  </span>
        </div>
      </div>
    );
  }

  getComment(id) {
    const endPointURL = 'http://vohras.tk:9000';
    request
      .get(endPointURL + '/comments/' + this.props.offer)
      .end((err, res) => {
        if (err) {
          console.error('comment error!');
          this.setState({error: true});
          return;
        }
        console.log(res);
        this.setState({offer: res.body});
        request
          .get(endPointURL+'/users/'+res.body.owner)
          .end((err, res) => {
            if (err) {
              console.error('fail to fetch users!');
              return;
            }
            this.setState({owner: res.body});
          });
      });
  }

}

export default Offer;
