import React, { Component, PropTypes } from 'react';
import ListingActions from 'actions/ListingActions';
import imageResolver from 'utils/image-resolver';
import ListingStore from 'stores/ListingStore';
import RequestedItem from 'components/RequestedItem';
import UserComment from 'components/UserComment';

const request = require('superagent');

let displayPic;
if (process.env.BROWSER) {
  displayPic = require('images/cat.png');
} else {
  displayPic = imageResolver('images/cat.png');
}

const mockUsers = [
  {
    id: 1,
    name: 'birdy',
    zipCode: '47906',
    lendListings: [1,2],
  },
  {
    id: 2,
    name: 'eagle',
    zipCode: '10422',
    lendListings: [2,3],
  },
  {
    id: 3,
    name: 'eagle',
    zipCode: '10422',
    lendListings: [3],
  }
]

const emptyUser = {
  id: -1,
  name: '',
  zipCode: '',
  borrowListings: [],
}

class ProfilePage extends Component {

  static propTypes = {
    params: PropTypes.object.isRequired
  }

  state = {
    user: null,
    items: null,
    location: '',
  }

  componentWillMount() {

    //this.setState({items: ListingActions.getListingByIds(this.state.user.lendListings)});
  }

  componentDidMount() {
    const endPointURL = 'http://vohras.tk:9000'
    request
      .get(endPointURL+'/users/'+this.props.params.id)
      .end((err, res) => {
        if (err) {
          console.error('fail to fetch users!');
          return;
        }
        this.setState({user: res.body});
        request
          .get('http://api.zippopotam.us/us/' + res.body.zipCode)
          .end((err, res) => {
            let place = res.body.places[0];
            this.setState({location: place['place name'] + ', '+ place['state abbreviation']});
          });
        ListingStore.fetchListings();
      });
    ListingStore.listen(this._onChange.bind(this));
    console.log('items',this.state.items);
  }

  _onChange() {
    let listings = ListingStore.getState().listings;
    console.log('change inside profile!', this.state.user, listings);
    if(listings) {
      let ret = [];
      /*for(let li of listings) {
        for(let id of this.state.user.borrowListings) {
          if(li.id == id) {
            ret.push(li);
            break;
          }
        }
      }*/
      ret = listings.filter( (item) => {
        return item.owner == this.props.params.id;
      });
      console.log("SET ITEM STATES");
      this.setState({items: ret});
    }
    //this.forceUpdate();
  }

  renderComments(user) {
    if(!user)
      return;
    let rawComments = user.comments;
    let itemid = this.props.params.id;
    console.log(itemid);
    return (
      <div className='comments-container'>
        {
          rawComments.map( (c) => {
            console.log('itemid',itemid);
            return <UserComment offer={c} item={itemid} owner={this.props.params.id}/>;
          })
        }
      </div>
    );
  }

  render() {
    console.log("here", this.state);
    let user = this.state.user
    if(!user) return <div></div>

    return (
      <div className='profile-wrapper'>
        <img src={ displayPic } alt='display picture' className='user-pic' />
        <h2>{ user.name }</h2>
        <span> { this.state.location } </span>
        <div className='modal'>
          <div className='list-container'>
            {
              this.state.items.map( item => {
                return <RequestedItem item={item}/>;
              })
            }
          </div>
        </div>
        <br/>
          <br/>
          <div className='modal'>
          { this.renderComments(user) }
        </div>
      </div>
    );
  }

}

export default ProfilePage;
