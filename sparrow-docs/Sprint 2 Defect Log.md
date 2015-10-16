Sprint 2 Defect Log
=======================

### Design Inspection Log

 - Product: Sparrow Design Inspection
 - Date: 5 October 2015
 - Author: Sparrow Team
 - Moderator: Joe Bruckner
 - Inspectors: Yao Xiao, Nathan Chang, Zilun Mai, Saranyu Phusit
 - Recorder: Sargun Vohra

| Defect # | Description                                                                            | Severity | How Corrected                                     |
|:---------|:---------------------------------------------------------------------------------------|:---------|:--------------------------------------------------|
|     1    | Listing's comment list should be a list of int's not String's                          |     2    | Change comment of type List<String> to List<Int>  |
|     2    | The user's login information should not be stored in the query parameter for the websites                          |     2    | Store the information in the cookies  |


### Code Inspection Log

 - Product: Sparrow Code Inspection
 - Date: 5th-16th October 2015
 - Author: Sparrow Team
 - Moderator: Joe Bruckner
 - Inspectors: Yao Xiao, Nathan Chang, Zilun Mai, Saranyu Phusit
 - Recorder: Sargun Vohra

| Defect # | Description                                                              | Severity | How Corrected                                                                        |
|:---------|:-------------------------------------------------------------------------|:---------|:-------------------------------------------------------------------------------------|
|     1    | When user close the browser, the cookie shall not disappear                          |     2    | Add expiraion date for the cookies  |


### Unit Testing Log

- Product: Sparrow Unit Tests
- Date: 5th-16th October 2015
- Author: Sparrow Team

| Defect # | Description                                                              | Severity | How Corrected                                                                        |
|:---------|:-------------------------------------------------------------------------|:---------|:-------------------------------------------------------------------------------------|
|     1    | When parameter is missing, the program should return a default value instead of crashing with undefined errpr                          |     1    | Add default value to avoid undefined problem. Example: user=document.getCookie("username") \|\| 'default user'  |
|     2    | When passing funtion pointer into inside function, the main function is self-dependent                        |     1    | Add "that" to save reference of its own object. 
|     3    | When show all listing, the order is arbitary                        |     3    | Use for (var 1=0; i < list.length; i++) instead of for (var list_details in list) 



