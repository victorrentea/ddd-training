/*package com.bnpparibas.peps.web.controller;

import com.bnpparibas.peps.beans.UserRefog;
import com.bnpparibas.peps.business.dto.ClientSortInfo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ExcelSortComparatorTest extends TestCase{
	
	 public ExcelSortComparatorTest( String testName )
	    {
	        super( testName );
	    }

	 public static Test suite()
	    {
	        return new TestSuite( ExcelSortComparatorTest.class );
	    }
	 
	public void testOrderByName() {
		UserRefog o1 = createUserRefog("Aaa", "Bbb", "R1");
		UserRefog o2 = createUserRefog("Ccc", "Ddd", "R2");
		
		ClientSortInfo si = createSortInfo("prenomNom", 1, "role", 1);
		ExcelSortComparator comparator = new ExcelSortComparator(si);
		
		assertEquals(1, comparator.compare(o1, o2));
	}
	
	
	private UserRefog createUserRefog(String nom, String prenom, String role) {
		UserRefog userRefog = new UserRefog();
		userRefog.setNom(nom);
		userRefog.setPrenom(prenom);
		userRefog.setRole(role);
		
		return userRefog;
	}
	
	private ClientSortInfo createSortInfo(String firstCriteria, Integer firstOrder, String secondCriteria, Integer secondOrder) {
		ClientSortInfo sortInfo = new ClientSortInfo();
		sortInfo.setActualSortField(firstCriteria);
		sortInfo.setActualSortFieldOrder(firstOrder);
		sortInfo.setSecondSortField(secondCriteria);
		sortInfo.setSecondSortFieldOrder(secondOrder);
		
		return sortInfo;
	}
	
}
*/