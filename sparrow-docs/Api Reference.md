
### API Endpoints

#### Users

##### Get User

`GET /users/[id]`  *Complete*

Returns the user object associated with the id passed in the call.

##### Create User

`POST /users` *Complete*

Creates a new user with the user creation object passed in the body of the request, and responds with the id of the new user.

##### Edit User

`PUT /users` *Complete*

Replaces the user on the database with the user passed in the request body.

##### Add Friend

`POST /users/friends/[id]` *In Progress*

Adds the user with the id passed in the call to the authenticated user's friend list.

##### Remove Friend

`DELETE /users/friends/[id]` *In Progress*

Removes the user with the id passed in the call from the authenticated user's friend list.

#### Frontpage

##### Get Frontpage Borrow Listings

`GET /frontpage/borrow/[page]` *In Progress*

Returns a collection of borrow listing ids.

##### Get Frontpage Lend Listings

`GET /frontpage/lend/[page]` *In Progress*

Returns a collection of lend listing ids.

#### Listings

##### Get Listing

`GET /listings/[id]` *Complete*

Returns the listing object associated with the id.

##### Edit Listing

`PUT /listings` *Complete*

Replaces the listing on the database with the listing passed in the request body.

##### Search Borrow Listings

`PUT /listings/filter` *In Progress*

Searches for listings based on the filter criteria passed in the call.

##### Create Borrow Listing

`POST /listings` *Complete*

Creates a new listing with the listing object passed in the call, and returns the id of the new listing.

##### Close Listing

`DELETE /listings/[id]`  *In Progress*

Closes the listing associated with the id passed in the call.

#### Comments

##### Get Comment

`GET /comments/[id]` *In Progress*

Returns the comment object associated with the id passed in the call.

##### Create Comment

`POST /comments/listing/[id]` *In Progress*

`POST /comments/user/[id]` *In Progress*

Posts a comment to the user or listing associated with the id passed in the call, and returns the id of the new comment.

##### Delete Comment

`DELETE /comments/[id]`  *In Progress* 

Deletes the comment associated with the id passed in the call.
