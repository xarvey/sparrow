'use strict';

import React         from 'react/addons';
import {Link}        from 'react-router';
import DocumentTitle from 'react-document-title';

import RequestedItem from '../components/RequestedItem';
import SetHeader from '../mixins/SetHeader';

const BorrowListPage = React.createClass({

  mixins: [SetHeader],
  
  header() {
    return {
      type: 'borrow',
      props: {}
    };
  },
  
  getInitialState() {
    return {
      mockData: [
        {
          name: 'birdy',
          time: '1 min ago',
          text: 'Can I please borrow a nest?',
          duration: '2 hours',
          price: '$0-$20'
        },
        {
          name: 'eagle',
          time: '3 mins ago',
          text: 'Need a glasses',
          duration: '1 day',
          price: 'free'
        },
        {
          name: 'albatross',
          time: '2 days later',
          text: 'I don\'t need you',
          duration: '1 second',
          price: '$30000'
        }
      ]
    }
  },
  
  propTypes: {
    currentUser: React.PropTypes.object.isRequired
  },

  mockList() {
    return (
      <div className="list-container">
        {
          this.state.mockData.map((item) => {
            return <RequestedItem item={item}/>
          })
        }
      </div>
    )
  },
  
  render() {
    return (
      <DocumentTitle title="Sparrow">
        <section className="home-page">

          <div className="wrapper"> 
            {this.mockList()}
          </div>
          
        </section>
      </DocumentTitle>
    );
  }

});

export default BorrowListPage;