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

	// add contacts to database
	public void addContactToDB(Contact contact) {
		try {
			if (AddressBookDBService.getInstance().addContact(contact))
				contactList.add(contact);
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

	// returns the count of contacts present in a city from database
	public long getContactsByCityFromDB(String city) {
		try {
			return AddressBookDBService.getInstance().getContactsByCity(city);
		} catch (AddressBookException e) {
			e.printStackTrace();
		}
		return 0l;
	}

	// returns the count of contacts present in a state from database
	public long getContactsByStateFromDB(String state) {
		try {
			return AddressBookDBService.getInstance().getContactsByState(state);
		} catch (AddressBookException e) {
			e.printStackTrace();
		}
		return 0l;
	}

}
