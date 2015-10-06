import React, { Component, PropTypes } from 'react';
import DocumentTitle from 'react-document-title';

const MissingFieldError = 'Missing field';
const OverflowError = 'Field overflow';
const NumberInvalidError = 'Bounty must be a valid non negative number';
const TagFormatError = 'Invalid Tag Format (i.e. \'desk red wooden\')';

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

          <button type='button' id='listing' onClick={ this.listing }>List</button>
          { this.errorDisplay() }

        </section>
      </DocumentTitle>
    );
  }

  listing() {
    const title = document.getElementById('Title').value;
    const description = document.getElementById('Description').value;
    const bounty = document.getElementById('Bounty').value;
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
      // TODO: Pass the value to backend
      console.log('All good');
      console.log(bounty.length);
    }
  }
}

export default ListingPage;
