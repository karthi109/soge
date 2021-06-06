package com.cts.unoadm.dao;

import java.util.ArrayList;
import java.util.List;

import com.cts.unoadm.exception.StudentAdmissionException;
import com.cts.unoadm.util.ApplicationUtil;
import com.cts.unoadm.util.DBConnectionManager;
import com.cts.unoadm.vo.StudentAdmission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAdmissionDAO {
	
	public boolean addStudentAdmissionDetails(List<StudentAdmission> stdAdmissions) throws StudentAdmissionException {
		boolean recordsAdded = false;
		Connection con=null;
		PreparedStatement ps = null;
		//code here
                try
                {
                    con=DBConnectionManager.getInstance().getConnection();
                    for(StudentAdmission obj:stdAdmissions)
                    {
                        ps=con.prepareStatement("Insert into uno_admission values(?,?,?,?,?,?,?,?,?,?,?,?,?);");
                        ps.setString(1, obj.getAdmissionId());
                        ps.setString(2, obj.getStudentCode());
                        ps.setDate(3, ApplicationUtil.convertUtilToSqlDate(obj.getDateOfCounseling()));
                        ps.setString(4, obj.getDepartmentName());
                        ps.setDate(5, ApplicationUtil.convertUtilToSqlDate(obj.getDateOfAdmission()));
                        ps.setString(6, obj.getPreferCollegeHostel());
                        ps.setString(7, obj.getFirstGraduate());
                        ps.setString(8, obj.getManagerApproval());
                        ps.setFloat(9, (float)obj.getAdmissionFee());
                        ps.setFloat(10, (float)obj.getTuitionFee());
                        ps.setDouble(11, obj.getHostelFee());
                        ps.setFloat(12, (float)obj.getTotalCollegeFee());
                        ps.setString(13, obj.getFinalStatusOfAdmission());
                        ps.execute();
                    }
                    recordsAdded=true;
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                    // throw new StudentAdmissionException(e.getMessage(),e.getCause());
                }
                finally
                {
                    try{
                    ps.close();
                    con.close();
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                }
                    return recordsAdded;
	}

	public List<StudentAdmission> getAllStudentAdmissionDetails() throws StudentAdmissionException {
		
		List<StudentAdmission> stdAdmissions = new ArrayList<StudentAdmission>();
        Connection con=null;
        PreparedStatement ps= null;
        ResultSet rs=null;
		//code here
                try
                {
                    con=DBConnectionManager.getInstance().getConnection();
                    ps=con.prepareStatement("Select * from uno_admission");
                    rs=ps.executeQuery();
                    while(rs.next())
                    {
                        StudentAdmission obj=new StudentAdmission(rs.getString(1),rs.getString(2),ApplicationUtil.convertStringToDate(rs.getString(3)),
                                rs.getString(4),ApplicationUtil.convertStringToDate(rs.getString(5)),rs.getString(6),rs.getString(7),rs.getString(8),
                                rs.getDouble(9),rs.getDouble(10),rs.getDouble(11),rs.getDouble(12),rs.getString(13));
                        stdAdmissions.add(obj);
                    }
                    rs.close();
                }
                catch(SQLException e)
                {
                    // throw new StudentAdmissionException(e.getMessage(),e.getCause());
                }
		finally
                {
                    try{
                    rs.close();
                    ps.close();
                    con.close();
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                    return stdAdmissions;

	}
}
