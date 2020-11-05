package com.bridgelabs.addressbooksystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookServiceController {
	public List<Contact> contactList;

	public enum IOService {
		DB_IO, REST_IO
	}

	public AddressBookServiceController() {
		contactList = new ArrayList<>();
	}

	public AddressBookServiceController(List<Contact> contactList) {
		this();
		contactList.stream().forEach(con -> this.contactList.add(con));
	}

	// reads details from database or json-server and stores to cntactList
	public void readContacts(IOService ioService) {
		if (ioService.equals(IOService.DB_IO)) {
			try {
				contactList = AddressBookDBService.getInstance().readContactsInAddressBook();
			} catch (AddressBookException e) {
				e.printStackTrace();
			}
		}
	}

	// add contacts to database or json server
	public boolean addContact(Contact contact, IOService ioService) {
		if (ioService.equals(IOService.DB_IO)) {
			try {
				if (AddressBookDBService.getInstance().addContact(contact)) {
					contactList.add(contact);
					return true;
				}
			} catch (AddressBookException e) {
				e.printStackTrace();
			}
		}
		if (ioService.equals(IOService.REST_IO)) {
			contactList.add(contact);
			return true;
		}
		return false;
	}

	// add contacts to database
	public void addMultipleContactsToDB(List<Contact> conList) {
		Map<Integer, Boolean> mapAdditionStatus = new HashMap<>();
		conList.stream().forEach(con -> {
			Runnable task = () -> {
				mapAdditionStatus.put(con.hashCode(), false);
				AddressBookMain.LOG.info("Contact being added: " + Thread.currentThread().getName());
				if (addContact(con, IOService.DB_IO)) {
					mapAdditionStatus.put(con.hashCode(), true);
					AddressBookMain.LOG.info("Contact added: " + Thread.currentThread().getName());
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
	public void updatePhoneContact(IOService ioService, String firstName, String lastName, Long phone) {
		if (ioService.equals(IOService.DB_IO)) {
			try {
				AddressBookDBService.getInstance().updateContactPhoneInAddressBook(firstName, lastName, phone);
			} catch (AddressBookException e) {
				e.printStackTrace();
			}
			Contact contact = getContact(firstName, lastName);
			if (contact != null)
				contact.setPhoneNo(phone);
		}

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

	// returns contact by name
	public Contact getContact(String firstName, String lastName) {
		return contactList.stream()
				.filter(con -> con.getFirstName().equals(firstName) && con.getLastName().equals(lastName)).findFirst()
				.orElse(null);
	}
}
