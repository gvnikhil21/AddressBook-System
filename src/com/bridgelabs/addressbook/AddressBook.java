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

	// get contact by full name
	public Contact getContactByFullName(String fullName) {
		for (Contact contact : contactList) {
			String name = contact.getFirstName() + " " + contact.getLastName();
			if (name.equals(fullName))
				return contact;
		}
		return null;
	}

	// delete contact
	public void deleteContact(Contact contact) {
		contactList.remove(contact);
	}
}
