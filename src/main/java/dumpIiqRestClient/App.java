package dumpIiqRestClient;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sailpoint.tools.Base64;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        String clientId ="ectAJlZCfeb1AvhGgozgj7dkYl7gDVLa";
        String clientSecret="m34XrqOjqDjz9HSx";
        String baseUrl="http://192.168.243.133:8080/iiq/oauth2";
       String token =  login(baseUrl,clientId,clientSecret);
       getAll(baseUrl,token);
        
    }
    public static void getAll(String baseUrl,String token) {
    	List<CustomApplication> customApplications = new ArrayList<CustomApplication>();
    	Client client = ClientBuilder.newClient();
		String apiURL = baseUrl + "/myOAuthAPI/test2";
		Response response1 = (Response) client.target(apiURL).request("application/json;charset=UTF-8"). // Request type
		accept("application/json;charset=UTF-8"). // Response access type - application/scim+json
		header("Authorization", token).get(); // header with access token as authorization value
	//	customApplications = response1.readEntity(new GenericType<List<CustomApplication>>() {}); // reading response as string format
		try {
		    if (response1.getStatus() == Response.Status.OK.getStatusCode()) {
		    	customApplications = response1.readEntity(new GenericType<List<CustomApplication>>() {});	
		    	if(null != customApplications && !customApplications.isEmpty()) {
		    	//	logger.info("result List size:"+resultList.size());
		    	}
		    }
		    else {
				logger.info("checkpost_3");
				// if they put the custom error stuff in the entity       	
				GenericType<ErrorDTO> errorMessage = new GenericType<ErrorDTO>() {};
				ErrorDTO errorObject= response1.readEntity( errorMessage) ;
				logger.info("httpCode:"+errorObject.getHttpCode());
				logger.info("message:"+errorObject.getMessage());				
		    }          
		}
		catch (ProcessingException ex){
			logger.info("exception",ex);
		}
		//logger.info("get data finish");
    }
    public static String login(String baseUrl,String clientId,String clientSecret) {
    	  Client client = ClientBuilder.newClient();
   		String tokenURL = baseUrl+"/generateToken";
   		String grantType = "client_credentials";
   		MultivaluedMap<String, String> formData = new MultivaluedHashMap<String,String>();

   		formData.add("grant_type", grantType);

   		String secret = "Basic " + Base64.encodeBytes(new String(clientId + ":" + clientSecret).getBytes()); 
   		// we	// should 	// use
   																												// Base64
   																												// encode
   																												// to
   																					// client
   																												// id
   																												// and
   																												// client
   																												// secret
   		Response response = (Response) client.target(tokenURL). // token URL to get access token
   				request("application/json"). // JSON Request Type
   				accept("application/json"). // Response access type - application/scim+json
   				header("Authorization", secret) // Authorization header goes here
   				.post(Entity.json(null)); // body with grant type

   		String token = response.readEntity(String.class); // reading response as string format
   		logger.info("Headers: "+response.getHeaders());
   		logger.info("token:"+token);
   		int responseCode = response.getStatus();
   		logger.info("response_code:"+responseCode);
   		String accessToken = null;
   		try {
   			JSONObject jsonObject = new JSONObject(token);
   	   		jsonObject.put("error_code", responseCode);
   	   		
   	   		String expiresInString = null;
   			accessToken = "Bearer " + jsonObject.getString("access_token");	
   			expiresInString = jsonObject.getString("expires_in");
   			
   		}catch( Exception e){
   			
   			logger.error("error",e);
   			
   		}
   		return accessToken;
    }
}
