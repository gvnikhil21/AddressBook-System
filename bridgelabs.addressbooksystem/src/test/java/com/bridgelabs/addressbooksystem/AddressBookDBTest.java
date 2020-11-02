package com.bridgelabs.addressbooksystem;

import static org.junit.Assert.assertEquals;

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
		Long expectedPhone = 998877665544l;
		Long phone = null;
		addressBookDBController.updatePhoneContact("Gv", "Nikhil", expectedPhone);
		Contact contact = AddressBookDBController.contactList.stream()
				.filter(con -> (con.getFirstName().equals("Gv")) && (con.getLastName().equals("Nikhil"))).findFirst()
				.orElse(null);
		if (contact != null)
			phone = contact.getPhoneNo();
		assertEquals(expectedPhone, phone);
	}
}
