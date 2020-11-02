package com.bridgelabs.addressbooksystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookController {
	private static final String TXT_FILE_PATH = "D:\\AssignmentBridgeLabs\\AddressBook-System\\bridgelabs.addressbooksystem\\Output Files\\contactsBook.txt";
	private static final String CSV_FILE_PATH = "D:\\\\AssignmentBridgeLabs\\\\AddressBook-System\\\\bridgelabs.addressbooksystem\\\\Output Files\\\\contactsBook.csv";
	private static final String JSON_FILE_PATH = "D:\\\\AssignmentBridgeLabs\\\\AddressBook-System\\\\bridgelabs.addressbooksystem\\\\Output Files\\\\contactsBook.json";

	// creates contact in specified addressBook
	public void createContact(AddressBook addressBook) throws IOException {
		File file = new File(TXT_FILE_PATH);
		file.createNewFile();
		try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));) {
			AddressBookMain.LOG.info("Enter the addressBook name to which you want to add contact");
			String addressBookName = AddressBookMain.consoleReader.readLine();
			writeContact(addressBook, fileWriter, addressBookName);
			AddressBookMain.LOG.info("Contact written successfully to contactsBook.txt!");
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
	public void storeContacts(AddressBook addressBook) {
		boolean response = addressBook.storeToAddressBook();
		if (response) {
			AddressBookMain.LOG.info("Contact stored successfully!\n");
		} else {
			AddressBookMain.LOG.info("Contact not added!\n");
		}
	}

	// prints all contacts in all addresBooks to console
	public void viewAllContacts(AddressBook addressBook) {
		Map<String, List<Contact>> contactMap = addressBook.getAddressBookMap();
		contactMap.entrySet().stream().forEach(entry -> {
			AddressBookMain.LOG.info("AddressBookName: " + entry.getKey());
			entry.getValue().stream().forEach(contact -> AddressBookMain.LOG.info(contact.getString()));
			AddressBookMain.LOG.info("");
		});
	}

	// edits contact
	public void editContact(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the addressBook name from which you want to edit contact");
		String addressBookName = AddressBookMain.consoleReader.readLine();
		AddressBookMain.LOG.info("Enter full name (firstName<space>lastName)to edit contact");
		String fullName = AddressBookMain.consoleReader.readLine();
		Contact contactToEdit = addressBook.getContactByFullName(addressBookName, fullName);
		if (contactToEdit == null) {
			AddressBookMain.LOG.info("No Contact found with the given full name\n");
		} else {
			createOrEditContact(contactToEdit);
			AddressBookMain.LOG.info("Contact details updated successfully!\n");
		}
	}

	private void createOrEditContact(Contact contact) throws IOException {
		AddressBookMain.LOG.info("Enter first name:");
		contact.setFirstName(AddressBookMain.consoleReader.readLine());
		AddressBookMain.LOG.info("Enter last name:");
		contact.setLastName(AddressBookMain.consoleReader.readLine());
		AddressBookMain.LOG.info("Enter address:");
		contact.setAddress(AddressBookMain.consoleReader.readLine());
		AddressBookMain.LOG.info("Enter city:");
		contact.setCity(AddressBookMain.consoleReader.readLine());
		AddressBookMain.LOG.info("Enter state:");
		contact.setState(AddressBookMain.consoleReader.readLine());
		AddressBookMain.LOG.info("Enter zip code:");
		try {
			contact.setZipCode(Integer.parseInt(AddressBookMain.consoleReader.readLine()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		AddressBookMain.LOG.info("Enter phone number:");
		try {
			contact.setPhoneNo(Long.parseLong(AddressBookMain.consoleReader.readLine()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		AddressBookMain.LOG.info("Enter email:");
		contact.setEmail(AddressBookMain.consoleReader.readLine());
	}

	// deletes contact from specified addressBook
	public void deleteContact(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the addressBook name from which you want to delete contact");
		String addressBookName = AddressBookMain.consoleReader.readLine();
		AddressBookMain.LOG.info("Enter full name (firstName<space>lastName)to remove contact");
		String fullName = AddressBookMain.consoleReader.readLine();
		Contact contact = addressBook.getContactByFullName(addressBookName, fullName);
		if (contact == null) {
			AddressBookMain.LOG.info("Contact not found!\n");
		} else {
			AddressBookMain.LOG.info("Do you want to remove the contact(Y/N)");
			String response = AddressBookMain.consoleReader.readLine();
			if (response.equalsIgnoreCase("Y")) {
				addressBook.deleteContact(addressBookName, contact);
				AddressBookMain.LOG.info("Contact removed successfully!\n");
			} else {
				AddressBookMain.LOG.info("Contact not removed!\n");
			}
		}
	}

	// displays contact by city
	public void viewContactByCity(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the city name to viewContacts");
		String cityName = AddressBookMain.consoleReader.readLine();
		List<Contact> cityContacts = addressBook.viewCityContacts(cityName);
		if (cityContacts == null || cityContacts.size() == 0) {
			AddressBookMain.LOG.info("No contacts in " + cityName + " city\n");
		} else {
			AddressBookMain.LOG.info("List of contacts in city " + cityName + " : ");
			cityContacts.stream().forEach(con -> AddressBookMain.LOG.info(con.getString()));
			AddressBookMain.LOG.info("Found " + cityContacts.stream().count() + " contacts in " + cityName + " city\n");
		}
	}

	// displays contact by state
	public void viewContactByState(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the state name to viewContacts");
		String stateName = AddressBookMain.consoleReader.readLine();
		List<Contact> stateContacts = addressBook.viewStateContacts(stateName);
		if (stateContacts == null || stateContacts.size() == 0) {
			AddressBookMain.LOG.info("No contacts in " + stateName + " state\n");
		} else {
			AddressBookMain.LOG.info("List of contacts in state " + stateName + " : ");
			stateContacts.stream().forEach(con -> AddressBookMain.LOG.info(con.getString()));
			AddressBookMain.LOG
					.info("Found " + stateContacts.stream().count() + " contacts in " + stateName + " state\n");
		}
	}

	// sorts by name
	public void sortByName(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByName(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// sorts by zip code
	public void sortByZipCode(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByZipCode(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// sorts by city
	public void sortByCity(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByCity(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// sorts by state
	public void sortByState(AddressBook addressBook) throws IOException {
		AddressBookMain.LOG.info("Enter the AddressBook name in which you want to sort the contact: ");
		String addressBookName = AddressBookMain.consoleReader.readLine();
		List<Contact> sortedContactList = addressBook.sortContactByState(addressBookName);
		printSortedContacts(sortedContactList, addressBookName);
	}

	// prints sorted contacts
	private void printSortedContacts(List<Contact> sortedContactList, String addressBookName) throws IOException {
		if (sortedContactList == null || sortedContactList.size() == 0) {
			AddressBookMain.LOG.info("Contact list in " + addressBookName + " addressBook empty!\n");
		} else {
			AddressBookMain.LOG.info("Contacts in " + addressBookName + " addressBook: ");
			sortedContactList.stream().forEach(con -> {
				AddressBookMain.LOG.info(con.getString());
			});
			AddressBookMain.LOG.info("");
		}
	}

	// performs csv operations
	public void performCSVOperations(AddressBook addressBook) throws IOException {
		writeToCSVFile(addressBook);
		readFromCSVFile();
		AddressBookMain.LOG.info("Scuccesfully performed CSV Operations!\n");
	}

	// reads contact details from csv file and prints to console
	private void readFromCSVFile() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
		CsvToBean<Contact> beanReader = new CsvToBeanBuilder<Contact>(reader).withType(Contact.class)
				.withIgnoreLeadingWhiteSpace(true).build();
		for (Contact con : beanReader) {
			AddressBookMain.LOG.info(con.getString());
		}
		AddressBookMain.LOG.info("");
	}

	// writes details of contact to csv file
	private void writeToCSVFile(AddressBook addressBook) throws IOException {
		Map<String, List<Contact>> addressBookMap = addressBook.getAddressBookMap();
		List<Contact> contactList = addressBookMap.values().stream().flatMap(list -> list.stream())
				.collect(Collectors.toList());
		Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH));
		StatefulBeanToCsv<Contact> beanWriter = new StatefulBeanToCsvBuilder<Contact>(writer)
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
		try {
			beanWriter.write(contactList);
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	// performs Json operations
	public void performJSONOperation(AddressBook addressBook) throws IOException {
		Gson gson = writeToJSOnFileFromCSVfile();
		readFromJSONFile(gson);
		AddressBookMain.LOG.info("Scuccesfully performed JSON Operations!\n");
	}

	// writes to Json file by reading from Csv file
	private Gson writeToJSOnFileFromCSVfile() throws IOException {
		File file = new File(JSON_FILE_PATH);
		file.createNewFile();
		// reads from csv file
		Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
		CsvToBean<Contact> beanReader = new CsvToBeanBuilder<Contact>(reader).withType(Contact.class)
				.withIgnoreLeadingWhiteSpace(true).build();
		// parses the details to list of contact objects
		List<Contact> contactList = beanReader.parse();
		Gson gson = new Gson();
		String json = gson.toJson(contactList);
		FileWriter writer = new FileWriter(file);
		writer.write(json);
		writer.close();
		return gson;
	}

	// reads from Json file and print in console
	private void readFromJSONFile(Gson gson) throws IOException {
		BufferedReader jsonReader = new BufferedReader(new FileReader(JSON_FILE_PATH));
		Contact[] contactArray = gson.fromJson(jsonReader, Contact[].class);
		List<Contact> contactList = Arrays.asList(contactArray);
		contactList.stream().forEach(con -> AddressBookMain.LOG.info(con.getString()));
		AddressBookMain.LOG.info("");
	}
}
