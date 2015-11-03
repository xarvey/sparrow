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
    item: PropTypes.number.isRequired,
    owner: PropTypes.number.isRequired
  }

  state = {
    offer: {
      owner: '',
      text: '',
      bounty: ''
    },
    owner: {
      name: '',
      id: ''
    },
    error: false
  }

  componentDidMount() {
    this.getComment(this.props.offer);
  }

  getcookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return " ";
  }

  render() {
    if(this.state.error) return <div></div>;
    console.log('refresh');
    let url = '/finish/'+this.props.item+';'+this.state.owner.id;
    console.log('propsid',this.props.owner);
    if(this.props.owner != this.getcookie('userid'))
      url = 'javascript:;';
    return (
      <Link to={url} className='offer-wrapper'>
        <div>
          <span className='user'>{this.state.owner.name}</span>
          <span className='comment'>{this.state.offer.text}</span>
        </div>
        <div>
          <span className='bounty'>{this.state.offer.bounty}  </span>
        </div>
      </Link>
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
