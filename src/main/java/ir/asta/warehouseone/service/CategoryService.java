package ir.asta.warehouseone.service;
import ir.asta.warehouseone.dao.CategoryDao;
import ir.asta.warehouseone.dto.*;
import ir.asta.warehouseone.entity.CategoryEntity;
import ir.asta.warehouseone.manager.CategoryManager;
import ir.asta.warehouseone.manager.SortDirection;
import ir.asta.warehouseone.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/category")
public class CategoryService {
    @Autowired
    private CategoryManager categoryManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(CategorySaveRequestDto dto){

        if (dto.getSubject() != null && ! dto.getSubject().equals("")){
            CategorySaveResponseDto responseDto = categoryManager.save(dto);
            return
                Response.status(200)
                        .entity(responseDto)
                        .build();
        }else {
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response  update(@PathParam("code") String code, CategorySaveRequestDto dto){

        if (code != null && !code.equals("") && dto.getSubject() != null && !dto.getSubject().equals("")){
            CategorySaveResponseDto responseDto = categoryManager.update(code, dto);

            return
                    Response
                            .status(200)
                            .entity(responseDto)
                            .build();
        }
        else
            return Response
                    .status(400)
                    .build();
    }

    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response load(@PathParam("code") String code){
        if (code != null && !code.equals("")){
            CategoryDto dto = categoryManager.loadCategoryByCode(code);
            return Response
                    .status(200)
                    .entity(dto)
                    .build();
        }
        else {
            return Response.status(400)
                    .build();
        }
    }

    @DELETE
    @Path("/{code}")
    public Response deleteByCode(@PathParam("code") String code){
        try {
            categoryManager.removeByCode(code);
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @GET
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(CategorySearchParamsDto searchParamsDto){

        if (searchParamsDto.getSortDirection() == null)
            searchParamsDto.setSortDirection(SortDirection.ASC);
        CategoryDtoPage page = categoryManager.searchCategories(searchParamsDto);
        return Response.status(200)
                .entity(page)
                .build();

    }
}
