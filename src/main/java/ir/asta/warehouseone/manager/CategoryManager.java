package ir.asta.warehouseone.manager;

import ir.asta.warehouseone.dto.CategorySearchParamsDto;
import ir.asta.warehouseone.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CategoryManager {

    @PersistenceContext
    private EntityManager entityManager;

    public String categoryCodeGenerator(){
        return UUID.randomUUID().toString();
    }

    public List<CategoryEntity> searchCategories(CategorySearchParamsDto searchParams){

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

        return new ArrayList<>();

    }

    //todo: exception handling must be done
    @Transactional
    public List<CategoryEntity> searchByCode(String code){
        List<CategoryEntity> list = new ArrayList<>();
        TypedQuery<CategoryEntity> query = entityManager.createQuery("select c from CategoryEntity c where c.code=:code", CategoryEntity.class);
        query.setParameter("code", code);
        CategoryEntity category = (CategoryEntity) query.getSingleResult();
        list.add(category);
        return list;
    }

    @Transactional
    public List<CategoryEntity> search(String subject, Integer pageSize, Integer pageNumber, String orderBy, SortDirection sortDirection){
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
        list = entityManager.createNativeQuery(query).getResultList();
        return list;
    }
}
