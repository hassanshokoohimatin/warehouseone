package ir.asta.warehouseone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoPage {

    private List<CategoryDto> items = new ArrayList<>();
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalCount;
}
