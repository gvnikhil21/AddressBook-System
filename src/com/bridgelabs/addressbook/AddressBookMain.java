package com.bridgelabs.addressbook;

import java.util.Scanner;

public class AddressBookMain {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// welcome message
		System.out.println("Welcome to AddressBook System!");

		AddressBookMain addressBookMain = new AddressBookMain();

		// creates contact
		Contact contact = addressBookMain.createContact();
		// displays contact
		addressBookMain.displayContact(contact);
	}

	private Contact createContact() {
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
		return contact;
	}

	private void displayContact(Contact contact) {
		System.out.println("Details of created contact: ");
		System.out.println("First Name: " + contact.getFirstName());
		System.out.println("Last Name: " + contact.getLastName());
		System.out.println("Address: " + contact.getAddress());
		System.out.println("City: " + contact.getCity());
		System.out.println("State: " + contact.getState());
		System.out.println("Zip Code: " + contact.getZipCode());
		System.out.println("Phone Number: " + contact.getPhoneNo());
		System.out.println("Email: " + contact.getEmail());
	}
}
