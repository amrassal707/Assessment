package PassBoard.Assessment;

import PassBoard.Assessment.DAO.EventRepo;
import PassBoard.Assessment.Models.Event;
import PassBoard.Assessment.Services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepo eventRepo;

    @Test
    void testGetAllEvents() {
        // Arrange
        when(eventRepo.findAll()).thenReturn(Collections.singletonList(new Event()));

        // Act as if we have an instance available based on the above function
        List<Event> events = eventService.getAll();

        // Assert if the data is mocked successfully
        assertEquals(1, events.size());
    }

    @Test
    void testGetEventsBetweenDates() throws ParseException {
        // Arrange
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        when(eventRepo.findAll()).thenReturn(Collections.singletonList(new Event()));

        // Act as if we have an instance available based on the above function
        List<Event> events = eventService.getEventsBetweenDates(startDate, endDate);

        // Assert if the data is mocked successfully
        assertEquals(1, events.size());
    }

    @Test
    void testCreateEvent() {
        // Arrange
        Event event = new Event();
        event.setName("Test Event");
        when(eventRepo.findAll()).thenReturn(Collections.emptyList());
        // adding mocking statements to occur
        when(eventRepo.save(any(Event.class))).thenReturn(event);


        String result = eventService.createEvent(event);

        // Assert if the returned result is "saved"
        assertEquals("saved", result);
    }

    @Test
    void testCreateEventAlreadyExists() {
        // Arrange
        Event existingEvent = new Event();
        existingEvent.setName("Test Event");
        when(eventRepo.findAll()).thenReturn(Collections.singletonList(existingEvent));

        // Act as if we have an instance available based on the above function
        Event newEvent = new Event();
        //creating new event with the same name
        newEvent.setName("Test Event");
        String result = eventService.createEvent(newEvent);

        // Assert if the data is the same
        assertEquals("already exists with that name", result);
    }

}
