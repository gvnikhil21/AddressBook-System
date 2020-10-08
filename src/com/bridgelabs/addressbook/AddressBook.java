package com.bridgelabs.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		addressBookMap.entrySet().stream().forEach(entry -> entry.getValue().stream().forEach(con -> {
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
		}));
	}

	// storing state to contact
	public void addStateToContact() {
		addressBookMap.entrySet().stream().forEach(entry -> entry.getValue().stream().forEach(con -> {
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
		}));
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
		addressBookCityToContactMap.entrySet().stream().forEach(e -> {
			if (e.getValue().containsKey(cityName))
				e.getValue().get(cityName).stream().forEach(con -> contactCityList.add(con));
		});
		return contactCityList;
	}

	public List<Contact> viewStateContacts(String stateName) {
		List<Contact> contactStateList = new ArrayList<Contact>();
		addressBookStateToContactMap.entrySet().stream().forEach(e -> {
			if (e.getValue().containsKey(stateName))
				e.getValue().get(stateName).stream().forEach(con -> contactStateList.add(con));
		});
		return contactStateList;
	}

	public List<Contact> sortContactByName(String addressBookName) {
		List<Contact> sortedList = new ArrayList<Contact>();
		if (addressBookMap.containsKey(addressBookName))
			addressBookMap.get(addressBookName).stream()
					.sorted((con1, con2) -> (con1.getFirstName() + con1.getLastName())
							.compareTo(con2.getFirstName() + con2.getLastName()))
					.forEach(con -> sortedList.add(con));
		return sortedList;
	}

	public List<Contact> sortContactByZipCode(String addressBookName) {
		List<Contact> sortedList = new ArrayList<Contact>();
		if (addressBookMap.containsKey(addressBookName))
			addressBookMap.get(addressBookName).stream().sorted((con1, con2) -> con1.getZipCode() - con2.getZipCode())
					.forEach(con -> sortedList.add(con));
		return sortedList;
	}

	public List<Contact> sortContactByCity(String addressBookName) {
		List<Contact> sortedList = new ArrayList<Contact>();
		if (addressBookMap.containsKey(addressBookName))
			addressBookMap.get(addressBookName).stream()
					.sorted((con1, con2) -> con1.getCity().compareTo(con2.getCity()))
					.forEach(con -> sortedList.add(con));
		return sortedList;
	}

	public List<Contact> sortContactByState(String addressBookName) {
		List<Contact> sortedList = new ArrayList<Contact>();
		if (addressBookMap.containsKey(addressBookName))
			addressBookMap.get(addressBookName).stream()
					.sorted((con1, con2) -> con1.getState().compareTo(con2.getState()))
					.forEach(con -> sortedList.add(con));
		return sortedList;
	}

}
