package PassBoard.Assessment;

import PassBoard.Assessment.DAO.EventRepo;
import PassBoard.Assessment.DTOs.EventDTO;
import PassBoard.Assessment.Mappers.EventMapper;
import PassBoard.Assessment.Models.Event;
import PassBoard.Assessment.Services.Interfaces.EventServiceInterface;
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
    private EventServiceInterface eventService;

    @Mock
    private EventRepo eventRepo;

    @Mock
    private EventMapper eventMapper;

    @Test
    void testGetAllEvents() {
        // Arrange
        when(eventRepo.findAll()).thenReturn(Collections.singletonList(new Event()));

        // Act
        List<EventDTO> events = eventService.getAll();

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
        List<EventDTO> events = eventService.getEventsBetweenDates(startDate, endDate);

        // Assert if the data is mocked successfully
        assertEquals(1, events.size());
    }

    @Test
    void testCreateEvent() {
        // Arrange
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Test Event");
        when(eventRepo.findAll()).thenReturn(Collections.emptyList());
        // adding mocking statements to occur

        when(eventRepo.save(eventMapper.DTOToEntity(eventDTO))).thenReturn(eventMapper.DTOToEntity(eventDTO));


        String result = String.valueOf(eventService.createEvent(eventDTO));

        // Assert if the returned result is "saved"
        assertEquals("saved", result);
    }

    @Test
    void testCreateEventAlreadyExists() {
        // Arrange
        Event existingEvent = new Event();
        existingEvent.setName("Test Event");
        when(eventRepo.findAll()).thenReturn(Collections.singletonList(existingEvent));

        // Act
        EventDTO newEvent = new EventDTO();
        //creating new event with the same name
        newEvent.setName("Test Event");
        String result = String.valueOf(eventService.createEvent(newEvent));

        // Assert if the data is the same
        assertEquals("already exists with that name", result);
    }

}
