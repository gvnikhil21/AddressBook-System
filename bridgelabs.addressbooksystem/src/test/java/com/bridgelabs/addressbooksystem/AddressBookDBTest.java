package com.bridgelabs.addressbooksystem;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bridgelabs.addressbooksystem.AddressBookServiceController.IOService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressBookDBTest {
	private static AddressBookServiceController addressBookDBController;

	@BeforeClass
	public static void initialize() {
		addressBookDBController = new AddressBookServiceController();
	}

	@Test
	public void test1_readDataFromDB_shouldreturnCorrectCOunt() {
		addressBookDBController.readContacts(IOService.DB_IO);
		Integer count = addressBookDBController.contactList.size();
		assertEquals(Integer.valueOf(4), count);
	}

	@Test
	public void test2_givenData_WhenUpdated_ShouldBeInSyncWithDB() {
		Long expectedPhone = 9988776654l;
		Long phone = null;
		addressBookDBController.updatePhoneContact(IOService.DB_IO, "Gv", "Nikhil", expectedPhone);
		Contact contact = addressBookDBController.getContact("Gv", "Nikhil");
		if (contact != null)
			phone = contact.getPhoneNo();
		assertEquals(expectedPhone, phone);
	}

	@Test
	public void test3_givenDatePeriod_WhenContactsRetrievedFromDB_ShouldReturnCorrectCount() {
		Integer count = addressBookDBController
				.getContactsAddedInParticularPeriodFromDB(LocalDate.of(2017, 05, 01), LocalDate.of(2019, 05, 01))
				.size();
		assertEquals(Integer.valueOf(2), count);
	}

	@Test
	public void test4_givenCity_WhenContactsRetrievedFromDB_ShouldReturnCorrectCount() {
		Long count = addressBookDBController.getContactsByCityFromDB("Hyderabad");
		assertEquals(Long.valueOf(1), count);
	}

	@Test
	public void test5_givenState_WhenContactsRetrievedFromDB_ShouldReturnCorrectCount() {
		Long count = addressBookDBController.getContactsByStateFromDB("Odisha");
		assertEquals(Long.valueOf(2), count);
	}

	@Test
	public void test6_givenContact_WhenAddedToDB_ShouldBeInSyncWithDB() {
		Contact expectedContact = new Contact("Manish", "Sharma", "Ring-Road", "Hyderabad", "Telangana", 500062,
				7788996655l, "sharmacba@yahoo.com", LocalDate.now(), "1", "2");
		Contact actualContact = null;
		addressBookDBController.addContact(expectedContact, IOService.DB_IO);
		actualContact = addressBookDBController.contactList.stream()
				.filter(con -> con.getFirstName().equals("Manish") && con.getLastName().equals("Sharma")).findFirst()
				.orElse(null);
		assertEquals(expectedContact, actualContact);
	}

	@Test
	public void test7_givenMultipleContacts_WhenAdded_ShouldBeInSyncWithDB() {
		Contact contactArray[] = {
				new Contact("Rohit", "Sharma", "Ring-Road", "Bangalore", "Karnataka", 530068, 9488996655l,
						"rohitcba@yahoo.com", LocalDate.now(), "1", "2"),
				new Contact("Bhuvi", "Kumar", "Himayat-Nagar", "Hyderabad", "Telangana", 500062, 9788665544l,
						"kumarcba@yahoo.com", LocalDate.now(), "1", "3"),
				new Contact("Pooran", "Steyn", "Palace-Road", "Mysore", "Karnataka", 530068, 9988997788l,
						"steyncba@yahoo.com", LocalDate.now(), "1", "3"),
				new Contact("Garg", "Kaul", "Connaught-Place", "New Delhi", "New Delhi", 110001, 9988996655l,
						"gargcba@yahoo.com", LocalDate.now(), "1", "2") };
		Instant start = Instant.now();
		addressBookDBController.addMultipleContactsToDB(Arrays.asList(contactArray));
		Instant end = Instant.now();
		AddressBookMain.LOG.info("Duration with thread: " + Duration.between(start, end));
		assertEquals(9, addressBookDBController.contactList.size());
	}

}
