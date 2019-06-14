package at.technikumwien;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Inject
    private EmployeeService employeeService;
    @Context
    private UriInfo uriInfo;

    @POST
    public Response create(Employee employee) {
        employeeService.save(employee);

        URI location = uriInfo.getAbsolutePathBuilder().path(employee.getId().toString()).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    public Employee retrieve(@PathParam("id") long id) {
        return employeeService.findById(id).orElse(null);
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") long id, Employee employee) {
        employee.setId(id);
        employeeService.save(employee);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        employeeService.deleteById(id);
    }

    @GET
    public List<Employee> retrieveAll() {
        return employeeService.findAll();
    }
}
