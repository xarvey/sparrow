import React, { Component, PropTypes } from 'react';
import DocumentTitle from 'react-document-title';

const MissingFieldError = 'Missing field';
const OverflowError = 'Field overflow';
const NumberInvalidError = 'Bounty must be a valid non negative number';
const TagFormatError = 'Invalid Tag Format (i.e. \'desk red wooden\')';
const request = require('superagent');

class ListingPage extends Component {

  static propTypes = {
    currentUser: PropTypes.object.isRequired
  }

  // TODO: add your files here after errormessage, i.e 'files': []. iuuno if the syntax's correct.
  state = {
    value: { 'errorMessage': '' },
    type: 'borrow'
  }


  handleErrorMessage(e) {
    const newValue = this.state.value;
    newValue.errorMessage = e;
    this.setState({ value: newValue });
  }

  errorDisplay() {
    return (<p>{ this.state.value.errorMessage }</p>);
  }

  onTypeChanged(e) {
    this.setState({ type: e.currentTarget.value });
    console.log(e.currentTarget.value);
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
    return (
      <DocumentTitle title='Create new item listing'>
        <section className='listing-page'>
        <h2>Create your item listing</h2>
          <div>
            <form>
              <div className='type'>
                <input type='radio' id='rdio-borrow' className='type' value='borrow' checked={ this.state.type === 'borrow' } onChange={ this.onTypeChanged.bind(this) }/>
                  <label id='radio-borrow' htmlFor='rdio-borrow'>Borrow</label>
                <input type='radio' id='rdio-lend' className='type' value='lend' checked={ this.state.type === 'lend' } onChange={ this.onTypeChanged.bind(this) }/>
                  <label id='radio-lend' htmlFor='rdio-lend'>Lend</label>
              </div>
              <textarea cols='50' rows='2' className='textbox' id='Title' placeholder='Title (140 words max)'/>
              <textarea cols='50' rows='4' className='textbox' id='Description' placeholder='Description (140 words max)'/>
              <input type='text' className='textbox' id='Tags' placeholder='Tags (Seperate by space)'/>
              <input type='number' min='0' className='numberbox' id='Bounty' placeholder='Bounty'/>
            </form>
          </div>

          <button type='button' id='listing' onClick={ this.listing.bind(this) }>List</button>
          { this.errorDisplay() }

        </section>
      </DocumentTitle>
    );
  }

  listing() {
    const title = document.getElementById('Title').value;
    const description = document.getElementById('Description').value;
    const bounty = document.getElementById('Bounty').value;
    const endPointURL = 'http://vohras.tk:9000'
    let tags = document.getElementById('Tags').value;

    if (title === null || title.length === 0 || description === null || description.length === 0 || bounty === null || tags === null || tags.length === 0) {
      console.log(MissingFieldError);
      this.handleErrorMessage(MissingFieldError);
    } else if (title.length > 140 || description > 140 || bounty > Number.MAX_VALUE) {
      console.log(OverflowError);
      this.handleErrorMessage(OverflowError);
    } else if (isNaN(bounty) || bounty.length === 0) {
      console.log(NumberInvalidError);
      this.handleErrorMessage(NumberInvalidError);
    }

    tags = tags.split(' ');
    if (tags.length === 0) {
      console.log(TagFormatError);
      this.handleErrorMessage(TagFormatError);
    } else {
      var itemInfo={};
      itemInfo.id=this.getcookie('userid');

      itemInfo.type=this.state.type;
      itemInfo.title=title;
      itemInfo.description=description;
      itemInfo.bounty=bounty;
      itemInfo.tags=tags;
      itemInfo.comments=[];
      console.log(itemInfo);
      request
        .post(endPointURL + '/listings')
        .set('Authentication',this.getcookie('username')+':'+this.getcookie('password'))
        .send(itemInfo)
        .end((err, res) => {
          if (err) {
            console.error('listing error!');
            return;
          }
        });
    }
  }
}

export default ListingPage;
