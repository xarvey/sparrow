'use strict';

import React  from 'react/addons';
import {Link} from 'react-router';

const Header = React.createClass({

  render() {
    return (
      <header>
        <div className="logo">
          <h1>Sparrow</h1>
          <p>Lend and borrow</p>
        </div>
        <div className="tabs">
          <Link to="/" className="tab active">Borrow Requests</Link>
          <Link to="/" className="tab">Lend Offers</Link>
        </div>
          <div className="login">
        </div>
      </header>
    );
  }

});

export default Header;