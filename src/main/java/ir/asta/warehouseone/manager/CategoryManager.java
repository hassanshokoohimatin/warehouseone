package ir.asta.warehouseone.manager;

import ir.asta.warehouseone.dto.CategoryDto;
import ir.asta.warehouseone.dto.CategoryDtoPage;
import ir.asta.warehouseone.dto.CategorySaveRequestDto;
import ir.asta.warehouseone.dto.CategorySearchParamsDto;
import ir.asta.warehouseone.entity.CategoryEntity;
import ir.asta.warehouseone.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CategoryManager {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CategoryMapper mapper;

    public String categoryCodeGenerator(){
        return UUID.randomUUID().toString();
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
        List<CategoryEntity> list = new ArrayList<>();
        TypedQuery<CategoryEntity> query = entityManager.createQuery("select c from CategoryEntity c where c.code=:code", CategoryEntity.class);
        query.setParameter("code", code);
        CategoryEntity category = (CategoryEntity) query.getSingleResult();
        list.add(category);
        CategoryDtoPage page = new CategoryDtoPage();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setSubject(category.getSubject());
        categoryDto.setCode(category.getCode());
        page.getItems().add(categoryDto);
        page.setTotalCount(1L);
        return page;
    }

    @Transactional
    public CategoryDtoPage search(String subject, Integer pageSize, Integer pageNumber, String orderBy, SortDirection sortDirection){
        BigInteger totalCount;
        List<CategoryEntity> list = new ArrayList<>();
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
        list = entityManager.createNativeQuery(query, CategoryEntity.class).getResultList();
        String countQuery = "select count(id) from wh_category where subject like "+ sub ;
        totalCount = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
        Long totalCountLong = totalCount.longValue();
        return
                mapper.searchResult(list, pageSize, pageNumber, totalCountLong);
        //todo: calculate totalCount
    }

    public CategoryEntity save(CategorySaveRequestDto dto){
        CategoryEntity category = new CategoryEntity(dto.getSubject(), categoryCodeGenerator());
        entityManager.persist(category);
        return category;
    }
}
