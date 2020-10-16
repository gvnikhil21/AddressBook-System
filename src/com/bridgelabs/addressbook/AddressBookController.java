package com.bridgelabs.addressbook;

import java.io.*;
import java.util.*;

public class AddressBookController {

	// creates contact in specified addressBook
	public void createContact(AddressBook addressBook) throws IOException {
		File file = new File("D:\\AssignmentBridgeLabs\\AddressBook System\\Output Files\\contactsBook.txt");
		file.createNewFile();
		try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));) {
			AddressBookMain.consoleWriter.write("Enter the addressBook name to which you want to add contact\n");
			AddressBookMain.consoleWriter.flush();
			String addressBookName = AddressBookMain.consoleReader.readLine();
			writeContact(addressBook, fileWriter, addressBookName);
			AddressBookMain.consoleWriter.write("Contact written successfully to contactsBook.txt!\n");
			AddressBookMain.consoleWriter.flush();
		}
	}

	// writes all contacts in contactsBook.txt
	public void writeContact(AddressBook addressBook, BufferedWriter fileWriter, String addressBookName)
			throws IOException {
		Contact contact = new Contact();
		createOrEditContact(contact);
		fileWriter.write(addressBookName + "," + contact.toString());
	}

	// stores contact to map by reading date from contactsBook.txt file
	public void storeContacts(AddressBook addressBook) throws IOException {
		boolean response = addressBook.storeToAddressBook();
		if (response) {
			AddressBookMain.consoleWriter.write("Contact stored successfully!\n\n");
			AddressBookMain.consoleWriter.flush();
		} else {
			AddressBookMain.consoleWriter.write("Contact not added!\n\n");
			AddressBookMain.consoleWriter.flush();
		}
	}

	// prints all contacts in all addresBooks to console
	public void viewAllContacts(AddressBook addressBook) {
		Map<String, List<Contact>> contactMap = addressBook.getAddressBookMap();
		contactMap.entrySet().stream().forEach(entry -> {
			try {
				AddressBookMain.consoleWriter.write("AddressBookName: " + entry.getKey() + "\n");
				AddressBookMain.consoleWriter.flush();
				entry.getValue().stream().forEach(contact -> {
					try {
						AddressBookMain.consoleWriter.write(contact.getString() + "\n");
						AddressBookMain.consoleWriter.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	// edits contact
	public void editContact(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the addressBook name from which you want to edit contact\n");
		AddressBookMain.consoleWriter.flush();
		String addressBookName = AddressBookMain.consoleReader.readLine();
		AddressBookMain.consoleWriter.write("Enter full name (firstName<space>lastName)to edit contact\n");
		AddressBookMain.consoleWriter.flush();
		String fullName = AddressBookMain.consoleReader.readLine();
		Contact contactToEdit = addressBook.getContactByFullName(addressBookName, fullName);
		if (contactToEdit == null) {
			AddressBookMain.consoleWriter.write("No Contact found with the given full name\n\n");
			AddressBookMain.consoleWriter.flush();
		} else {
			createOrEditContact(contactToEdit);
			AddressBookMain.consoleWriter.write("Contact details updated successfully!\n\n");
			AddressBookMain.consoleWriter.flush();
		}
	}

	private void createOrEditContact(Contact contact) throws IOException {
		AddressBookMain.consoleWriter.write("Enter first name:\n");
		AddressBookMain.consoleWriter.flush();
		contact.setFirstName(AddressBookMain.consoleReader.readLine());
		AddressBookMain.consoleWriter.write("Enter last name:\n");
		AddressBookMain.consoleWriter.flush();
		contact.setLastName(AddressBookMain.consoleReader.readLine());
		AddressBookMain.consoleWriter.write("Enter address:\n");
		AddressBookMain.consoleWriter.flush();
		contact.setAddress(AddressBookMain.consoleReader.readLine());
		AddressBookMain.consoleWriter.write("Enter city:\n");
		AddressBookMain.consoleWriter.flush();
		contact.setCity(AddressBookMain.consoleReader.readLine());
		AddressBookMain.consoleWriter.write("Enter state:\n");
		AddressBookMain.consoleWriter.flush();
		contact.setState(AddressBookMain.consoleReader.readLine());
		AddressBookMain.consoleWriter.write("Enter zip code:\n");
		AddressBookMain.consoleWriter.flush();
		try {
			contact.setZipCode(Integer.parseInt(AddressBookMain.consoleReader.readLine()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		AddressBookMain.consoleWriter.write("Enter phone number:\n");
		AddressBookMain.consoleWriter.flush();
		try {
			contact.setPhoneNo(Long.parseLong(AddressBookMain.consoleReader.readLine()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		AddressBookMain.consoleWriter.write("Enter email:\n");
		AddressBookMain.consoleWriter.flush();
		contact.setEmail(AddressBookMain.consoleReader.readLine());
	}

	// deletes contact from specified addressBook
	public void deleteContact(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the addressBook name from which you want to delete contact\n");
		AddressBookMain.consoleWriter.flush();
		String addressBookName = AddressBookMain.consoleReader.readLine();
		AddressBookMain.consoleWriter.write("Enter full name (firstName<space>lastName)to remove contact\n");
		AddressBookMain.consoleWriter.flush();
		String fullName = AddressBookMain.consoleReader.readLine();
		Contact contact = addressBook.getContactByFullName(addressBookName, fullName);
		if (contact == null) {
			AddressBookMain.consoleWriter.write("Contact not found!\n\n");
			AddressBookMain.consoleWriter.flush();
		} else {
			AddressBookMain.consoleWriter.write("Do you want to remove the contact(Y/N)\n");
			AddressBookMain.consoleWriter.flush();
			String response = AddressBookMain.consoleReader.readLine();
			if (response.equalsIgnoreCase("Y")) {
				addressBook.deleteContact(addressBookName, contact);
				AddressBookMain.consoleWriter.write("Contact removed successfully!\n\n");
				AddressBookMain.consoleWriter.flush();
			} else {
				AddressBookMain.consoleWriter.write("Contact not removed!\n\n");
				AddressBookMain.consoleWriter.flush();
			}
		}
	}

	// displays contact by city
	public void viewContactByCity(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the city name to viewContacts\n");
		AddressBookMain.consoleWriter.flush();
		String cityName = AddressBookMain.consoleReader.readLine();
		List<Contact> cityContacts = addressBook.viewCityContacts(cityName);
		if (cityContacts == null || cityContacts.size() == 0) {
			AddressBookMain.consoleWriter.write("No contacts in " + cityName + " city\n\n");
			AddressBookMain.consoleWriter.flush();
		} else {
			AddressBookMain.consoleWriter.write("List of contacts in city " + cityName + " : \n");
			AddressBookMain.consoleWriter.flush();
			cityContacts.stream().forEach(con -> {
				try {
					AddressBookMain.consoleWriter.write(con.getString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			AddressBookMain.consoleWriter
					.write("Found " + cityContacts.stream().count() + " contacts in " + cityName + " city\n\n");
			AddressBookMain.consoleWriter.flush();
		}
	}

	// displays contact by state
	public void viewContactByState(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the state name to viewContacts\n");
		AddressBookMain.consoleWriter.flush();
		String stateName = AddressBookMain.consoleReader.readLine();
		List<Contact> stateContacts = addressBook.viewStateContacts(stateName);
		if (stateContacts == null || stateContacts.size() == 0) {
			AddressBookMain.consoleWriter.write("No contacts in " + stateName + " state\n\n");
			AddressBookMain.consoleWriter.flush();
		} else {
			AddressBookMain.consoleWriter.write("List of contacts in state " + stateName + " : \n");
			AddressBookMain.consoleWriter.flush();
			stateContacts.stream().forEach(con -> {
				try {
					AddressBookMain.consoleWriter.write(con.getString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			AddressBookMain.consoleWriter
					.write("Found " + stateContacts.stream().count() + " contacts in " + stateName + " state\n\n");
			AddressBookMain.consoleWriter.flush();
		}
	}

	// sorts by name
	public void sortByName(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the AddressBook name in which you want to sort the contact: \n");
		AddressBookMain.consoleWriter.flush();
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByName(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// sorts by zip code
	public void sortByZipCode(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the AddressBook name in which you want to sort the contact: \n");
		AddressBookMain.consoleWriter.flush();
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByZipCode(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// sorts by city
	public void sortByCity(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the AddressBook name in which you want to sort the contact: \n");
		AddressBookMain.consoleWriter.flush();
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByCity(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// sorts by state
	public void sortByState(AddressBook addressBook) throws IOException {
		AddressBookMain.consoleWriter.write("Enter the AddressBook name in which you want to sort the contact: \n");
		AddressBookMain.consoleWriter.flush();
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByState(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// prints sorted contacts
	private void printSortedContacts(List<Contact> sortedContactList, String addressBookName) throws IOException {
		if (sortedContactList == null || sortedContactList.size() == 0) {
			AddressBookMain.consoleWriter.write("Contact list in " + addressBookName + " addressBook empty!\n\n");
			AddressBookMain.consoleWriter.flush();
		} else {
			AddressBookMain.consoleWriter.write("Contacts in " + addressBookName + " addressBook: \n");
			AddressBookMain.consoleWriter.flush();
			sortedContactList.stream().forEach(con -> {
				try {
					AddressBookMain.consoleWriter.write(con.getString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			AddressBookMain.consoleWriter.write("\n");
			AddressBookMain.consoleWriter.flush();
		}
	}
}
