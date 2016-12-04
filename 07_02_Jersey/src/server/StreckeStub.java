package server;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/streckeStub")
public class StreckeStub
{
    private Strecke strecke = Strecke.getInstance();
	/**
	 * Der Strecke-Stub muss so implementiert werden, dass 
	 * bei der Übergabe der entsprechenden URI
     * ein Response-Objet zurückgegeben wird
	 */

	// Der StreckStub muss so implementiert werden,
	@GET
	@Path("/reserviere/")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public MethodResponse reserviere(@QueryParam ("zugnummer") Integer zugnummer, @QueryParam ("bRichtung") Boolean bRichtung, @QueryParam ("iAbschnitt") Integer iAbschnitt){
        Boolean value = strecke.reserviere(zugnummer, bRichtung, iAbschnitt);
        MethodResponse response = new MethodResponse("Boolean", value.toString());
		return response;
	}

    @GET
    @Path("/getAbschnitt/")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
    public MethodResponse getAbschnitt(@QueryParam("bRichtung") Boolean bRichtung,@QueryParam("iAbschnitt") Integer iAbschnitt){
        Integer value = strecke.getAbschnitt(bRichtung, iAbschnitt);
        MethodResponse response = new MethodResponse("Integer", value.toString());
        return response;
    }

	@GET
    @Path("/wechselnVon/")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public MethodResponse wechselnVon(@QueryParam("zugnummer")Integer zugnummer,@QueryParam("bRichtung") Boolean bRichtung,@QueryParam("iAbschnitt") Integer iAbschnitt) {
        String value = strecke.wechselnVon(zugnummer, bRichtung, iAbschnitt);
        MethodResponse response = new MethodResponse("String", value.toString());
        return response;
	}

	@GET
    @Path("/wechselnNach/")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public MethodResponse wechselnNach(@QueryParam("zugnummer")Integer zugnummer,@QueryParam("bRichtung") Boolean bRichtung,@QueryParam("iAbschnitt") Integer iAbschnitt){
        String value = strecke.wechselnNach(zugnummer, bRichtung, iAbschnitt);
        MethodResponse response = new MethodResponse("String", value.toString());
        return response;
	}

	@GET
    @Path("/freigeben/")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public MethodResponse freigeben(@QueryParam("zugnummer")Integer zugnummer,@QueryParam("bRichtung") Boolean bRichtung,@QueryParam("iAbschnitt") Integer iAbschnitt) {
        Boolean value = strecke.freigeben(zugnummer, bRichtung, iAbschnitt);
        MethodResponse response = new MethodResponse("Boolean", value.toString());
        return response;
    }

	@GET
    @Path("/verlassen/")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public MethodResponse verlassen(@QueryParam("zugnummer")Integer zugnummer,@QueryParam("bRichtung") Boolean bRichtung) {
        String value = strecke.verlassen(zugnummer, bRichtung);
        MethodResponse response = new MethodResponse("String", value.toString());
        return response;
	}

	@GET
    @Path("/getStreckenLaenge")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public MethodResponse getStreckenLaenge() {
        Integer value = strecke.getStreckenLaenge();
        MethodResponse response = new MethodResponse("Integer", value.toString());
        return response;
	}


}