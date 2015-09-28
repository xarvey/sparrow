import React, { Component, PropTypes } from 'react';

import Footer from 'components/footer';

if (process.env.BROWSER) require('styles/main.css');

class App extends Component {

  static propTypes = {
    flux: PropTypes.object.isRequired,
    children: PropTypes.element
  }

  constructor(props, context) {
    super(props, context);
    this.state = { i18n: props.flux.getStore('locale').getState() };
  }

  componentDidMount() {
    const { flux } = this.props;
    flux.getStore('locale').listen(this._handleLocaleChange);
    flux.getStore('page-title').listen(this._handlePageTitleChange);
  }

  componentWillUnmount() {
    const { flux } = this.props;
    flux.getStore('locale').unlisten(this._handleLocaleChange);
    flux.getStore('page-title').unlisten(this._handlePageTitleChange);
  }

  _handleLocaleChange = (i18n) => this.setState({ i18n })
  _handlePageTitleChange = ({ title }) => document.title = title

  // If we have children components sent by `react-router`
  // we need to clone them and add them the correct
  // locale and messages sent from the Locale Store
  renderChild = (child) =>
    React.cloneElement(child, { ...this.state.i18n });

  render() {
    const { children } = this.props;
    return (
      <div className='app'>
        { children.header }
        { children.content }
        <hr />
        <Footer />
      </div>
    );
  }

}

export default App;
