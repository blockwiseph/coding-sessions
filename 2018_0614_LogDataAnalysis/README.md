Log data analysis application is required to generate report metrics on log file data.

The input to the application is  a log file, the output is a bunch of reports.

Input Log file contains a list of events. 

Valid log file lines are:

LOGIN <email>
LOGOUT <email>
CRASH <email> 
PURCHASE <email> <num_items> <amount>

We need to do basic analytics on these events. Each type of analytics report should be spit out, in json format.

1. How many of each event type
2. How many unique users logged in, unique users logged out
3. Average of logins, logouts, crashes  per unique user
4. Average purchase amount, average price per item, average items per purchase


It should be easy to support new event types.
It should be easy to add new analytic types.


As a test of flexibility, we will add a new event type, and report type, after completing the whole application.
Ideally, just add the new classes and everything works as expected.

VIEW_AD <email> <ad_id>

5. How many views per ad, how many unique views per ad