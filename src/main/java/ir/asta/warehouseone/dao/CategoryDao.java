package ir.asta.warehouseone.dao;


import ir.asta.warehouseone.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;


    //find a category with id
    public CategoryEntity findById(Long id){
        return
                entityManager.find(CategoryEntity.class, id);
    }


    //find all categories
    public List<CategoryEntity> categoryEntityList(){
        return entityManager
                .createQuery("select c from CategoryEntity c", CategoryEntity.class)
                .getResultList();
    }

    //update the subject of a category
    @Transactional
    public void updateSubject(String newSubject, CategoryEntity category){

        //wh_category refers to CategoryEntity table
        Query query = entityManager.createNativeQuery("UPDATE wh_category set subject=:subject WHERE id=:id");
        query.setParameter("subject", newSubject);
        query.setParameter("id", category.getId());
        query.executeUpdate();
    }

}
