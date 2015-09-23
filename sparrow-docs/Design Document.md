Sparrow Design Document
========================

General System Design
----------------------------

### Description

The system consists of a back end and a front end. The front end consists of the web application that makes requests to the back end and presents the data to the user. The back end consists of the database and the web service that handles client requests with the database.

### Process

The general process for communication between the different components is as follows:

```sequence
Client->Service: JSON request over HTTP
Service->Database: SQL query
Note right of Database: Query is executed
Database-->Service: SQL response table
Service-->Client: JSON response over HTTP
```

The process that the back end uses to handle a request is as follows:

```flow
start=>start: Request arrives
parse=>operation: Parse the JSON
valid=>condition: Valid request?
check=>operation: Check user permissions
perm=>condition: User has permission?
db=>operation: Perform DB query
success=>condition: Successful?
response=>operation: Translate response object to JSON
err400=>end: 400 error
err403=>end: 403 error
err500=>end: 500 error
end=>end: JSON response is sent

start->parse->valid
valid(no)->err400
valid(yes)->check->perm
perm(no)->err403
perm(yes)->db->success
success(no)->err500
success(yes)->response->end
```

Detailed Design
----------------------------

### Service Objects

This is the list of JSON object types used when communicating with the REST API. Types marked with a '?' may be missing.

#### UserCreation Object

This object is used in the creation of users.

| Field    | Type   | Description                    |
|:---------|:-------|:-------------------------------|
| name     | String | The user's public display name |
| email    | String | The user's email address       |
| passcode | String | The user's password            |
| zipCode  | String | The user's USA zip code        |

#### User Object

This object represents a user's profile.

| Field          | Type      | Description                                   |
|:---------------|:----------|:----------------------------------------------|
| id             | Int       | The user's unique id                          |
| name           | String    | The user's public display name                |
| zipCode        | String    | The user's USA zip code                       |
| creationDate   | Long      | The date this user was created, in UNIX time  |
| friends        | List[Int] | The ids of the user's friends                 |
| borrowListings | List[Int] | The ids of the user's request listings        |
| lendListings   | List[Int] | The ids of the user's lend listings           |
| comments       | List[Int] | The ids of the comments on the user's profile |
| upVotes        | List[Int] | The users who have upvoted this user          |
| downVotes      | List[Int] | The users who have downvoted this user        |

#### Listing Object

This object represents a item available for lending or an item requested for borrowing.

| Field        | Type         | Description                                     |
|:-------------|:-------------|:------------------------------------------------|
| id           | Int          | The listing's unique id                         |
| owner        | Int          | The id of the user who posted the listing       |
| creationDate | Long         | The date this listing was created, in UNIX time |
| title        | String       | The title of the listing                        |
| description  | String       | The description of listing, in Markdown format  |
| tags         | List[String] | The list of tags of the listing                 |
| comments     | List[Int]    | The list of comment ids on this item            |
| bounty       | Int          | The bounty on this item, in US cents            |

#### Comment Object

This object represents a comment on a listing

| Field        | Type   | Description                                                                   |
|:-------------|:-------|:------------------------------------------------------------------------------|
| id           | Int    | The comment's unique id                                                       |
| owner        | Int    | The id of the user who posted the comment                                     |
| parent       | Int    | The id of the user or listing this comment is on                              |
| creationDate | Long   | The date this comment was created, in UNIX time                               |
| isPrivate    | Bool   | If true, this comment is only visible to the poster and the owner of the page |
| text         | String | The text of the comment                                                       |

#### FilterParams Object

This object represents a filter query

| Field     | Type         | Description                   |
|:----------|:-------------|:------------------------------|
| keywords  | String?      | The search text to search for |
| zipcode   | List[String] | The location to search in     |
| bountyMin | Int?         | The minimum bounty applied    |
| bountyMax | Int?         | The maximum bounty applied    |

### API Endpoints

#### Users

##### Get User

`GET /users/[id]`

Returns the user object associated with the id passed in the call.

##### Create User

`POST /users`

Creates a new user with the user creation object passed in the body of the request, and responds with the id of the new user.

##### Edit User

`PUT /users`

Replaces the user on the database with the user passed in the request body.

##### Add Friend

`GET /users/friends/add/[id]`

Adds the user with the id passed in the call to the authenticated user's friend list.

##### Remove Friend

`GET /users/friends/remove/[id]`

Removes the user with the id passed in the call from the authenticated user's friend list.

#### Frontpage

##### Get Frontpage Borrow Listings

`GET /frontpage/borrow/[page]`

Returns a collection of borrow listing ids.

##### Get Frontpage Lend Listings

`GET /frontpage/lend/[page]`

Returns a collection of lend listing ids.

#### Listings

##### Get Listing

`GET /listings/[id]`

Returns the listing object associated with the id.

##### Edit Listing

`PUT /listings`

Replaces the listing on the database with the listing passed in the request body.

##### Search Borrow Listings

`PUT /listings/borrow/filter`

Searches for borrow listings based on the filter criteria passed in the call.

##### Search Lend Listings

`PUT /listings/lend/filter`

Searches for lend listings based on the filter criteria passed in the call.

##### Create Borrow Listing

`POST /listings/borrow`

Creates a new borrow listing with the borrow listing object passed in the call, and returns the id of the new listing.

##### Create Lend Listing

`POST /listings/lend`

Creates a new lend listing with the borrow listing object passed in the call, and returns the id of the new listing.

##### Close Listing

`DELETE /listings/[id]`

Closes the listing associated with the id passed in the call.

#### Comments

##### Get Comment

`GET /comments/[id]`

Returns the comment object associated with the id passed in the call.

##### Create Comment

`POST /comments/listing/[id]`

`POST /comments/user/[id]`

Posts a comment to the user or listing associated with the id passed in the call, and returns the id of the new comment.

##### Delete Comment

`DELETE /comments/[id]`

Deletes the comment associated with the id passed in the call.

### Protocol

The client-server communication uses the HTTP protocol, and the body of each request and response contains data encoded as JSON. Creation methods return the id of the new object in the body on success, while other methods return an empty body on success. On failure, each API method returns an HTTP response code to categorize the error, and optionally a string in the body to describe what happened. Authentication is done via the "Authorization" header of the HTTP request, with the format "username:passcode" as the value.
