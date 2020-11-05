package com.bridgelabs.addressbooksystem;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bridgelabs.addressbooksystem.AddressBookServiceController.IOService;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressBookRESTAPITest {
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	@Test
	public void test1_givenContact_WhenAdded_ShouldMatch201Response() {
		Contact[] contactArray = getContactsList();
		AddressBookServiceController addressBookDBController = new AddressBookServiceController(Arrays.asList(contactArray));
		Contact contact = new Contact("Gv", "Nikhil", "main-road", "Hyderabad", "Telangana", 500062, 7896541230l,
				"abc@gmail.com");
		Response response = addContactToJSONServer(contact);
		int statusCode = response.getStatusCode();
		assertEquals(201, statusCode);
		addressBookDBController.addContact(contact, IOService.REST_IO);
		assertEquals(1, addressBookDBController.contactList.size());
	}

	@Test
	public void test2_givenContacts_WhenRetrieved_ShouldMatchCount() {
		Contact[] contactArray = getContactsList();
		AddressBookServiceController addressBookDBController = new AddressBookServiceController(Arrays.asList(contactArray));
		assertEquals(1, addressBookDBController.contactList.size());
	}

	// returns the contact object in json-server
	private Contact[] getContactsList() {
		Response response = RestAssured.get("/contacts");
		AddressBookMain.LOG.info("Contact details(in JSON server): \n" + response.getBody().asString());
		return new Gson().fromJson(response.asString(), Contact[].class);
	}

	// adds contact object to json-server
	private Response addContactToJSONServer(Contact contact) {
		String Json = new Gson().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(Json);
		return request.post("/contacts");
	}

}
