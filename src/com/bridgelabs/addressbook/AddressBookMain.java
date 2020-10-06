package com.bridgelabs.addressbook;

import java.util.ArrayList;
import java.util.Scanner;

public class AddressBookMain {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int choice;

		// welcome message
		System.out.println("Welcome to AddressBook System!");

		AddressBookMain addressBookMain = new AddressBookMain();
		AddressBook addressBook = new AddressBook();
		do {
			System.out.println(
					"Enter the choice no\n1. Add Contact\n2. View all Contacts\n3. Edit Existing Contact by Full Name\n4. Exit");
			choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:
				addressBookMain.createContact(addressBook);
				break;
			case 2:
				addressBookMain.viewContacts(addressBook);
				break;
			case 3:
				addressBookMain.editContact(addressBook);
				break;
			case 4:
				System.out.println("You have quit the program!");
				break;
			default:
				System.out.println("Invalid choice! Select a valid choice.");
				break;
			}
		} while (choice != 4);
	}

	private void createContact(AddressBook addressBook) {
		Contact contact = new Contact();
		System.out.println("Create a contact");
		createOrEditContact(contact);
		addressBook.addContact(contact);
		System.out.println("Contact added successfully!");
	}

	private void editContact(AddressBook addressBook) {
		System.out.println("Enter full name (firstName<space>lastName)to edit contact");
		String fullName = sc.nextLine();
		Contact contactToEdit = addressBook.getContactByFullName(fullName);
		if (contactToEdit == null)
			System.out.println("No Contact found with the given full name");
		else {
			createOrEditContact(contactToEdit);
			System.out.println("Contact details updated successfully!");
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

	private void viewContacts(AddressBook addressBook) {
		ArrayList<Contact> contactList = addressBook.getContactList();
		if (contactList.size() == 0)
			System.out.println("No contacts in the Address Book to display!");
		else {
			System.out.println("\nContacts in the Address Book are: ");
			int countContact = 0;
			for (Contact contact : contactList) {
				countContact++;
				System.out.println("Contact " + countContact + " : ");
				displayContactDetails(contact);
			}
		}

	}

	private static void displayContactDetails(Contact contact) {
		System.out.println("First Name: " + contact.getFirstName());
		System.out.println("Last Name: " + contact.getLastName());
		System.out.println("Address: " + contact.getAddress());
		System.out.println("City: " + contact.getCity());
		System.out.println("State: " + contact.getState());
		System.out.println("Zip Code: " + contact.getZipCode());
		System.out.println("Phone Number: " + contact.getPhoneNo());
		System.out.println("Email: " + contact.getEmail() + "\n");
	}
}
