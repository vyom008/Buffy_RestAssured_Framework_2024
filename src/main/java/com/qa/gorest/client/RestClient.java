package com.qa.gorest.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.frameworkexception.APIFrameworkException;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	private Properties prop;
	private String baseURI;
	private boolean isAuthorizationHeaderAdded = false;

	public RestClient(Properties prop, String baseURI) {
		this.prop = prop;
		this.baseURI = baseURI;
	}

	private void addAuthorizationHeader(RequestSpecBuilder specBuilder) {
		if (!isAuthorizationHeaderAdded) {
			specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("tokenId"));
			isAuthorizationHeaderAdded = true;
		}
	}

	private void setRequestContentType(RequestSpecBuilder specBuilder, String contentType) {
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;
		default:
			System.out.println("Please pass the right content type....");
			throw new APIFrameworkException("INVALIDCONTENTTYPE");
		}
	}

	private RequestSpecification createRequestSpec(boolean includeAuth) {
		RequestSpecBuilder specBuilder = new RequestSpecBuilder();
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthorizationHeader(specBuilder);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {
		RequestSpecBuilder specBuilder = new RequestSpecBuilder();
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthorizationHeader(specBuilder);
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryParams,
			boolean includeAuth) {
		RequestSpecBuilder specBuilder = new RequestSpecBuilder();
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthorizationHeader(specBuilder);
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryParams != null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
		RequestSpecBuilder specBuilder = new RequestSpecBuilder();
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthorizationHeader(specBuilder);
		}
		setRequestContentType(specBuilder, contentType);
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object requestBody, String contentType,
			Map<String, String> headersMap, boolean includeAuth) {
		RequestSpecBuilder specBuilder = new RequestSpecBuilder();
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {
			addAuthorizationHeader(specBuilder);
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		setRequestContentType(specBuilder, contentType);
		return specBuilder.build();
	}

	// Http Methods Utils:
	// get
	public Response get(String serviceUrl, boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(includeAuth)).log().all().when().get(serviceUrl);
		}
		return given(createRequestSpec(includeAuth)).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headersMap, boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(headersMap, includeAuth)).log().all().when().get(serviceUrl);
		}
		return given(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headersMap, Map<String, String> queryParams,
			boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(headersMap, queryParams, includeAuth)).log().all().when().get(serviceUrl);
		}
		return given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
	}

	// post
	public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(requestBody, contentType, includeAuth)).log().all().when().post(serviceUrl);
		}
		return given(createRequestSpec(requestBody, contentType, includeAuth)).when().post(serviceUrl);
	}

	public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap,
			boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all().when()
					.post(serviceUrl);
		}
		return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().post(serviceUrl);
	}

	// put
	public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(requestBody, contentType, includeAuth)).log().all().when().put(serviceUrl);
		}
		return given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl);
	}

	public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap,
			boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all().when()
					.put(serviceUrl);
		}
		return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().put(serviceUrl);
	}

	// patch
	public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(requestBody, contentType, includeAuth)).log().all().when().patch(serviceUrl);
		}
		return given(createRequestSpec(requestBody, contentType, includeAuth)).when().patch(serviceUrl);
	}

	public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap,
			boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all().when()
					.patch(serviceUrl);
		}
		return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().patch(serviceUrl);
	}

	// delete
	public Response delete(String serviceUrl, boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(includeAuth)).log().all().when().delete(serviceUrl);
		}
		return given(createRequestSpec(includeAuth)).when().delete(serviceUrl);
	}
	
	//OAuth2.0
	
	public Response getAmadeus(String serviceUrl, Map<String, String> headersMap, Map<String, String> queryParams,
			boolean includeAuth, boolean log) {
		if (log) {
			return given(createRequestSpec(headersMap, queryParams, includeAuth)).relaxedHTTPSValidation().log().all().when().get(serviceUrl);
		}
		return given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
	}
	public String getAccessToken(String serviceUrl, String grantType, String clientId, String clientSecret) {
//		1. POST - get the access token
		
		
		RestAssured.baseURI = "https://test.api.amadeus.com";
		Map<String, String> formParams = new HashMap<>();
		formParams.put("grant_type", grantType);
		formParams.put("client_id", clientId);
		formParams.put("client_secret", clientSecret);
		
        String accessToken = given().log().all().relaxedHTTPSValidation().contentType(ContentType.URLENC)
        .formParams(formParams)
		.when().post(serviceUrl)
		.then().log().all().and().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).extract().path("access_token");
        
        System.out.println(accessToken);
        return accessToken;
	}
}
