package com.bridgelabs.addressbooksystem;

import java.util.ArrayList;
import java.util.List;

public class AddressBookDBController {
	public static List<Contact> contactList;
	public static AddressBookDBController addressBookDBController;

	private AddressBookDBController() {
		contactList = new ArrayList<>();
	}

	public static AddressBookDBController getInstance() {
		if (addressBookDBController == null)
			addressBookDBController = new AddressBookDBController();
		return addressBookDBController;
	}

	// reads details from database and stores to cntactList
	public void readContactsFromAddressBookDB() {
		try {
			contactList = AddressBookDBService.getInstance().readContactsInAddressBook();
		} catch (AddressBookException e) {
			e.printStackTrace();
		}
	}
}
