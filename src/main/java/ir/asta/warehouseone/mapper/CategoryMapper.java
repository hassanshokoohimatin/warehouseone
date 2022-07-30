package ir.asta.warehouseone.mapper;

import ir.asta.warehouseone.dto.CategoryDto;
import ir.asta.warehouseone.dto.CategoryDtoPage;
import ir.asta.warehouseone.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDtoPage searchResult(List<CategoryEntity> list, Integer pageSize, Integer pageNumber, Long totalCount){
        CategoryDtoPage page = new CategoryDtoPage();

        for (CategoryEntity entity : list){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setSubject(entity.getSubject());
            categoryDto.setCode(entity.getCode());
            page.getItems().add(categoryDto);
        }
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        page.setTotalCount(totalCount);

        return page;
    }
}
