Version:
Java: 17.0.8.1
JavaFx: 17.0.6

Getting input instruction:
1. Login screen:
   User1:
   - username: "User1"
   - password: 123456
   User2:
   - username: "User2"
   - password: 123456
2. Choosing calendar by clicking on each radio button on the right.
3. Adding a new event:
   - Click the "Add Event" button to see the "Add New Event" popup
   - Type event name, choose date, start and end time.
     Notes: Start and end time must be of this format: "hh:mm"
   - Click the "Add" button to save event
   - Event will be displayed on the calendar
4. Editing an existing event
   - Click an event on the calendar to see the "Edit Event" popup
   - Edit event name, start or end time
      Notes: Start and end time must be of this format: "hh:mm"
   - Click Update to save changes
   - Click Delete to delete event
5. Change app's setting
   - Click the "Setting" button on top to see the "Settings" popup
   - Modifying time zone by selecting from dropdown menu
   - Changing app theme by clicking the radio button
   - Clicking Save to see the updates
6. Toggle Public/Private calendar
   - Click the PUBLIC/PRIVATE button to toggle changes
7. Log out
   - Logout button on top right corner to go back to login screen.

Notes:
- Since I only implemented calendar view by Month, other options for day, week, and year are not working yet.
- Inputting start and end time when editting or adding event must be of this format: "hh:mm". For example: 06:30 instead of 6:30
