# Sprint 2 Defect Log

### Design Inspection Log

- Product: Sparrow Design Inspection
- Date: 5 October 2015
- Author: Sparrow Team
- Moderator: Joe Bruckner
- Inspectors: Yao Xiao, Nathan Chang, Zilun Mai, Saranyu Phusit
- Recorder: Sargun Vohra

| Defect # | Description                              | Severity | How Corrected                            |
| :------- | :--------------------------------------- | :------- | :--------------------------------------- |
| 1        | Listing's comment list should be a list of int's not String's | 2        | Change comment of type List[String] to List[Int] |
| 2        | The user's login information should not be stored in the query parameter for the website | 2        | Store the information in cookies         |
| 3        | The user's previous search information should not be stored in the query parameter for the website | 2        | Store the information in cookies         |
| 4        | After a successful login, the user's profile page is loaded. It should load the home page instead | 3        | Change the redirection after a correct login to the home page |
| 5        | Sending borrow requests multiple times results in other users viewing duplicate requests. | 2        | Remove duplicate requests from the list of requests. |
| 6        | Accepting borrow requests multiple times by clicking the accept buttons in quick succession results in invalid requests. | 1        | Only accept the first acceptect request for each item. |
| 7        | There is no way to track whether a listing is open or closed | 1        | Add and maintain a "closed' boolean field to listing |

### Code Inspection Log

- Product: Sparrow Code Inspection
- Date: 14th October 2015
- Author: Sparrow Team
- Moderator: Joe Bruckner
- Inspectors: Yao Xiao, Nathan Chang, Zilun Mai, Saranyu Phusit
- Recorder: Sargun Vohra

| Defect # | Description                              | Severity | How Corrected                            |
| :------- | :--------------------------------------- | :------- | :--------------------------------------- |
| 1        | When the user closes the browser, the cookie won't disappear | 2        | Add expiraion date for the cookies       |
| 2        | When the user logs out, their log on information is still stored. | 2        | Clear cookies when logging out           |
| 3        | Users are not prevented from typing in too long search entries. Buffer overflowing results in messed up tags and bad searches. | 2        | Limit the characters the user can type   |
| 4        | Users are not prevented from typing in too long comments. Buffer overflowing results in cut off text | 3        | Limit the characters the user can type.  |
| 5        | Inputting a negative value for the price field is valid. This should be invalid | 2        | Make sure inputted values are positive.  |
| 6        | Frontpage API calls return results in arbitrary order instead of sorted by creation date (desc) | 1        | Sort the list before paginating and returning |

### Unit Testing Log

- Product: Sparrow Unit Tests
- Date: 16th October 2015
- Author: Sparrow Team

| Defect # | Description                              | Severity | How Corrected                            |
| :------- | :--------------------------------------- | :------- | :--------------------------------------- |
| 1        | When parameter is missing, the program should return a default value instead of crashing with undefined error | 1        | Add default value to avoid undefined problem. Example: user=document.getCookie("username") \ |
| 2        | When passing funtion pointer into inside function, the main function is self-dependent | 1        | Add "that" to save reference of its own object. |
| 3        | When show all listing, the order is arbitary | 3        | Use for (var 1=0; i < list.length; i++) instead of for (var list_details in list) |
| 4        | Authentication never succeeds despite proper credentials | 1        | Swap the arguments to '+' operator in the hash code functions |
| 5        | Empty comments are created               | 2        | Add a condition to reject empty comments |
| 6        | Comments can be posted to nonexistent listings or users | 2        | Add a condition to reject comments to nonexistent targets |
| 7        | A request for a missing comment does not give a "404" status | 3        | Change response status code for missing comment to "404" |
| 8        | All frontpage calls for the first or last page go out of bounds | 1        | Rework the algorithm for frontpage pagination |
| 9        | Frontpage calls to nonsense pages (index < 0 or size <= 1) cause an exception | 3        | Check for that condition and return "400 Bad Request" error |
| 10       | Invalid zip code is not rejected in user creation and edit | 2        | Check for that condition and return "400 Bad Request" error |
| 11       | Invalid name is not rejected in user creation and edit | 2        | Check for that condition and return "400 Bad Request" error |
| 12       | Invalid password is not rejected in user creation | 2        | Check for that condition and return "400 Bad Request" error |
| 13       | Invalid email is not rejected in user edit | 2        | Check for that condition and return "400 Bad Request" error |
| 14       | Invalid title on listing creation and edit is not rejected | 2        | Check for that condition and return "400 Bad Request" error |
| 15       | Invalid description on listing creation and edit is not rejected | 2        | Check for that condition and return "400 Bad Request" error |
| 16       | Invalid bounty on listing creation and edit is not rejected | 2        | Check for that condition and return "400 Bad Request" error |