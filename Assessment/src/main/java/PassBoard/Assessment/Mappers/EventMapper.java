package PassBoard.Assessment.Mappers;

import PassBoard.Assessment.DTOs.EventDTO;
import PassBoard.Assessment.Models.Event;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    private static final ModelMapper mapper= new ModelMapper();

    public EventDTO maptoDTO(Event event){
        return mapper.map(event,EventDTO.class);
    }

    public Event DTOToEntity(EventDTO eventDTO) {
        return mapper.map(eventDTO, Event.class);
    }

}
