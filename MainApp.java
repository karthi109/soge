package com.cts.unoadm.main;

import com.cts.unoadm.exception.StudentAdmissionException;
import com.cts.unoadm.service.StudentAdmissionService;
import com.cts.unoadm.skeletonvalidator.SkeletonValidator;
import java.util.Scanner;

public abstract class MainApp {
    public abstract int temp();
	public static void main(String[] args) {
		//Don't delete this code
		//Skeletonvalidaton starts
		new SkeletonValidator();
		//Skeletonvalidation ends

		//Write your code here..
                Scanner kb=new Scanner(System.in);
                StudentAdmissionService std=new StudentAdmissionService();
                try
                {
                    if(std.addStudentAdmissionDetails("inputFeed.txt"))
                    {
                        System.out.println("Data has been inserted into database");
                    }
                    else
                    {
                        System.out.println("Databse insertion failed");
                    }
                    System.out.println("Enter a admission id to search status");
                    String id=kb.nextLine();
                    if(std.searchStudentAdmission(id))
                    {
                        System.out.println("Student admission found");
                    }
                    else
                    {
                        System.out.println("Can't be found on database");
                    }
                }
                catch(StudentAdmissionException e)
                {
                    System.out.println(e.getMessage());
                }
		
		
	}

}
