package ir.asta.warehouseone.dao;


import ir.asta.warehouseone.dto.CategorySaveRequestDto;
import ir.asta.warehouseone.entity.CategoryEntity;
import ir.asta.warehouseone.manager.CategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CategoryManager categoryManager;

    //==================================================================================================================
    //find a category with id
    public CategoryEntity findById(Long id){
        return
                entityManager.find(CategoryEntity.class, id);
    }
    //==================================================================================================================

    //find all categories
    public List<CategoryEntity> categoryEntityList(){
        return entityManager
                .createQuery("select c from CategoryEntity c", CategoryEntity.class)
                .getResultList();
    }
    //==================================================================================================================

    //update the subject of a category
    public void updateSubject(String newSubject, CategoryEntity category){

        //wh_category refers to CategoryEntity table
        Query query = entityManager.createNativeQuery("UPDATE wh_category set subject=:subject WHERE id=:id");
        query.setParameter("subject", newSubject);
        query.setParameter("id", category.getId());
        query.executeUpdate();
    }
    //==================================================================================================================

    //find a category with code
    public CategoryEntity loadByCode(String code){

        //============================================================================================
        //        return (CategoryEntity)
        //                entityManager.createQuery("select c from CategoryEntity c where c.code=:code")
        //                        .setParameter("code", code)
        //                        .getSingleResult();
        //============================================================================================

        String queryString = "select c from CategoryEntity c where c.code= :code";
        TypedQuery<CategoryEntity> typedQuery = entityManager.createQuery(queryString, CategoryEntity.class);
        typedQuery.setParameter("code", code);
        CategoryEntity category = typedQuery.getSingleResult();
        return category;
    }//todo: if category not found throw CategoryNotFoundException
    //==================================================================================================================

    public void save(CategoryEntity category){
        entityManager.persist(category);
    }
    //==================================================================================================================

    public CategoryEntity save(CategorySaveRequestDto dto){
        CategoryEntity category = new CategoryEntity(dto.getSubject(), categoryManager.categoryCodeGenerator());
        entityManager.persist(category);
        return category;
    }
    //==================================================================================================================

    public void remove(CategoryEntity category){
        entityManager.remove(category);
    }
    //==================================================================================================================

    public void removeById(Long id){
        CategoryEntity category = findById(id);
        entityManager.remove(category);
    }
    //==================================================================================================================

    //remove by code
    public void removeByCode(String code){
        CategoryEntity category = loadByCode(code); //todo: if category not found throw CategoryNotFound
        entityManager.remove(category);             //todo: now we dont consider relation
    }


}
