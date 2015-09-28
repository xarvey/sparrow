'use strict';

import React         from 'react/addons';
import DocumentTitle from 'react-document-title';

var MissingFieldError = "Missing field";
var OverflowError = "Field overflow";
var NumberInvalidError = "Bounty must be a valid non negative number";
var TagFormatError = "Invalid Tag Format (i.e. \"desk red wooden\")";

const ListingPage = React.createClass({

  static propTypes: {
    currentUser: React.PropTypes.object.isRequired
  },

  //TODO: add your files here after errormessage, i.e "files": []. iuuno if the syntax's correct.
  getInitialState() {
    return {value: {"errorMessage":''}};
  },

  handleErrorMessage(e) {
    var newValue = this.state.value;
    newValue.errorMessage = e;
    this.setState({value: newValue})
  },

  errorDisplay() {
    return (<p>{this.state.value.errorMessage}</p>);
  },

  render() {
    return (
      <DocumentTitle title="Sparrow">
        <section className="listing-page">
        <p>List Your Item</p>
          <div>
            <form>
              <input type="text" className="textbox" id="Title" placeholder="Title (140 words max)"/>
              <br></br>
              <input type="text" className="textbox" id="Description" placeholder="Description (140 words max)"/>
              <br></br>
              <input type="text" className="textbox" id="Tags" placeholder="Tags (Seperate by space)"/>
              <br></br>
              <input type="number" min="0" className="numberbox" id="Bounty" placeholder="Bounty"/>
              <br></br>
              <input type="radio" className="type" value="borrow"/>Borrow
              <br></br>
              <input type="radio" className="type" value="lend"/>Lend
            </form>
          </div>

          <div className="buttons">
            <button type="button" id="listing" onClick={this.listing}>List</button>
          </div>

          {this.errorDisplay()}

        </section>
      </DocumentTitle>
    );
  },

  listing() {
    var title = document.getElementById("Title").value;
    var description = document.getElementById("Description").value;
    var bounty = document.getElementById("Bounty").value;
    var tags = document.getElementById("Tags").value;

    if(title == null || title.length == 0 || description == null || description.length == 0 || bounty == null || tags == null || tags.length == 0)
    {
      console.log(MissingFieldError);
      this.handleErrorMessage(MissingFieldError);
    }

    else if(title.length > 140 || description > 140 || bounty > Number.MAX_VALUE)
    {
      console.log(OverflowError);
      this.handleErrorMessage(OverflowError);
    }

    else if(isNaN(bounty) || bounty.length == 0) {
      console.log(NumberInvalidError);
      this.handleErrorMessage(NumberInvalidError);
    }

    tags = tags.split(' ');
    if(tags.length == 0) {
      console.log('not good');
    }

    else
    {
      //TODO: Pass the value to backend
      console.log('All good');
      console.log(bounty.length);
    }
  }
});

export default ListingPage;
