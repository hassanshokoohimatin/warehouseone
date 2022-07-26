package ir.asta.warehouseone.service;

import ir.asta.warehouseone.dao.CategoryDao;
import ir.asta.warehouseone.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class MyService {

    @Autowired
    private CategoryDao categoryDao;


    @GET
    public String method(){

        CategoryEntity category = categoryDao.findById(2L);

        categoryDao.updateSubject("", category);

        return categoryDao.findById(2L).getSubject();
    }
}
