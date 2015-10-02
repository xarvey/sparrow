// import findIndex from 'lodash/array/findIndex';
// import isEmpty from 'lodash/array/findIndex';

class UserStore {

  constructor() {
    this.bindActions(this.alt.getActions('users'));
    this.state = {
      user: '',
      loggedin: false
    };
  }

}

export default UserStore;
