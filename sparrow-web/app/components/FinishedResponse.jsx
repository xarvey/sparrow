import React, { Component, PropTypes } from 'react';
import ListingStore from 'stores/ListingStore';

const request = require('superagent');

class FinishedResponse extends Component {

  static propTypes = {
    id: PropTypes.object.isRequired,
  }

  state = {
    posted: false
  }

  render() {
    let form = this.state.posted ?
      (
        <span>You have submitted the comment!</span>
      ):
      (
        <textarea ref='message' cols='50' rows='4' className='textbox' id='message' placeholder='Provide the feedback'/>
      )
    let button = this.state.posted? null : <button type='button' onClick={this.submitComment.bind(this)} className='offer'>Submit</button>
    return (
      <div>
        {form}
        {button}
      </div>
    );
  }

  submitComment() {
    const endPointURL = 'http://vohras.tk:9000';
    const commentInfo = {
      owner: this.getcookie('userid'),
      parent: this.props.id,
      text: this.refs.message.value,
      isPrivate: false
    }
    request
      .post(endPointURL + '/comments/user/' + this.props.id)
      .set('Authentication',this.getcookie('username')+':'+this.getcookie('password'))
      .send(commentInfo)
      .end((err, res) => {
        if (err) {
          console.error('listing error!');
          return;
        }
        this.setState({posted: true});
      });
  }

  getcookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return " "
  }

}

export default FinishedResponse;
