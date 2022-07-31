package ir.asta.warehouseone.manager;

import ir.asta.warehouseone.dao.CategoryDao;
import ir.asta.warehouseone.dto.*;
import ir.asta.warehouseone.entity.CategoryEntity;
import ir.asta.warehouseone.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CategoryManager {

    private final CategoryDao categoryDao;
    private final CategoryMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public CategoryManager(CategoryDao categoryDao, CategoryMapper mapper) {
        this.categoryDao = categoryDao;
        this.mapper = mapper;
    }

    public CategorySaveResponseDto save(CategorySaveRequestDto dto){
        CategoryEntity category = mapper.convert(dto);
        categoryDao.save(category);
        return
                mapper.convert(category);
    }

    public CategoryDtoPage searchCategories(CategorySearchParamsDto searchParams){

        String subject = searchParams.getSubject();
        String code = searchParams.getCode();
        Integer pageSize = searchParams.getPageSize();
        Integer pageNumber = searchParams.getPageNumber();
        String orderBy = searchParams.getOrderBy();
        SortDirection sortDirection = searchParams.getSortDirection();

        if (code != null && code != "")
            return searchByCode(code);
        if (subject != null && subject != ""){
            return search(subject, pageSize, pageNumber, orderBy, sortDirection);
        }

        return new CategoryDtoPage();

    }

    //todo: exception handling must be done
    @Transactional
    public CategoryDtoPage searchByCode(String code){
        TypedQuery<CategoryEntity> query = entityManager.createQuery("select c from CategoryEntity c where c.code=:code", CategoryEntity.class);
        query.setParameter("code", code);
        CategoryEntity category = query.getSingleResult();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCode(category.getCode());
        categoryDto.setSubject(category.getSubject());
        CategoryDtoPage page = new CategoryDtoPage();
        page.getItems().add(categoryDto);
        return page;
        //todo: totalCount must be edited
    }

    @Transactional
    public CategoryDtoPage search(String subject, Integer pageSize, Integer pageNumber, String orderBy, SortDirection sortDirection){
        Integer from = 1;
        Integer to = 10;
        String sub = "'%"+subject+"%'";
        String sort = sortDirection.toString();
        if (pageSize <=0 || pageSize == null)
            pageSize = 10;//default size
        if (pageNumber <= 0 || pageNumber == null)
            pageNumber = 1;
        if (pageNumber>1) {
            from = (pageNumber-1)*pageSize ;
            to = pageSize ;
        }
        if (pageNumber == 1){
            from = 0;
            to = pageSize;
        }
        if (orderBy == null || orderBy.equals(""))
            orderBy = "id";
        if (sortDirection == null)
            sortDirection = SortDirection.ASC;
        String query = "select * from wh_category where subject like "+ sub +" order by " + orderBy +" " + sort + " " + "limit " + from + ", " + to;
        List<CategoryEntity> list = entityManager.createNativeQuery(query).getResultList();


        CategoryDtoPage page = new CategoryDtoPage();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        page.setTotalCount(100L);
        return page;
        //todo: totalCount must be edited
    }

    @Transactional
    public int count(CategorySearchParamsDto dto){
        int count = 0;
        if (dto.getSubject() == null && dto.getCode() == null)
            return count;
        if (dto.getCode() != null){

            //todo:count must be finished with code
            return count;
        }else {
            //todo: count must be finished with subject
            return count;
        }
    }

    @Transactional
    public CategorySaveResponseDto update(String code, CategorySaveRequestDto dto){
        CategoryEntity category = categoryDao.loadByCode(code);

        categoryDao.updateSubject(dto.getSubject(), category);

        CategoryEntity categoryEntity = categoryDao.findById(category.getId());

        return
                mapper.convert(categoryEntity);
    }//todo: exception handling

    public CategoryDto loadCategoryByCode(String code){
        CategoryEntity category = categoryDao.loadByCode(code);
        return
                mapper.convertEntityToCategoryDto(category);
    }

    public void removeByCode(String code){
        categoryDao.removeByCode(code);
    }
}
