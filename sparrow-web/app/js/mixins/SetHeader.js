var SetHeader = {
  setHeader(header) {
    var event = new CustomEvent('App.Events.SetHeader', { detail: header });
    document.dispatchEvent(event);
  },
  componentDidMount() {
    if(this.header) {
      this.setHeader(this.header());  
    }
  }
};

export default SetHeader;