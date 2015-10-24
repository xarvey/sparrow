import React, { Component, PropTypes } from 'react';
import ListingActions from 'actions/ListingActions';
import imageResolver from 'utils/image-resolver';
import ListingStore from 'stores/ListingStore';
import RequestedItem from 'components/RequestedItem';

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
  lendListings: [],
}

class ProfilePage extends Component {

  static propTypes = {
    params: PropTypes.object.isRequired
  }

  state = {
    user: null,
    items: null
  }

  componentWillMount() {

    //this.setState({items: ListingActions.getListingByIds(this.state.user.lendListings)});
  }

  componentDidMount() {
    this.setState({user: mockUsers[this.props.params.id-1]});
    ListingStore.listen(this._onChange.bind(this));
    ListingStore.fetchListings();
    console.log('items',this.state.items);
  }

  _onChange() {
    let listings = ListingStore.getState().listings;
    console.log('change inside profile!', this.state.user, listings);
    if(listings) {
      let ret = [];
      for(let li of listings) {
        for(let id of this.state.user.lendListings) {
          if(li.id == id) {
            ret.push(li);
            break;
          }
        }
      }
      this.setState({items: ret});
    }
    //this.forceUpdate();
  }

  render() {
    console.log("here", this.state);
    let user = this.state.user
    return (
      <div className='profile-wrapper'>
        <img src={ displayPic } alt='display picture' className='user-pic' />
        <h2>{ user.name }</h2>
        <span> Location: {user.zipCode} </span>
        <div className='modal'>
          <div className='list-container'>
            {
              this.state.items.map( item => {
                return <RequestedItem item={item}/>;
              })
            }
          </div>
        </div>
      </div>
    );
  }

}

export default ProfilePage;
