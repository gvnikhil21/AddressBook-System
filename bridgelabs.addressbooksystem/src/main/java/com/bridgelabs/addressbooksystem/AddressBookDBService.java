package com.bridgelabs.addressbooksystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
	public static AddressBookDBService addressBookDBService;

	// private constructor
	private AddressBookDBService() {

	}

	// ensures singleton property
	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}

	// reads address-book from database and returns the list of contacts
	public List<Contact> readContactsInAddressBook() throws AddressBookException {
		List<Contact> contactList = new ArrayList<>();
		try (Connection con = DatabaseConnector.getConnection()) {
			String query = "select * from contact";
			PreparedStatement conStatement = con.prepareStatement(query);
			ResultSet resultSet = conStatement.executeQuery();
			contactList = getDataInDB(resultSet);
			AddressBookMain.LOG.info("read contact details from database successfully");
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage());
		}
		return contactList;
	}

	// updates contact details in database
	public void updateContactPhoneInAddressBook(String fisrtName, String lastName, Long phone)
			throws AddressBookException {
		try (Connection con = DatabaseConnector.getConnection()) {
			String query = "update contact set phone_no=? where first_name=? and last_name=?";
			PreparedStatement conStatement = con.prepareStatement(query);
			conStatement.setLong(1, phone);
			conStatement.setString(2, fisrtName);
			conStatement.setString(3, lastName);
			int status = conStatement.executeUpdate();
			if (status > 0)
				AddressBookMain.LOG.info("updated contact details in database successfully");
			else
				AddressBookMain.LOG.info("contact details not updated");
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage());
		}
	}

	// returns the list of contacts added in particular period
	public List<Contact> getContactsAddedInParticularPeriod(LocalDate start, LocalDate end)
			throws AddressBookException {
		List<Contact> contactList = new ArrayList<>();
		try (Connection con = DatabaseConnector.getConnection()) {
			String query = "select * from contact where date_added between ? and ?";
			PreparedStatement conStatement = con.prepareStatement(query);
			conStatement.setDate(1, Date.valueOf(start));
			conStatement.setDate(2, Date.valueOf(end));
			ResultSet resultSet = conStatement.executeQuery();
			contactList = getDataInDB(resultSet);
			AddressBookMain.LOG.info("Contacts added in particular period retrieved successfully");
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage());
		}
		return contactList;
	}

	private List<Contact> getDataInDB(ResultSet resultSet) throws AddressBookException {
		List<Contact> contactList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				int zipCode = resultSet.getInt("zip");
				long phoneNo = resultSet.getLong("phone_no");
				String email = resultSet.getString("email_id");
				String contactId = String.valueOf(resultSet.getInt("contact_id"));
				contactList.add(
						new Contact(firstName, lastName, address, city, state, zipCode, phoneNo, email, contactId));
			}
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage());
		}
		return contactList;
	}
}
