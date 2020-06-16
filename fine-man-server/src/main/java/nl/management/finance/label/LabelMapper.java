package nl.management.finance.label;

import org.springframework.stereotype.Service;

@Service
public class LabelMapper {
    public LabelDto toDto(Label label) {
        LabelDto dto = new LabelDto();
        dto.setId(label.getId());
        dto.setEditable(label.isEditable());
        dto.setName(label.getName());
        return dto;
    }
}
