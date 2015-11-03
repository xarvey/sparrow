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
    spinner: false,
  }

  _handleRequestStoreChange = ({ inProgress }) =>
    this.setState({ spinner: inProgress })

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

  logout() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
      var cookie = cookies[i];
      var eqPos = cookie.indexOf("=");
      var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
      document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
    window.location.href="/borrow";
  }

  render() {
    let borrow = 'tab';
    let lend = 'tab';
    if (this.props.type === 'borrow') {
      borrow = borrow + ' active';
    } else if (this.props.type === 'lend') {
      lend = lend + ' active';
    }

    let name = this.getcookie('name');
    let userid = this.getcookie('userid');

    let login = ( <Link to='/login' className='login-btn'>Login</Link> )
    let create = (<Link to='/create' className='active'>Create</Link> )

    if(userid.trim().length > 0) {
        login = <Link to={'/user/'+userid}><strong>{name}</strong></Link>
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
            {login}
          </div>
          {create}
          <button onClick={this.logout.bind(this)} > Logout</button>
        </div>
      </header>
    );
  }
}

export default Header;
