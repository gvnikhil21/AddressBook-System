package com.bridgelabs.addressbooksystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	// adds contact to database
	public boolean addContact(Contact contact) throws AddressBookException {
		Connection con = null;
		PreparedStatement conStatement = null;
		ResultSet resultSet = null;
		int rowsAffected = -1;
		con = DatabaseConnector.getConnection();
		// adds to contact table in database
		try {
			con.setAutoCommit(false);
			String query = "insert into contact (first_name,last_name,address,city,state,zip,phone_no,email_id,date_added) values (?,?,?,?,?,?,?,?,?)";
			conStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			conStatement.setString(1, contact.getFirstName());
			conStatement.setString(2, contact.getLastName());
			conStatement.setString(3, contact.getAddress());
			conStatement.setString(4, contact.getCity());
			conStatement.setString(5, contact.getState());
			conStatement.setInt(6, contact.getZipCode());
			conStatement.setLong(7, contact.getPhoneNo());
			conStatement.setString(8, contact.getEmail());
			conStatement.setDate(9, Date.valueOf(contact.getDateAdded()));
			rowsAffected = conStatement.executeUpdate();
			if (rowsAffected == 0) {
				return false;
			}
			resultSet = conStatement.getGeneratedKeys();
			if (resultSet.next())
				contact.setContactId(String.valueOf(resultSet.getInt(1)));
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				return false;
			} catch (SQLException e1) {
				throw new AddressBookException(e.getMessage());
			}
		}
		// adds to addressbook_contact table in database
		try {
			String query = "insert into addressbook_contact (contact_id,id_addressbook_name,id_addressbook_type) values (?,?,?)";
			conStatement = con.prepareStatement(query);
			conStatement.setInt(1, Integer.valueOf(contact.getContactId()));
			conStatement.setInt(2, Integer.valueOf(contact.getAddressBookId()));
			conStatement.setInt(3, Integer.valueOf(contact.getAddressBookTypeId()));
			rowsAffected = conStatement.executeUpdate();
			if (rowsAffected == 0)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				return false;
			} catch (SQLException e1) {
				throw new AddressBookException(e.getMessage());
			}
		}

		try {
			con.commit();
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new AddressBookException(e.getMessage());
			}
		}
		return true;
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

	// returns the count of contacts present in a city
	public long getContactsByCity(String city) throws AddressBookException {
		long count = 0l;
		try (Connection con = DatabaseConnector.getConnection()) {
			String query = "select count(contact_id) from contact where city=?";
			PreparedStatement conStatement = con.prepareStatement(query);
			conStatement.setString(1, city);
			ResultSet resultSet = conStatement.executeQuery();
			if (resultSet.next())
				count = resultSet.getLong(1);
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage());
		}
		return count;
	}

	// returns the count of contacts present in a state
	public long getContactsByState(String state) throws AddressBookException {
		long count = 0l;
		try (Connection con = DatabaseConnector.getConnection()) {
			String query = "select count(contact_id) from contact where state=?";
			PreparedStatement conStatement = con.prepareStatement(query);
			conStatement.setString(1, state);
			ResultSet resultSet = conStatement.executeQuery();
			if (resultSet.next())
				count = resultSet.getLong(1);
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage());
		}
		return count;
	}

	// returns list of contacts present in result-set for a query retrieved from DB
	private List<Contact> getDataInDB(ResultSet resultSet) throws AddressBookException {
		List<Contact> contactList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				Integer zipCode = resultSet.getInt("zip");
				Long phoneNo = resultSet.getLong("phone_no");
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
