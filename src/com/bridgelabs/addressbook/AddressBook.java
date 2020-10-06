package com.bridgelabs.addressbook;

import java.util.ArrayList;

public class AddressBook {
	private ArrayList<Contact> contactList;

	// constructor
	public AddressBook() {
		contactList = new ArrayList<Contact>();
	}

	// getters and setters
	public ArrayList<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}

	// adding contact list
	public void addContact(Contact contact) {
		contactList.add(contact);
	}
}
