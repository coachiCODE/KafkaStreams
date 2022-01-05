package expenses;

import beans.ClientBeanInterface;
import beans.CurrencyBeanInterface;
import beans.DataBeanInterface;
import beans.ManagerBeanInterface;
import data.Client;
import data.Currency;
import data.Manager;

import java.util.List;
import javax.ejb.EJB;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



@Path("/myservice")
@Produces(MediaType.APPLICATION_JSON)
public class MyService {

    @EJB
    private ClientBeanInterface clientBean;
    @EJB
    private ManagerBeanInterface managerBean;
    @EJB
    private CurrencyBeanInterface currencyBean;
    @EJB
    private DataBeanInterface dataBean;

    @POST
    @Path("/manager")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addManager(Manager manager){
        managerBean.addManager(manager.getName(), manager.getPassword());
        return Response.status(Response.Status.OK).entity("Manager criado com sucesso!").build();
    }

    @POST
    @Path("/client")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Client client){
        clientBean.addClient(client.getName(), client.getManager_id());
        return Response.status(Response.Status.OK).entity("Cliente criado com sucesso!").build();
    }

    @POST
    @Path("/currency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCurrency(Currency currency){
        currencyBean.addCurrency(currency.getName(),currency.getExchange());
        return Response.status(Response.Status.OK).entity("Currency criada com sucesso!").build();
    }

    @GET
    @Path("/manager")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getManagers(){
        List<Manager> managers = managerBean.listManagers();
        return Response.status(Response.Status.OK).entity(managers).build();
    }

    @GET
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClients(){
        List<Client> clients = clientBean.listClients();
        return Response.status(Response.Status.OK).entity(clients).build();
    }

    @GET
    @Path("/currency")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrencies(){
        List<Currency> currencies = currencyBean.listCurrencies();
        return Response.status(Response.Status.OK).entity(currencies).build();
    }

    @GET
    @Path("client/credit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientCredit(@QueryParam("id") int id){
        float credit = clientBean.getCredit(id);
        return Response.status(Response.Status.OK).entity(credit).build();
    }

    @GET
    @Path("client/payments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientPayments(@QueryParam("id") int id){
        float payments = clientBean.getPayments(id);
        return Response.status(Response.Status.OK).entity(payments).build();
    }

    @GET
    @Path("/client/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBalance(@QueryParam("id") int id){
        float balance = clientBean.getBalance(id);
        return Response.status(Response.Status.OK).entity(balance).build();
    }

    @GET
    @Path("/client/credit/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreditsTotal(){
        float allCredits = dataBean.getAllCredits();
        return Response.status(Response.Status.OK).entity(allCredits).build();
    }

    @GET
    @Path("/client/payments/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentsTotal(){
        float allPayments = dataBean.getAllPayments();
        return Response.status(Response.Status.OK).entity(allPayments).build();
    }

    @GET
    @Path("/client/balance/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBalancesTotal(){
        float allBalances = dataBean.getAllBalance();
        return Response.status(Response.Status.OK).entity(allBalances).build();
    }

    @GET
    @Path("/client/debt")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBiggestDebt(){
        //campo em Data que tem o id do cliente com maior debt?
        return Response.status(Response.Status.OK).entity("balance").build();
    }
}
