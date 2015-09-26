'use strict';

import React         from 'react/addons';
import DocumentTitle from 'react-document-title';

var MissingFieldError = "Missing field";
var OverflowError = "Field overflow";
const ListingPage = React.createClass({

  propTypes: {
    currentUser: React.PropTypes.object.isRequired
  },

  getInitialState() {
    return {value: ''};
  },

  handleError(e) {
    this.setState({value: e})
  },

  errorDisplay() {
    return (<p>{this.state.value}</p>);
  },

  render() {
    return (
      <DocumentTitle title="Sparrow">
        <section className="listing-page">
        <p>List Your Item</p>
          <div>
            <form>
              <input type="text" className="textbox" id="Title" placeholder="Title"/>
              <br></br>
              <input type="text" className="textbox" id="Description" placeholder="Description"/>
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
    if(title == null || description == null || bounty == null)
    {
      console.log(MissingFieldError);
      this.handleError(MissingFieldError);
    }
    if(title.length > 140 || description > 140 || bounty > Number.MAX_VALUE)
    {
      console.log(OverflowError);
      this.handleError(OverflowError);    
    }
    //TODO: bounty textbox is able to enter "-1-1-1-1-1"
    //TODO: parsing for tags.
    
    else
    {
      //TODO: Pass the value to backend
      console.log('All good');
      console.log(bounty);
    }
  }
});

export default ListingPage;