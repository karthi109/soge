package com.cts.unoadm.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cts.unoadm.exception.StudentAdmissionException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.*;

public abstract class ApplicationUtil {

	/**
	 * @param fileName
	 * @return List<String>
	 * @throws StudentAdmissionException
	 */
	 public abstract int temp();
	public static List<String> readFile(String fileName) throws StudentAdmissionException {
		List<String> studentAdmissionList = new ArrayList<String>();
		  //Code here..
                  try(Stream<String> lines=Files.lines(Paths.get(fileName)))
                  {
                      lines.forEach((line)->{
                      String words[]=line.split(",");
                      if(ApplicationUtil.checkIfValidAdmission(ApplicationUtil.convertStringToDate(words[2].trim()), ApplicationUtil.convertStringToDate(words[4].trim()), words[7].trim()))
                      {
                          studentAdmissionList.add(line.trim());
                      }
                  });
                  }
                  catch(IOException e)
                  {
                    //   throw new StudentAdmissionException(e.getMessage(),e.getCause());
                  }
		
		return studentAdmissionList;
	}

	/**
	 * @param util
	 *            Date
	 * @return sql Date
	 */
	public static java.sql.Date convertUtilToSqlDate(java.util.Date uDate) {
		
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		
		//Code here..
                
		return sDate;
	}

	/**
	 * @param inDate
	 * @return Date
	 */
	public static Date convertStringToDate(String inDate) {
		
		//Code here..
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                Date date=null;
                try
                {
                    date=sdf.parse(inDate);
                }
                catch(ParseException e)
                {
                    e.printStackTrace();
                }
		
		return date;//TODO change this return value
	}

	public static boolean checkIfValidAdmission(Date dtOfCounseling, Date dtOfAdmission, String manager) {
		boolean admissionValidity = false;
		
		//Code here..
                long cou=dtOfCounseling.getTime();
		long adm=dtOfAdmission.getTime();
                long days=TimeUnit.DAYS.convert(Math.abs(adm-cou), TimeUnit.MILLISECONDS);
                if(days<=10 && "Approved".equalsIgnoreCase(manager))
                    {admissionValidity=true;}
		return admissionValidity;
	}
}
