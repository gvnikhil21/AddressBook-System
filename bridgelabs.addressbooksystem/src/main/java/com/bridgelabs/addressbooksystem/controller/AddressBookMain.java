package com.bridgelabs.addressbooksystem.controller;

import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bridgelabs.addressbooksystem.controller.AddressBookServiceController.IOService;

public class AddressBookMain {
	public static Logger LOG = LogManager.getLogger(AddressBookMain.class);
	public static BufferedReader consoleReader;

	public static void main(String[] args) {
		int choice = 0;
		// creating a directory to store output files
		File outputFile = new File(
				"D:\\AssignmentBridgeLabs\\AddressBook-System\\bridgelabs.addressbooksystem\\Output Files");
		outputFile.mkdir();

		AddressBookController addressBookController = new AddressBookController();
		AddressBookServiceController addressBookServiceController = new AddressBookServiceController();
		try {
			consoleReader = new BufferedReader(new InputStreamReader(System.in));
			LOG.info("Welcome to AddressBook System!\n");
			do {
				LOG.info(
						"Enter the choice number:\n1. Add Contact\n2. Store Contacts \n3. View All Contacts\n4. Edit Existing Contact by Full Name\n5. Remove Contact by Full Name\n6. View Contacts by City\n7. View Contacts by State\n8. Sort Contact by Name\n9. Sort Contact by Zip Code\n10. Sort Contact by City\n11. Sort Contact by State\n12. Write and read details from and to CSV file\n13. Read from csv and write to JSON file\n14.Read contacts from addressbook database\n15. Exit\n");
				try {
					choice = Integer.parseInt(consoleReader.readLine());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				switch (choice) {
				case 1:
					addressBookController.createContact();
					addressBookController.storeContacts();
					break;
				case 2:
					addressBookController.storeContacts();
					break;
				case 3:
					addressBookController.viewAllContacts();
					break;
				case 4:
					addressBookController.editContact();
					break;
				case 45:
					addressBookController.deleteContact();
					break;
				case 6:
					addressBookController.viewContactByCity();
					break;
				case 7:
					addressBookController.viewContactByState();
					break;
				case 8:
					addressBookController.sortByName();
					break;
				case 9:
					addressBookController.sortByZipCode();
					break;
				case 10:
					addressBookController.sortByCity();
					break;
				case 11:
					addressBookController.sortByState();
					break;
				case 12:
					addressBookController.performCSVOperations();
					break;
				case 13:
					addressBookController.performJSONOperation();
					break;
				case 14:
					addressBookServiceController.readContacts(IOService.DB_IO);
					break;
				case 15:
					LOG.info("You have quit the program!");
					break;
				default:
					LOG.info("Invalid choice! Select a valid choice.\n\n");
					break;
				}
			} while (choice != 15);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				consoleReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
