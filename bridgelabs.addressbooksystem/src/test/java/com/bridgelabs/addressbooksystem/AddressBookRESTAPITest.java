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
		AddressBookServiceController addressBookDBController = new AddressBookServiceController(
				Arrays.asList(contactArray));
		Contact[] conArray = {
				new Contact("Hinata", "Hyuga", "Himayat-Nagar", "Hyderabad", "Telangana", 500062, 7777777777l,
						"hinata@gmail.com"),
				new Contact("Naruto", "Uzumaki", "Connaught-Place", "New Delhi", "New Delhi", 1100042, 8888888888l,
						"naruto@gmail.com"),
				new Contact("Endou", "Mamoru", "Thane", "Mumbai", "Maharashtra", 4500230, 5555555555l,
						"endou@gmail.com"),
				new Contact("Natsumi", "Raimon", "Whitefield", "Bangalore", "Karnataka", 530068, 4444444444l,
						"natsumi@gmail.com") };
		Arrays.asList(conArray).stream().forEach(contact -> {
			Response response = addContactToJSONServer(contact);
			int statusCode = response.getStatusCode();
			assertEquals(201, statusCode);
		});
		addressBookDBController.addMultipleContacts(Arrays.asList(conArray), IOService.REST_IO);
		assertEquals(4, addressBookDBController.contactList.size());
	}

	@Test
	public void test2_givenContacts_WhenRetrieved_ShouldMatchCount() {
		Contact[] contactArray = getContactsList();
		AddressBookServiceController addressBookDBController = new AddressBookServiceController(
				Arrays.asList(contactArray));
		assertEquals(4, addressBookDBController.contactList.size());
	}

	@Test
	public void test3_givenContactPhone_WhenUpdated_ShouldMatch() {
		String firstName = "Hinata";
		String lastName = "Hyuga";
		long phone = 1234567890l;
		Contact[] contactArray = getContactsList();
		AddressBookServiceController addressBookServiceController = new AddressBookServiceController(
				Arrays.asList(contactArray));
		addressBookServiceController.updatePhoneContact(IOService.REST_IO, firstName, lastName, phone);
		Contact contact = addressBookServiceController.getContact(firstName, lastName);
		String Json = new Gson().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.headers("Content-Type", "application/json");
		request.body(Json);
		Response response = request.put("/contacts/" + contact.getContactId());
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
		assertEquals(phone, contact.getPhoneNo());
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
