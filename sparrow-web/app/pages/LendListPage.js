import React, { Component } from 'react';
import DocumentTitle from 'react-document-title';


class LendListPage extends Component {

  static propTypes = {
    // currentUser: React.PropTypes.object.isRequired
  }

  mockList() {
    return (
      <div className='list-container'>
        <br/>
        Coming Soon!
      </div>
    );
  }

  render() {
    return (
      <DocumentTitle title='Sparrow'>
        <section className='home-page'>

          <div className='wrapper'>
            { this.mockList() }
          </div>

        </section>
      </DocumentTitle>
    );
  }

}

export default LendListPage;
