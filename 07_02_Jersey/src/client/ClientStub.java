package client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import common.StreckeI;
import server.MethodResponse;

import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class ClientStub implements StreckeI {
    private long seqNr = 1;
    private final static String BASE_URL = "http://localhost:8080/rest";
    private Client client = null;
    private WebResource service = null;

    public ClientStub(){
        client = Client.create();
        service = client.resource(getBaseURI());
    }

    public Boolean reserviere(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt) {
        Builder builder =  service.path("streckeStub")
                .path("reserviere")
                .queryParam("zugnummer", Integer.toString(zugnummer))
                .queryParam("bRichtung", String.valueOf(bRichtung))
                .queryParam("iAbschnitt", Integer.toString(iAbschnitt))
                .accept(MediaType.TEXT_XML);
        return Boolean.valueOf(builder.get(MethodResponse.class).getValue());
    }

    public Integer getAbschnitt(Boolean bRichtung, Integer iAbschnitt) {
        Builder builder = service.path("streckeStub")
                .path("getAbschnitt")
                .queryParam("bRichtung", String.valueOf(bRichtung))
                .queryParam("iAbschnitt", Integer.toString(iAbschnitt))
                .accept(MediaType.TEXT_XML);
        return Integer.parseInt(builder.get(MethodResponse.class).getValue());
    }


    public String wechselnVon(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt) {
        Builder builder = service.path("streckeStub")
                .path("wechselnVon")
                .queryParam("zugnummer", Integer.toString(zugnummer))
                .queryParam("bRichtung", String.valueOf(bRichtung))
                .queryParam("iAbschnitt", Integer.toString(iAbschnitt))
                .accept(MediaType.TEXT_XML);
        return builder.get(MethodResponse.class).getValue();
    }

    public String wechselnNach(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt) {
        Builder builder = service.path("streckeStub")
                .path("wechselnNach")
                .queryParam("zugnummer", Integer.toString(zugnummer))
                .queryParam("bRichtung", String.valueOf(bRichtung))
                .queryParam("iAbschnitt", Integer.toString(iAbschnitt))
                .accept(MediaType.TEXT_XML);
        return builder.get(MethodResponse.class).getValue();
    }

    public Boolean freigeben(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt) {
        Builder builder = service.path("streckeStub")
                .path("reserviere")
                .queryParam("zugnummer", Integer.toString(zugnummer))
                .queryParam("bRichtung", String.valueOf(bRichtung))
                .queryParam("iAbschnitt", Integer.toString(iAbschnitt))
                .accept(MediaType.TEXT_XML);
        return Boolean.valueOf(builder.get(MethodResponse.class).getValue());
    }

    public String verlassen(Integer zugnummer, Boolean bRichtung) {
        Builder builder = service.path("streckeStub")
                .path("verlassen")
                .queryParam("zugnummer", Integer.toString(zugnummer))
                .queryParam("bRichtung", String.valueOf(bRichtung))
                .accept(MediaType.TEXT_XML);
        return builder.get(MethodResponse.class).getValue();
    }

    public Integer getStreckenLaenge() {
        Builder builder = service.path("streckeStub")
                .path("getStreckenLaenge")
                .accept(MediaType.TEXT_XML);
        return Integer.parseInt(builder.get(MethodResponse.class).getValue());
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(BASE_URL).build();
    }
}