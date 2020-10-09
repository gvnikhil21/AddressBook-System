package com.bridgelabs.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressBook {
	private HashMap<String, ArrayList<Contact>> addressBookMap;
	private Map<String, List<Contact>> CityToContactMap;
	private Map<String, List<Contact>> StateToContactMap;

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

	// storing city to contact
	public void addCityToContact() {
		CityToContactMap = addressBookMap.values().stream().flatMap(list -> list.stream())
				.collect(Collectors.groupingBy(Contact::getCity, Collectors.toList()));
	}

	// storing state to contact
	public void addStateToContact() {
		StateToContactMap = addressBookMap.values().stream().flatMap(list -> list.stream())
				.collect(Collectors.groupingBy(Contact::getState, Collectors.toList()));
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
		Contact contact = null;
		if (addressBookMap.containsKey(addressBookName)) {
			contact = addressBookMap.get(addressBookName).stream()
					.filter(con -> (con.getFirstName() + " " + con.getLastName()).equals(fullName)).findAny()
					.orElse(null);
		}

		return contact;
	}

	// delete contact
	public void deleteContact(String addressBookName, Contact contact) {
		addressBookMap.get(addressBookName).remove(contact);
	}

	// view contacts by city
	public List<Contact> viewCityContacts(String cityName) {
		addCityToContact();
		return CityToContactMap.get(cityName);
	}

	// view contacts by state
	public List<Contact> viewStateContacts(String stateName) {
		addStateToContact();
		return StateToContactMap.get(stateName);
	}

	public List<Contact> sortContactByName(String addressBookName) {
		List<Contact> sortedList = new ArrayList<Contact>();
		if (addressBookMap.containsKey(addressBookName))
			addressBookMap.get(addressBookName).stream()
					.sorted((con1, con2) -> (con1.getFirstName()).compareTo(con2.getFirstName()))
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
