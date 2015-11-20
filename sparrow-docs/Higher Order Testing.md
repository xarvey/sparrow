# Higher order testing

## Test 59: Private schedule

For this test we disabled all network connections and loaded the schedule to make sure it's stored on the phone. The test passed.

## Test 60: Performance test

We were unable to find any quick search option, so the test fails.

## Test 61: Hallway testing

For this test we asked a person to use the app to input a schedule and select a restaurant to eat.

Observations:

 - Starts on a blank screen.

 - Schedule was created with a blank name

 - Back button did not work

 - Created another blank one

 - Had to kill the app and start again to leave the input screen

 - Nav drawer didn't close after selecting restaurant list

 - Filters work

 - "How do I go to all?"

 - Map starts all the way zoomed out

 - Can't go back again without restarting the app

## Test 62: Stress tests

For this test we used the monkey tool in the Android SDK to run many random events quickly and watched how the app behaves.

### 100 events

No issues.

### 250 events

No issues.

### 500 events

No issues.

### 1000 events

No issues.

### 10000 events

App crashed with a CursorIndexOutOfBoundsException.

### 20000 events

App got stuck on a blank screen with no buttons or text or other UI elements, after saving to database. Could not exit this screen.
