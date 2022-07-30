package ir.asta.warehouseone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.asta.warehouseone.dao.CategoryDao;
import ir.asta.warehouseone.dto.CategoryDtoPage;
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
import javax.ws.rs.*;
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

        List<CategoryEntity> list = categoryDao.findAllCategories();

        return Response
                .status(Response.Status.OK)
                .entity(list)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(CategorySearchParamsDto dto){

        if (dto.getPageNumber() == null){
            dto.setPageNumber(1);
        }
        if (dto.getPageSize() == null){
            dto.setPageSize(10);
        }
        if (dto.getSortDirection() == null){
            dto.setSortDirection(SortDirection.ASC);
        }
        CategoryDtoPage page =
                categoryManager.searchCategories(dto);

        return
        Response.status(200)
                .entity(page)
                .build();
    }
}
