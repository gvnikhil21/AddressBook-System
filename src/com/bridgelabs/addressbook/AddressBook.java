package com.bridgelabs.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressBook {
	private HashMap<String, ArrayList<Contact>> addressBookMap;
	private HashMap<String, HashMap<String, ArrayList<Contact>>> addressBookCityToContactMap;
	private HashMap<String, HashMap<String, ArrayList<Contact>>> addressBookStateToContactMap;

	public AddressBook() {
		addressBookMap = new HashMap<String, ArrayList<Contact>>();
		addressBookCityToContactMap = new HashMap<String, HashMap<String, ArrayList<Contact>>>();
		addressBookStateToContactMap = new HashMap<String, HashMap<String, ArrayList<Contact>>>();
	}

	// getters and setters
	public HashMap<String, ArrayList<Contact>> getAddressBookMap() {
		return addressBookMap;
	}

	// adding addressBook
	public void addAddressBook(String addressBookName) {
		addressBookMap.put(addressBookName, new ArrayList<Contact>());
	}

	// storing city to contact
	public void addCityToContact() {
		List<Contact> contactList = new ArrayList<Contact>();
		for (Map.Entry<String, ArrayList<Contact>> entry : addressBookMap.entrySet()) {
			contactList = entry.getValue().stream().collect(Collectors.toList());
			contactList.stream().forEach(con -> {
				if (addressBookCityToContactMap.containsKey(entry.getKey())) {
					if (addressBookCityToContactMap.get(entry.getKey()).containsKey(con.getCity())) {
						if (addressBookCityToContactMap.get(entry.getKey()).get(con.getCity()).contains(con) == false)
							addressBookCityToContactMap.get(entry.getKey()).get(con.getCity()).add(con);
					} else {
						addressBookCityToContactMap.get(entry.getKey()).put(con.getCity(), new ArrayList<Contact>());
						addressBookCityToContactMap.get(entry.getKey()).get(con.getCity()).add(con);
					}
				} else {
					addressBookCityToContactMap.put(entry.getKey(), new HashMap<String, ArrayList<Contact>>());
					addressBookCityToContactMap.get(entry.getKey()).put(con.getCity(), new ArrayList<Contact>());
					addressBookCityToContactMap.get(entry.getKey()).get(con.getCity()).add(con);
				}
			});
		}
	}

	// storing state to contact
	public void addStateToContact() {
		List<Contact> contactList = new ArrayList<Contact>();
		for (Map.Entry<String, ArrayList<Contact>> entry : addressBookMap.entrySet()) {
			contactList = entry.getValue().stream().collect(Collectors.toList());
			contactList.stream().forEach(con -> {
				if (addressBookStateToContactMap.containsKey(entry.getKey())) {
					if (addressBookStateToContactMap.get(entry.getKey()).containsKey(con.getState())) {
						if (addressBookStateToContactMap.get(entry.getKey()).get(con.getState()).contains(con) == false)
							addressBookStateToContactMap.get(entry.getKey()).get(con.getState()).add(con);
					} else {
						addressBookStateToContactMap.get(entry.getKey()).put(con.getState(), new ArrayList<Contact>());
						addressBookStateToContactMap.get(entry.getKey()).get(con.getState()).add(con);
					}
				} else {
					addressBookStateToContactMap.put(entry.getKey(), new HashMap<String, ArrayList<Contact>>());
					addressBookStateToContactMap.get(entry.getKey()).put(con.getState(), new ArrayList<Contact>());
					addressBookStateToContactMap.get(entry.getKey()).get(con.getState()).add(con);
				}
			});
		}
	}

	// adding contact list
	public boolean addContact(String addressBookName, Contact contact) {
		if (addressBookMap.containsKey(addressBookName)) {
			Contact contactCheck = addressBookMap.get(addressBookName).stream().filter(con -> contact.equals(con))
					.findAny().orElse(null);

			if (contactCheck == null) {
				addressBookMap.get(addressBookName).add(contact);
				addCityToContact();
				addStateToContact();
				return true;
			} else
				return false;
		} else {
			addAddressBook(addressBookName);
			addressBookMap.get(addressBookName).add(contact);
			addCityToContact();
			addStateToContact();
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
		addressBookCityToContactMap.get(addressBookName).get(contact.getCity()).remove(contact);
		addressBookStateToContactMap.get(addressBookName).get(contact.getState()).remove(contact);
	}

	public List<Contact> viewCityContacts(String cityName) {
		List<Contact> contactCityList = new ArrayList<Contact>();
		for (Map.Entry<String, HashMap<String, ArrayList<Contact>>> entry : addressBookCityToContactMap.entrySet()) {
			entry.getValue().get(cityName).stream().forEach(contact -> contactCityList.add(contact));
		}
		return contactCityList;
	}

	public List<Contact> viewStateContacts(String stateName) {
		List<Contact> contactStateList = new ArrayList<Contact>();
		for (Map.Entry<String, HashMap<String, ArrayList<Contact>>> entry : addressBookStateToContactMap.entrySet()) {
			entry.getValue().get(stateName).stream().forEach(contact -> contactStateList.add(contact));
		}
		return contactStateList;
	}
}
