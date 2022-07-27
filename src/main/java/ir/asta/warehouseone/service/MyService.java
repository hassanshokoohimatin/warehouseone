package ir.asta.warehouseone.service;

import ir.asta.warehouseone.dao.CategoryDao;
import ir.asta.warehouseone.dto.CategorySaveRequestDto;
import ir.asta.warehouseone.dto.CategorySearchParamsDto;
import ir.asta.warehouseone.entity.CategoryEntity;
import ir.asta.warehouseone.manager.CategoryManager;
import ir.asta.warehouseone.manager.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class MyService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryManager categoryManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response method(){

        CategorySearchParamsDto paramsDto = new
                CategorySearchParamsDto("ic", "", 10, 1, "", SortDirection.ASC);
        List<CategoryEntity> list = categoryManager.searchCategories(paramsDto);

        return Response
                .status(Response.Status.OK)
                .entity(list)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }
}
