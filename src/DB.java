import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB {
	static Connection con;
	static Statement stmt;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC&allowLoadLocalInfile=true&allowPublicKeyRetrieval=true","root","1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void createT(String t, String c) {
		execute("create table " + t + "(" + c + ")");
		execute("load data local infile './datafile/" + t + ".txt' into table " + t + " ignore 1 lines");
	}
	
	
	
	public DB() {
		execute("drop database if exists 2021지방_1");
		execute("create database 2021지방_1");
		execute("drop user if exists user@localhost");
		execute("create user user@localhost identified by '1234'");
		execute("use 2021지방_1");
		execute("set global local_infile=true");
		execute("grant select, insert, delete, update on 2021지방_1.* to user@localhost");
		createT("user", "u_no int primary key not null auto_increment, u_id varchar(20), u_pw varchar(20), u_name varchar(15), u_phone varchar(20), u_age date, u_10percent int, u_30percent int");
		createT("category", "c_no int primary key not null auto_increment, c_name varchar(10)");
		createT("product", "p_no int primary key not null auto_increment, c_no int, p_name varchar(20), p_price int, p_stock int, p_explanation varchar(150), foreign key(c_no) references category(c_no)");
		createT("purchase","pu_no int primary key not null auto_increment, p_no int, pu_price int, pu_count int, coupon int, u_no int, pu_date date, foreign key(p_no) references product(p_no), foreign key(u_no) references user(u_no)");
		createT("attendance", "a_no int primary key not null auto_increment, u_no int, a_date date, foreign key(u_no) references user(u_no)");
		createT("coupon", "c_no int primary key not null auto_increment, u_no int, c_date varchar(15), c_10percent int, c_30percent int, foreign key(u_no) references user(u_no)");
		JOptionPane.showMessageDialog(null, "셋팅성공", "정보", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void main(String[] args) {
		new DB();
	}
}
