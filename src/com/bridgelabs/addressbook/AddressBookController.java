package com.bridgelabs.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookController {
	private static Scanner sc = new Scanner(System.in);

	public void createAddressbook(AddressBook addressBook) {
		System.out.println("Enter the name of AddressBook: ");
		String addressBookName = sc.nextLine();
		addressBook.addAddressBook(addressBookName);
		System.out.println("AddressBook added successfully!\n");
	}

	public void createContact(AddressBook addressBook) {
		System.out.println("Enter the addressBook name to which you want to add contact");
		String addressBookName = sc.nextLine();
		Contact contact = new Contact();
		System.out.println("Create a contact");
		createOrEditContact(contact);
		boolean response = addressBook.addContact(addressBookName, contact);
		if (response) {
			System.out.println("Contact added successfully!\n");
		}

		else
			System.out.println("Contact not added!\n");
	}

	public void viewContacts(AddressBook addressBook) {
		System.out.println("Enter the addressBook name whose contacts you want to see");
		String addressBookName = sc.nextLine();
		ArrayList<Contact> contactList = addressBook.getContactList(addressBookName);
		if (contactList == null || contactList.size() == 0)
			System.out.println("No contacts in the" + addressBookName + " Address Book to display!\n");
		else {
			System.out.println("\nContacts in the " + addressBookName + " Address Book are: ");
			contactList.stream().forEach((System.out::println));
			System.out.print("\n");
		}
	}

	public void editContact(AddressBook addressBook) {
		System.out.println("Enter the addressBook name from which you want to edit contact");
		String addressBookName = sc.nextLine();
		System.out.println("Enter full name (firstName<space>lastName)to edit contact");
		String fullName = sc.nextLine();
		Contact contactToEdit = addressBook.getContactByFullName(addressBookName, fullName);
		if (contactToEdit == null)
			System.out.println("No Contact found with the given full name");
		else {
			createOrEditContact(contactToEdit);
			System.out.println("Contact details updated successfully!\n");
		}

	}

	private void createOrEditContact(Contact contact) {
		System.out.println("Enter first name:");
		contact.setFirstName(sc.nextLine());
		System.out.println("Enter last name:");
		contact.setLastName(sc.nextLine());
		System.out.println("Enter address:");
		contact.setAddress(sc.nextLine());
		System.out.println("Enter city:");
		contact.setCity(sc.nextLine());
		System.out.println("Enter state:");
		contact.setState(sc.nextLine());
		System.out.println("Enter zip code:");
		contact.setZipCode(sc.nextInt());
		System.out.println("Enter phone number:");
		contact.setPhoneNo(sc.nextLong());
		sc.nextLine();
		System.out.println("Enter email:");
		contact.setEmail(sc.nextLine());
	}

	public void deleteContact(AddressBook addressBook) {
		System.out.println("Enter the addressBook name from which you want to delete contact");
		String addressBookName = sc.nextLine();
		System.out.println("Enter full name (firstName<space>lastName)to remove contact");
		String fullName = sc.nextLine();
		Contact contact = addressBook.getContactByFullName(addressBookName, fullName);
		if (contact == null)
			System.out.println("Contact not found!\n");
		else {
			System.out.println("Do you want to remove the contact(Y/N)");
			String response = sc.nextLine();
			if (response.equalsIgnoreCase("Y")) {
				addressBook.deleteContact(addressBookName, contact);
				System.out.println("Contact removed successfully!\n");
			} else
				System.out.println("Contact not removed!\n");
		}

	}

	public void viewContactByCity(AddressBook addressBook) {
		System.out.println("Enter the city name to viewContacts");
		String cityName = sc.nextLine();
		List<Contact> cityContacts = addressBook.viewCityContacts(cityName);
		if (cityContacts == null || cityContacts.size() == 0)
			System.out.println("No contacts in " + cityName + " city\n");
		else {
			System.out.println("List of contacts in city " + cityName + " : ");
			cityContacts.stream().forEach(System.out::println);
			System.out.println("Found " + cityContacts.stream().count() + " contacts in " + cityName + " city\n");
		}
	}

	public void viewContactByState(AddressBook addressBook) {
		System.out.println("Enter the state name to viewContacts");
		String stateName = sc.nextLine();
		List<Contact> stateContacts = addressBook.viewStateContacts(stateName);
		if (stateContacts == null || stateContacts.size() == 0)
			System.out.println("No contacts in " + stateName + " state\n");
		else {
			System.out.println("List of contacts in state " + stateName + " : ");
			stateContacts.stream().forEach(System.out::println);
			System.out.println("Found " + stateContacts.stream().count() + " contacts in " + stateName + " state\n");
		}
	}

	public void sortByName(AddressBook addressBook) {
		System.out.println("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = sc.nextLine();
		List<Contact> sortedContactList = addressBook.sortContactByName(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	public void sortByZipCode(AddressBook addressBook) {
		System.out.println("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = sc.nextLine();
		List<Contact> sortedContactList = addressBook.sortContactByZipCode(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	public void sortByCity(AddressBook addressBook) {
		System.out.println("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = sc.nextLine();
		List<Contact> sortedContactList = addressBook.sortContactByCity(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	public void sortByState(AddressBook addressBook) {
		System.out.println("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = sc.nextLine();
		List<Contact> sortedContactList = addressBook.sortContactByState(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	private void printSortedContacts(List<Contact> sortedContactList, String addressBookName) {
		if (sortedContactList == null || sortedContactList.size() == 0)
			System.out.println("Contact list in " + addressBookName + " addressBook empty!\n");
		else {
			System.out.println("Contacts in " + addressBookName + " addressBook: ");
			sortedContactList.stream().forEach(System.out::println);
			System.out.print("\n");
		}
	}
}
