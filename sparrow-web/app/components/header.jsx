import React, { Component, PropTypes } from 'react';
import { Link } from 'react-router';
import { IntlMixin } from 'react-intl';

/*
//import imageResolver from 'utils/image-resolver';
//import Spinner from 'components/shared/spinner';
//import LangPicker from 'components/shared/lang-picker';

// Load styles for the header
// and load the `react-logo.png` image
// for the `<img src='' />` element
let reactLogo;
if (process.env.BROWSER) {
  reactLogo = require('images/react-logo.png');
} else {
  reactLogo = imageResolver('images/react-logo.png');
}*/

class Header extends Component {

  static propTypes = {
    type: PropTypes.object.isRequired
  }

  _getIntlMessage = IntlMixin.getIntlMessage

  state = {
    spinner: false
  }

  _handleRequestStoreChange = ({ inProgress }) =>
    this.setState({ spinner: inProgress })

  render() {
    let borrow = 'tab';
    let lend = 'tab';
    if (this.props.type === 'borrow') {
      borrow = borrow + ' active';
    } else if (this.props.type === 'lend') {
      lend = lend + ' active';
    }
    return (
      <header>
        <div className='wrapper'>
          <div className='logo'>
            <h1>Sparrow</h1>
            <p>Lend and borrow</p>
          </div>
          <div className='tabs'>
            <Link to='/borrow' className={ borrow }>Borrow Requests</Link>
            <Link to='/lend' className={ lend }>Lend Offers</Link>
          </div>
          <div className='login'>
            <Link to='/login' className='login-btn'>Login</Link>
          </div>
        </div>
      </header>
    );
  }
}

export default Header;
