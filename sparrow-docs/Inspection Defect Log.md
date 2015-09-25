Inspection Defect Log
=======================

### Design Inspection

 - Product: Sparrow Design Inspection
 - Date: 21 September 2015
 - Author: Sparrow Team
 - Moderator: Joe Bruckner
 - Inspectors: Yao Xiao, Nathan Chang, Zilun Mai, Saranyu Phusit
 - Recorder: Sargun Vohra

| Defect # | Description                                                                            | Severity | How Corrected                                  |
|:---------|:---------------------------------------------------------------------------------------|:---------|:-----------------------------------------------|
| 1        | Borrow and lend API methods should not be duplicated wherever possible                 | 1        | Get and Edit calls were merged                     |
| 2        | Downvotes should be a list of ids, not a count                                         | 3        | Changed the type from Int to List<Int>         |
| 3        | Search terms should automaticalls search tags, so filters should not have a tags field | 2        | Removed the tags fields from the filter object |
| 4        | Friend endpoints should use POST and DELETE not add and remove directories             | 1        |     Changed GET friend calls to POST and DELETE    |
| 5        | When login authorization fails, the status code should be 500 not 200                  | 2        | Change the response code                           |
| 6        | The front end should sanitize the input to prevent malicious input                     | 2        | Sanitize the input                            |

