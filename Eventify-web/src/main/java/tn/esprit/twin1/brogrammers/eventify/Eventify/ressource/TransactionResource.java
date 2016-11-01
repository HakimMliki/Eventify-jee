package tn.esprit.twin1.brogrammers.eventify.Eventify.ressource;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.paypal.api.payments.Payment;

import tn.esprit.twin1.brogrammers.eventify.Eventify.contracts.IReservationBusinessLocal;
import tn.esprit.twin1.brogrammers.eventify.Eventify.contracts.ITransactionBusinessLocal;
import tn.esprit.twin1.brogrammers.eventify.Eventify.domain.Reservation;
import tn.esprit.twin1.brogrammers.eventify.Eventify.domain.Transaction;
import tn.esprit.twin1.brogrammers.eventify.Eventify.util.Paypal.PaymentWithPayPalServlet;

@Path("transaction")
@RequestScoped
public class TransactionResource {

	@EJB
	ITransactionBusinessLocal transactionBusiness;
	PaymentWithPayPalServlet p = new PaymentWithPayPalServlet();
	@EJB
	IReservationBusinessLocal reservationBusiness;
	
	//lena ajout transaction
	@GET
	@Path("paypal/pay/{idReservation}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pay(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse,@PathParam("idReservation")int idReservation)
			throws ServletException, IOException {
		System.out.println("firaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaas");
		Reservation reservation = reservationBusiness.findReservationById(idReservation);
		System.out.println(reservation);
		Payment pp = p.createPayment(servletRequest, servletResponse,reservation);
		//System.out.println(Payment.getOAuthTokenCredential());
		Transaction tr = new Transaction( "", reservation.getAmount()+reservation.getAmount()*(7/100), reservation);
		//transactionBusiness.create(tr);
		return Response.status(Status.FOUND).entity(p.createPayment(servletRequest, servletResponse,reservation)).build();
	}
	
	
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> getAllTransaction() {

		return transactionBusiness.getAllTransactions();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response findTransactionById(@PathParam("id") int id) {
		Transaction t = transactionBusiness.findTransactionById(id);
		if (t == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.status(Status.OK).entity(t).build();
		}
	}

	/********/

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTransaction(Transaction transaction) {
		transactionBusiness.create(transaction);
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTransaction(Transaction transaction) {
		transactionBusiness.updateTransaction(transaction);
		return Response.status(Status.OK).build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteTransaction(@PathParam(value = "id") int id) {
		if (transactionBusiness.deleteTransactionById(id)) {
			return Response.status(Status.OK).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("paypalredirectcancled")
	public Response PaypalCancled() {

			return Response.status(Status.OK).build();
		}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("paypalredirect")
	public Response PaypalConfirmed() {

			return Response.status(Status.OK).build();
		}
	

}
