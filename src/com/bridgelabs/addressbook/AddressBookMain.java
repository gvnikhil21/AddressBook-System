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
					"Enter the choice number:\n1. Add AddressBook\n2. Add Contact\n3. View all Contacts\n4. Edit Existing Contact by Full Name\n5. Remove Contact by Full Name\n6. Exit");
			choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:
				addressBookMain.createAddressbook(addressBook);
				break;
			case 2:
				addressBookMain.createContact(addressBook);
				break;
			case 3:
				addressBookMain.viewContacts(addressBook);
				break;
			case 4:
				addressBookMain.editContact(addressBook);
				break;
			case 5:
				addressBookMain.deleteContact(addressBook);
				break;
			case 6:
				System.out.println("You have quit the program!");
				break;
			default:
				System.out.println("Invalid choice! Select a valid choice.\n");
				break;
			}
		} while (choice != 6);
	}

	private void createAddressbook(AddressBook addressBook) {
		System.out.println("Enter the name of AddressBook: ");
		String addressBookName = sc.nextLine();
		addressBook.addAddressBook(addressBookName);
		System.out.println("AddressBook added successfully!\n");
	}

	private void deleteContact(AddressBook addressBook) {
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

	private void createContact(AddressBook addressBook) {
		System.out.println("Enter the addressBook name to which you want to add contact");
		String addressBookName = sc.nextLine();
		Contact contact = new Contact();
		System.out.println("Create a contact");
		createOrEditContact(contact);
		addressBook.addContact(addressBookName, contact);
		System.out.println("Contact added successfully!\n");
	}

	private void editContact(AddressBook addressBook) {
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

	private void viewContacts(AddressBook addressBook) {
		System.out.println("Enter the addressBook name whose contacts you want to see");
		String addressBookName = sc.nextLine();
		ArrayList<Contact> contactList = addressBook.getContactList(addressBookName);
		if (contactList == null || contactList.size() == 0)
			System.out.println("No contacts in the" + addressBookName + " Address Book to display!\n");
		else {
			System.out.println("\nContacts in the " + addressBookName + " Address Book are: ");
			int countContact = 0;
			for (Contact contact : contactList) {
				countContact++;
				System.out.println("Contact " + countContact + " : ");
				displayContactDetails(contact);
			}
		}

	}

	// display contact details
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
