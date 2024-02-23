package com.kavi.DLT.Utilities;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

	@DataProvider(name = "TestData")
	public Object[][] testData() {
		return new Object[][] { 
			{ "OMI-0085", "Omfys@123" },
			{ "OMI-0086", "Omfys@123" },
			{ "OMI-0001", "Omfys@123" },
			{ "OMI-1003", "Omfys@123" },
		};
	}
}
