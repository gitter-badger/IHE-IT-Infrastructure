package com.gaduo.ihe;

import java.util.Iterator;

import com.gaduo.zk.model.CompanyInfomation;
import com.gaduo.zk.view_model.CompanyInfoVM;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CompanyInfoVM com = new CompanyInfoVM();
		com.init();
		Iterator<CompanyInfomation> iterator = com.getCompanyInfomations().iterator();
		while(iterator.hasNext()){
			CompanyInfomation c = iterator.next();
			System.out.println(c.toString());
		}
	}

}
