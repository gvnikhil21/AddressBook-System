package com.bridgelabs.addressbooksystem;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressBookDBTest {
	private static AddressBookDBController addressBookDBController;

	@BeforeClass
	public static void initialize() {
		addressBookDBController = AddressBookDBController.getInstance();
	}

	@Test
	public void test1_readDataFromDB_shouldreturnCorrectCOunt() {
		addressBookDBController.readContactsFromAddressBookDB();
		Integer count = AddressBookDBController.contactList.size();
		assertEquals(Integer.valueOf(4), count);
	}

	@Test
	public void test2_givenData_WhenUpdated_ShouldBeInSyncWithDB() {
		Long expectedPhone = 9988776654l;
		Long phone = null;
		addressBookDBController.updatePhoneContact("Gv", "Nikhil", expectedPhone);
		Contact contact = AddressBookDBController.contactList.stream()
				.filter(con -> (con.getFirstName().equals("Gv")) && (con.getLastName().equals("Nikhil"))).findFirst()
				.orElse(null);
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
		addressBookDBController.addContactToDB(expectedContact);
		actualContact = AddressBookDBController.contactList.stream()
				.filter(con -> con.getFirstName().equals("Manish") && con.getLastName().equals("Sharma")).findFirst()
				.orElse(null);
		assertEquals(expectedContact, actualContact);
	}
}
