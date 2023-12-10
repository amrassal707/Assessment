# Assessment
Code in Main Branch

The event planning platform is a simple way of booking events available.

Here is the explanation of the code:

1) User: A Controller-Service-DAO pattern was followed in all entities that we have, creating a user/event and booking an event. All follow the same pattern. I added a DTO for the user as it's a best practice not to deal with an entity directly. The DTO shows only the balance and the username, and disregards the ID for better readability.
2) Event & Ticket: Events may consist of different types of tickets, hence I created a class named ticket that holds the properties of a ticket and created a list of composed tickets in the event class, also following the same pattern mentioned above in the user.
3) EventPurchase/Booking an event: this class takes in the number of tickets you want along with the event name and your username, we check to find the event name if it exists, deduct from the number of tickets available and deduct from your balance.

Event Controller:

Get All Events

Endpoint: GET /event
Description: Retrieves a list of all events.
Response Type: List of Event objects.
Get Events Between Dates

Endpoint: GET /event/{startDate}/{endDate}
Description: Retrieves a list of events that fall within the specified date range.
Request Parameters:
startDate (String): Start date in "yyyy-MM-dd" format.
endDate (String): End date in "yyyy-MM-dd" format.
Response Type: List of Event objects.
Create Event

Endpoint: POST /event
Description: Creates a new event.
Request Body: JSON object representing the event to be created. See the Event model for details.
Response Type: String message indicating the success of the operation.
Book Controller:

Book Event

Endpoint: POST /book
Description: Books an event for a user.
Request Body: JSON object representing the event to be booked. See the EventsPurchased model for details.
Response Type: String message indicating the success of the operation.
Refund Event

Endpoint: POST /book/refund
Description: Refunds a booked event for a user.
Request Body: JSON object representing the event to be refunded. See the EventsPurchased model for details.
Response Type: String message indicating the success of the operation.
Get Booked Events by Name

Endpoint: GET /book/{name}
Description: Retrieves a list of events booked by a user with the specified name.
Get All Users
Endpoint: GET /user
Description: Retrieves a list of all users along with their details.
Response Type: List of UserDTO objects.
Create User
Endpoint: POST /user
Description: Creates a new user.
Request Body: JSON object representing the user to be created. See the UserDTO model for details.
Response Type: String message indicating the success of the operation.
Error Handling
In case of incomplete or missing data in the request, appropriate error messages will be returned.
