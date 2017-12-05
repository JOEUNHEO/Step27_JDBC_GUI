package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import test.dto.MemberDto;
import test.util.DBConnect;

/*
 * 	Dao => Data Access Object �� ����
 * 
 * 	- ȸ�� ������ ���ؼ� SELECT, INSERT, UPDATE, DELETE
 * 	  �۾��� ���� �� ��ü�� �����ϱ� ���� Ŭ���� �����ϱ�
 * 
 * 	- Application �������� MemberDao ��ü�� ���� 1����
 * 	  ������ �� �ֵ��� �����Ѵ�. (Connection ��ü ������ �����Ǿ� �ֱ� ������, Dao��ü�� 1���� ����ϵ��� �����ؾ���)
 */
public class MemberDao {
	//1. �ڽ��� �������� ���� private static �ʵ� �����
	private static MemberDao dao;
	//2. �ܺο��� ��ü ������ �� ������ �������� ���������ڸ�
	//   private �� ����
	private MemberDao() {}
	//3. �ڽ��� �������� �������ִ� static ��� �޼ҵ� �����
	public static MemberDao getInstance() {
		if(dao == null) {
			dao = new MemberDao();
		}
		return dao;
	}
	
	// DB �� ȸ�������� �����ϴ� �޼ҵ�
	public boolean insert(MemberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		//�۾��� �������θ� ���� ����
		boolean isSuccess = false;
		
		try {
			conn = new DBConnect().getConn();
			//������ sql��
			String sql = "INSERT INTO member (num,name,addr)" + "VALUES(member_seq.NEXTVAL,?,?)";
			pstmt = conn.prepareStatement(sql);
			// ? �� ���ε��ϱ�
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getAddr());
			// sql �� �����ϰ� �߰��� row �� ���� ������
			int flag = pstmt.executeUpdate();
			if(flag > 0) {//�۾� �����̸�
				isSuccess = true;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//���� ���θ� ����
		return isSuccess;
	}
	// DB �� ȸ�������� �����ϴ� �޼ҵ�
	public boolean delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		//�۾��� �������θ� ���� ����
		boolean isSuccess = false;
		
		try {
			conn = new DBConnect().getConn();
			//������ sql��
			String sql = "DELETE FROM member WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			// ? �� ���ε��ϱ�
			pstmt.setInt(1, num);
			// sql �� �����ϰ� �߰��� row �� ���� ������
			int flag = pstmt.executeUpdate();
			if(flag > 0) {//�۾� �����̸�
				isSuccess = true;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//���� ���θ� ����
		return isSuccess;		
	}
	// DB �� ȸ�������� �����ϴ� �޼ҵ�
	public boolean update(MemberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		//�۾��� �������θ� ���� ����
		boolean isSuccess = false;
		
		try {
			conn = new DBConnect().getConn();
			//������ sql��
			String sql = "UPDATE member SET name=?, addr=?" + "WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			// ? �� ���ε��ϱ�
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getAddr());
			pstmt.setInt(3, dto.getNum());
			// sql �� �����ϰ� �߰��� row �� ���� ������
			int flag = pstmt.executeUpdate();
			if(flag > 0) {//�۾� �����̸�
				isSuccess = true;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//���� ���θ� ����
		return isSuccess;		
	}
	// DB �� ȸ�� ����� ������ �ִ� �޼ҵ�
	public List<MemberDto> getList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//MemberDto ��ü�� ���� Vector ��ü����
		List<MemberDto> members = new ArrayList<>();
				
		try {
			conn = new DBConnect().getConn();
			//������ sql��
			String sql = "SELECT * FROM member ORDER BY num ASC";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String addr = rs.getString("addr");
				
				MemberDto dto = new MemberDto(num,name,addr);
				members.add(dto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return members;
	}
	
	//���ڷ� ���޵Ǵ� ��ȣ�� �ش��ϴ� ȸ�������� ���� �� �ִ� �޼ҵ�
	public MemberDto getData(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//MemberDto ��ü�� �������� ���� ���� �����
		MemberDto dto = null;
		
		try {
			conn = new DBConnect().getConn();
			//������ sql �� �غ�
			String sql = "SELECT * FROM member WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			//ResultSet ��ü�� ������ ������
			//(SELECT ���� ���� ��� ���� ������ �ִ� ��ü)
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//Ŀ���� ��ĭ ������
				//Ŀ���� ��ġ�� ������ ȸ�� ������ ���´�.
				String name = rs.getString("name");
				String addr = rs.getString("addr");
				//MemberDto ��ü�� ��´�.
				dto = new MemberDto(num, name, addr);				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
				if(rs != null)rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return dto;
	}
}
