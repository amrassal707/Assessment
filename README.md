# Assessment

The event planning platform is a simple way of booking events available.

Here is the explanation of the code:

1) User: A Controller-Service-DAO pattern was followed in all entities that we have, creating user/event and booking an event. All follow the same pattern. I added a DTO for the user as it's a best practice not to deal with an entity directly. The DTO shows only the balance and the username, disregards the ID for better readability.
2) Event & Ticket: Events may consist of different types of tickets, hence I created a class named ticket that holds the properties of a ticket and created a list of composed tickets in the event class, also following the same pattern mentioned above in the user.
3) EventPurchase/Booking an event: this class takes in the number of tickets you want along with the event name and your username, we check to find the event name if it exists, deduct from the number of tickets available and deduct from your balance.
