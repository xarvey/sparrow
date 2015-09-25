'use strict';

import React from 'react/addons';

const RequestedItem = React.createClass({

  render() {
    return (
      <div className="list-wrapper">
        <div className="user-info">
          <div className="user-pic"></div>
          <div className="user-text">
            <h3>{this.props.item.name}</h3>
            <p>{this.props.item.time}</p>
          </div>
        </div>
        <div className="list-text">{this.props.item.text}</div>
        <div className="list-details">
          <left>
            <div className="time">
              <i className="ion ion-android-alarm-clock"></i>
              <span>{this.props.item.duration}</span>
            </div>
            <div className="price">
              {this.props.item.price}
            </div>
          </left>
          <button className="btn-lend">LEND</button>
        </div>
      </div>
    );
  }

});

export default RequestedItem;