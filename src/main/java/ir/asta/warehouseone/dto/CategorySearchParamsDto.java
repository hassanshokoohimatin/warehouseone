package ir.asta.warehouseone.dto;

import ir.asta.warehouseone.manager.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySearchParamsDto {

    private String subject;
    private String code;
    private Integer pageSize;
    private Integer pageNumber;
    private String orderBy;
    private SortDirection sortDirection;

}
