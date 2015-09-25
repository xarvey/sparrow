Sprint 1 Defect Log
=======================

### Design Inspection Log

 - Product: Sparrow Design Inspection
 - Date: 21 September 2015
 - Author: Sparrow Team
 - Moderator: Joe Bruckner
 - Inspectors: Yao Xiao, Nathan Chang, Zilun Mai, Saranyu Phusit
 - Recorder: Sargun Vohra

| Defect # | Description                                                                            | Severity | How Corrected                                  |
|:---------|:---------------------------------------------------------------------------------------|:---------|:-----------------------------------------------|
| 1        | Borrow and lend API methods should not be duplicated wherever possible                 | 1        | Get and Edit calls were merged                 |
| 2        | Downvotes should be a list of ids, not a count                                         | 3        | Changed the type from Int to List<Int>         |
| 3        | Search terms should automatically search tags, so filters should not have a tags field | 2        | Removed the tags fields from the filter object |
| 4        | Friend endpoints should use POST and DELETE not add and remove directories             | 1        | Changed GET friend calls to POST and DELETE    |
| 5        | When login authorization fails, the status code should be 500 not 200                  | 2        | Change the response code                       |
| 6        | The front end should sanitize the input to prevent malicious input                     | 2        | Sanitize the input                             |

### Code Inspection Log

 - Product: Sparrow Code Inspection
 - Date: 21 September 2015
 - Author: Sparrow Team
 - Moderator: Joe Bruckner
 - Inspectors: Yao Xiao, Nathan Chang, Zilun Mai, Saranyu Phusit
 - Recorder: Sargun Vohra

| Defect # | Description                                                              | Severity | How Corrected                                                                        |
|:---------|:-------------------------------------------------------------------------|:---------|:-------------------------------------------------------------------------------------|
| 1        | Move JSON logic out of Sparrow class to make Sparrow type safe           | 2        | Moved JSON parsing code out to the functions in the endpoints package                |
| 2        | Sparrow class should own the Gson object for custom rules                | 3        | Moved Gson object into Sparrow instance                                              |
| 3        | Change design to avoid lend and borrow queries to avoid code duplication | 1        | Combined the methods and modified design doc                                         |
| 4        | Create Datastore interface with mock to improve testability              | 1        | Created Datastore interface and made the temporary datastore implement the interface |
| 5        | Max int value is a valid id, should allow it                             | 2        | Don't throw exception when seeing the max int value as ID                            |

### Unit Testing Log

- Product: Sparrow Unit Tests
- Date: 21 September 2015
- Author: Sparrow Team

No defects were found by the unit tests.
