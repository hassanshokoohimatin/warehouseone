package ir.asta.warehouseone.mapper;

import ir.asta.warehouseone.dto.CategoryDto;
import ir.asta.warehouseone.dto.CategoryDtoPage;
import ir.asta.warehouseone.dto.CategorySaveRequestDto;
import ir.asta.warehouseone.dto.CategorySaveResponseDto;
import ir.asta.warehouseone.entity.CategoryEntity;
import ir.asta.warehouseone.manager.CategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class CategoryMapper {

    public String categoryCodeGenerator(){
        return UUID.randomUUID().toString();
    }

    public CategoryEntity convert(CategorySaveRequestDto dto){

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCode(categoryCodeGenerator());
        categoryEntity.setSubject(dto.getSubject());

        return categoryEntity;
    }

    public CategorySaveResponseDto convert(CategoryEntity category){
        CategorySaveResponseDto responseDto = new CategorySaveResponseDto();
        responseDto.setCode(category.getCode());
        return responseDto;
    }

    public CategoryDto convertEntityToCategoryDto(CategoryEntity categoryEntity){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCode(categoryEntity.getCode());
        categoryDto.setSubject(categoryEntity.getSubject());
        return categoryDto;
    }

    public CategoryDtoPage searchResult(List<CategoryEntity> list, Integer pageSize, Integer pageNumber, Long totalCount){
        CategoryDtoPage page = new CategoryDtoPage();

        for (CategoryEntity category : list){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setSubject(category.getSubject());
            categoryDto.setCode(category.getCode());
            page.getItems().add(categoryDto);
        }
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        page.setTotalCount(totalCount);

        return page;
    }
}
