'use strict';

import React  from 'react/addons';
import {Link} from 'react-router';

const Header = React.createClass({

  getInitialState() {
    return {
      type: 'borrow', 
      props: {}
    };
  },
  
  componentDidMount() {
    document.addEventListener('App.Events.SetHeader', function(e) {
      this.setState(e.detail);
    }.bind(this));
  },
  
  conponentWillUnmount() {
    document.removeEventListener('App.Events.SetHeader');
  },
  
  render() {
    var borrowTab, lendTab;
    if(this.state.type == 'borrow') {
      borrowTab = "tab active";
      lendTab = "tab";
    } else if(this.state.type == 'lend') {
      borrowTab = "tab";
      lendTab = "tab active";
    }
    return (
      <header>
        <div className="wrapper">
          <div className="logo">
            <h1>Sparrow</h1>
            <p>Lend and borrow</p>
          </div>
          <div className="tabs">
            <Link to="/borrow" className={borrowTab}>Borrow Requests</Link>
            <Link to="/lend" className={lendTab}>Lend Offers</Link>
          </div>
          <div className="login">
            <Link to="/login" className="login-btn">Login</Link>
          </div>
        </div>
      </header>
    );
  }

});

export default Header;