package PassBoard.Assessment.Mappers;

import PassBoard.Assessment.DTOs.EventPurchaseDTO;
import PassBoard.Assessment.Models.EventsPurchased;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EventPurchaseMapper {
    private static final ModelMapper mapper= new ModelMapper();

    public EventPurchaseDTO mapToDTO(EventsPurchased eventsPurchased){
        return mapper.map(eventsPurchased, EventPurchaseDTO.class);
    }
    public EventsPurchased DTOToEntity(EventPurchaseDTO eventPurchaseDTO) {
        return mapper.map(eventPurchaseDTO, EventsPurchased.class);
    }
}
