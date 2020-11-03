package com.bridgelabs.addressbooksystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

	// updates phone details of contact
	public void updatePhoneContact(String firstName, String lastName, Long phone) {
		try {
			AddressBookDBService.getInstance().updateContactPhoneInAddressBook(firstName, lastName, phone);
		} catch (AddressBookException e) {
			e.printStackTrace();
		}
		readContactsFromAddressBookDB();
	}

	// returns list of contacts added in particular period
	public List<Contact> getContactsAddedInParticularPeriodFromDB(LocalDate start, LocalDate end) {
		try {
			return AddressBookDBService.getInstance().getContactsAddedInParticularPeriod(start, end);
		} catch (AddressBookException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
