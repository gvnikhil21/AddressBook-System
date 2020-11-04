package com.bridgelabs.addressbooksystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// add contacts to database
	public void addMultipleContactsToDB(List<Contact> conList) {
		Map<Integer, Boolean> mapAdditionStatus = new HashMap<>();
		conList.stream().forEach(con -> {
			Runnable task = () -> {
				mapAdditionStatus.put(con.hashCode(), false);
				AddressBookMain.LOG.info("Contact being added: " + Thread.currentThread().getName());
				try {
					if (AddressBookDBService.getInstance().addContact(con))
						contactList.add(con);
					mapAdditionStatus.put(con.hashCode(), true);
					AddressBookMain.LOG.info("Contact added: " + Thread.currentThread().getName());
				} catch (AddressBookException e) {
					e.printStackTrace();
				}
			};
			Thread thread = new Thread(task, con.getFirstName() + " " + con.getLastName());
			thread.start();
		});
		while (mapAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				AddressBookMain.LOG.error(e.getMessage());
			}
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
