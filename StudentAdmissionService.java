package com.cts.unoadm.service;

import com.cts.unoadm.dao.StudentAdmissionDAO;
import java.util.ArrayList;
import java.util.List;

import com.cts.unoadm.exception.StudentAdmissionException;
import com.cts.unoadm.util.ApplicationUtil;
import com.cts.unoadm.vo.StudentAdmission;

public class StudentAdmissionService {
	
	/**
	 * @return List<StudentAdmission>
	 */
	public static List<StudentAdmission> buildStudentAdmissionsList(List<String> studentAdmissionRecords) {
		List<StudentAdmission> studentAdmissionList = new ArrayList<StudentAdmission>();
		
		//Code here
                for(String line:studentAdmissionRecords)
                {
                    String[] words=line.split(",");
                    double[] fees=calculateTotalCollegeFee(words[5].trim(),words[6].trim(),words[3].trim());
                    StudentAdmission obj=new StudentAdmission(words[0].trim(),words[1].trim(),
                            ApplicationUtil.convertStringToDate(words[2].trim()),words[3].trim(),
                            ApplicationUtil.convertStringToDate(words[4].trim()),words[5].trim(),words[6].trim(),
                            words[7].trim(),fees[0],fees[1],fees[2],fees[3],"AdmissionSuccessful");
                    studentAdmissionList.add(obj);
                }
		
		return studentAdmissionList;
	}


	public boolean addStudentAdmissionDetails(String inputFeed) throws StudentAdmissionException {
		
		//Code here
		try{
                List<String> rec=ApplicationUtil.readFile(inputFeed);
		    
                List<StudentAdmission> stdRec=StudentAdmissionService.buildStudentAdmissionsList(rec);
		StudentAdmissionDAO s=new StudentAdmissionDAO();
		
		return s.addStudentAdmissionDetails(stdRec);
		}
		catch(Exception e){
		    e.printStackTrace();
		}
		return false;
	}

	public static double[] calculateTotalCollegeFee(String preferCollegeHostel, String firstGraduate, String departmentName) {
		double[] studentAdmissionCosts = new double[4];

		//Code here..
                studentAdmissionCosts[0]=30000d;
                studentAdmissionCosts[1]=0d;
                studentAdmissionCosts[2]=0d;
                studentAdmissionCosts[3]=0d;
                
                if("EEE".equalsIgnoreCase(departmentName) || "CSE".equalsIgnoreCase(departmentName) || "IT".equalsIgnoreCase(departmentName))
                   { studentAdmissionCosts[1]=45000d;}
                    
                else if("ECE".equalsIgnoreCase(departmentName) || "CIVIL".equalsIgnoreCase(departmentName))
                    {studentAdmissionCosts[1]=50000d;}
                else if("MECH".equalsIgnoreCase(departmentName))
                    {studentAdmissionCosts[1]=55000d;}
                if("Yes".equalsIgnoreCase(preferCollegeHostel))
                    {studentAdmissionCosts[2]=75000d;}
                studentAdmissionCosts[3]=studentAdmissionCosts[0]+studentAdmissionCosts[1]+studentAdmissionCosts[2];
                
                if("Yes".equalsIgnoreCase(firstGraduate))
                    {studentAdmissionCosts[3]-=20000d;}
		return studentAdmissionCosts;
	}

	public boolean searchStudentAdmission(String admissionId) throws StudentAdmissionException {
		boolean status = false;
		//Code here..
		try{
                List<StudentAdmission> fetch=new StudentAdmissionDAO().getAllStudentAdmissionDetails();
        	
                
                for(StudentAdmission s:fetch)
                {
                    
                    
                    if(s.getAdmissionId().equalsIgnoreCase(admissionId))
                    {
                        status=true;
                        System.out.println(s.toString());
                    }
                }
		
		return status;
		}
		catch(Exception e){
		    e.printStackTrace();
		}
		return status;
	}
}
