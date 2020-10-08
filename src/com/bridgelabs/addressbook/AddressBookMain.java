package com.bridgelabs.addressbook;

import java.util.Scanner;

public class AddressBookMain {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int choice;

		// welcome message
		System.out.println("Welcome to AddressBook System!");

		AddressBookController addressBookController = new AddressBookController();
		AddressBook addressBook = new AddressBook();
		do {
			System.out.println(
					"Enter the choice number:\n1. Add AddressBook\n2. Add Contact\n3. View all Contacts\n4. Edit Existing Contact by Full Name\n5. Remove Contact by Full Name\n6. View Contacts by City\n7. View Contacts by State\n8. Sort Contact by Name\n9. Sort Contact by Zip Code\n10. Sort Contact by City\n11. Sort Contact by State\n12. Exit");
			choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:
				addressBookController.createAddressbook(addressBook);
				break;
			case 2:
				addressBookController.createContact(addressBook);
				break;
			case 3:
				addressBookController.viewContacts(addressBook);
				break;
			case 4:
				addressBookController.editContact(addressBook);
				break;
			case 5:
				addressBookController.deleteContact(addressBook);
				break;
			case 6:
				addressBookController.viewContactByCity(addressBook);
				break;
			case 7:
				addressBookController.viewContactByState(addressBook);
				break;
			case 8:
				addressBookController.sortByName(addressBook);
				break;
			case 9:
				addressBookController.sortByZipCode(addressBook);
				break;
			case 10:
				addressBookController.sortByCity(addressBook);
				break;
			case 11:
				addressBookController.sortByState(addressBook);
				break;
			case 12:
				System.out.println("You have quit the program!");
				break;
			default:
				System.out.println("Invalid choice! Select a valid choice.\n");
				break;
			}
		} while (choice != 12);
	}
}
