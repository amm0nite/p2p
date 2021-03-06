package fr.ambox.p2p;

import java.util.HashMap;

import fr.ambox.p2p.connexion.PDU;
import fr.ambox.p2p.connexion.ReceptionData;
import fr.ambox.p2p.http.HttpResponse;

public abstract class UserService extends Service {
	public abstract void handle(PDU pdu, ReceptionData receptionData);
	public abstract HttpResponse apiGET(String[] elements, HashMap<String, String> params);
	public abstract HttpResponse apiPOST(String[] elements, HashMap<String, String> params);
	public abstract HttpResponse apiDELETE(String[] elements, HashMap<String, String> params);
	
	public HttpResponse apiGET(HashMap<String, String> params) {
		return this.apiGET(null, params);
	}
	public HttpResponse apiPOST(HashMap<String, String> params) {
		return this.apiPOST(null, params);
	}
	public HttpResponse apiDELETE(HashMap<String, String> params) {
		return this.apiDELETE(null, params);
	}
	
	public HttpResponse apiGET() {
		return this.apiGET(null, null);
	}
}