package com.bridgelabs.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressBook {
	private HashMap<String, ArrayList<Contact>> addressBookMap;

	public AddressBook() {
		addressBookMap = new HashMap<String, ArrayList<Contact>>();
	}

	// getters and setters
	public HashMap<String, ArrayList<Contact>> getAddressBookMap() {
		return addressBookMap;
	}

	// adding addressBook
	public void addAddressBook(String addressBookName) {
		addressBookMap.put(addressBookName, new ArrayList<Contact>());
	}

	// adding contact list
	public boolean addContact(String addressBookName, Contact contact) {
		if (addressBookMap.containsKey(addressBookName)) {
			Contact contactCheck = addressBookMap.get(addressBookName).stream().filter(con -> contact.equals(con))
					.findAny().orElse(null);
			if (contactCheck == null) {
				addressBookMap.get(addressBookName).add(contact);
				return true;
			} else
				return false;
		} else {
			addAddressBook(addressBookName);
			addressBookMap.get(addressBookName).add(contact);
			return true;
		}
	}

	// get contact list of specific addressBook
	public ArrayList<Contact> getContactList(String addressBookName) {
		return addressBookMap.get(addressBookName);
	}

	// get contact by full name
	public Contact getContactByFullName(String addressBookName, String fullName) {
		if (addressBookMap.containsKey(addressBookName)) {
			for (Contact contact : addressBookMap.get(addressBookName)) {
				String name = contact.getFirstName() + " " + contact.getLastName();
				if (name.equals(fullName))
					return contact;
			}
		}
		return null;
	}

	// delete contact
	public void deleteContact(String addressBookName, Contact contact) {
		addressBookMap.get(addressBookName).remove(contact);
	}

	public List<Contact> searchContactByCityOrState(String name) {
		List<Contact> contactListByCityOrState = new ArrayList<Contact>();
		List<Contact> contactList = new ArrayList<Contact>();
		for (Map.Entry<String, ArrayList<Contact>> entry : addressBookMap.entrySet()) {
			contactListByCityOrState = entry.getValue().stream()
					.filter(con -> (con.getCity().equalsIgnoreCase(name) || con.getState().equalsIgnoreCase(name)))
					.collect(Collectors.toList());
			contactListByCityOrState.stream().forEach(con -> contactList.add(con));
		}
		return contactList;
	}
}
