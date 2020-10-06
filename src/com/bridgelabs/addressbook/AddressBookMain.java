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
			System.out.println("Enter the choice number\n1. Add Contact\n2. View all Contacts\n3. Exit");
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
				System.out.println("You have quit the program!");
				break;
			default:
				System.out.println("Invalid choice! Select a valid choice.");
				break;
			}
		} while (choice != 3);
	}

	private void createContact(AddressBook addressBook) {
		System.out.println("Create a contact");
		System.out.println("Enter first name:");
		String firstName = sc.nextLine();
		System.out.println("Enter last name:");
		String lastName = sc.nextLine();
		System.out.println("Enter address:");
		String address = sc.nextLine();
		System.out.println("Enter city:");
		String city = sc.nextLine();
		System.out.println("Enter state:");
		String state = sc.nextLine();
		System.out.println("Enter zip code:");
		int zipCode = sc.nextInt();
		System.out.println("Enter phone number:");
		long phoneNo = sc.nextLong();
		sc.nextLine();
		System.out.println("Enter email:");
		String email = sc.nextLine();

		Contact contact = new Contact(firstName, lastName, address, city, state, zipCode, phoneNo, email);
		addressBook.addContact(contact);
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
