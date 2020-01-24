# CHICKENS WITH FINGERS
### Standard message rates apply

The concept of this project is to periodically report one’s location to a desired number in the form of a Google Maps link sent via SMS text messaging. The user is first prompted for access to their location, contacts, and SMS messages. These permissions are all necessary for the app to properly function. 

The user is initially shown a screen of Google Maps displaying their current location and the only option is a button that says START A NEW ADVENTURE. The user is posed two questions upon pressing this button, a time interval at which to send text messages and a phone number to send those messages to. An error warning will appear in the form of a toast if the user does any of the following:

1.	Does not enter a valid phone number (10-11 characters of only digits)
2.	Does not enter any phone number
3.	Does not provide a time interval (interval cannot be zero hours zero minutes)

Once the user has given sufficient data they may press START which takes the user to a third and final screen. In the final screen the user can see a countdown timer that displays the time remaining in their interval until the next text message will be sent. Below that is a text field displaying the previously entered phone number, this is shown for the user to ensure they entered the correct number. Every time the countdown timer reaches zero a toast appears to inform the user a text message has been sent, it does not consider whether the user has service or not. The timer is then reset to the given time interval and the process repeats. The app can be minimized and phone locked and will continue to work, if the app is closed completely the messages will not send.

There are 3 buttons on the final screen, STOP, RESTART, and END ADVENTURE. Stop and restart can manipulate the timer allowing the user to stop the timer and restart it without needing to go through the set-up process a second time. Ending the program will return the user to the main screen displaying Google Maps. The user will need to re-enter a phone number and time interval if they wish to resume 


## #groupproject-chickens-with-fingers
