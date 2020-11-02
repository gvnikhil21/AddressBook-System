package com.bridgelabs.addressbooksystem;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressBookDBTest {
	@Test
	public void test1_readDataFromDB_shouldreturnCorrectCOunt() {
		AddressBookDBController.getInstance().readContactsFromAddressBookDB();
		Integer count = AddressBookDBController.contactList.size();
		assertEquals(Integer.valueOf(4), count);
	}
}
