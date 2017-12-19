package citationsearch.mapper;

import static citationsearch.constants.Constants.*;

import citationsearch.entity.Entity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mapper {
	protected Connection con = null;
	protected Statement stmt = null;
	protected String query = "";
	protected ResultSet rs;
	
	public Mapper() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.con = DriverManager.getConnection(SERVER_ADDR);
			this.stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read
	 * 
	 * @param id get an entity by id
	 * 
	 * @return Entity
	 */
	public Entity get(int id) {
		return new Entity();
	}
	
	/**
	 * Create
	 * 
	 * @param entity
	 * 
	 * @return int new entity id
	 */
	public int create(Entity entity) {
		return 0;
	}
	
	/**
	 * Delete
	 * 
	 * @param id
	 * 
	 * @return void
	 */
	public void delete(int id) {
		//
	}
	
	protected ResultSet executeGetQuery() {
		//System.out.println("Executing get query: " + this.query);
		//ResultSet rs = null;
		try {
			this.rs = this.stmt.executeQuery(this.query);
		} catch (SQLException e) {
			System.out.println("Error with query: " + this.query);
			e.printStackTrace();
		}
		
		return this.rs;
	}
	
	protected ResultSet executeGetQuery2() {
		//System.out.println("Executing get query: " + this.query);
		ResultSet rs = null;
		try {
			this.stmt = this.con.createStatement();
			rs = this.stmt.executeQuery(this.query);
		} catch (SQLException e) {
			System.out.println("Error with query: " + this.query);
			e.printStackTrace();
		}
		
		return rs;
	}
	
	protected int executeOtherQuery() {
		//System.out.println("Executing other query: " + this.query);
		int result = 0;
		try {
			result = this.stmt.executeUpdate(this.query);
		} catch (Exception e) {
			System.out.println("Error with query: " + this.query);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void close() {
		try {
			this.stmt.close();
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshStatement() {
		try {
			this.rs.close();
			this.stmt.close();
			this.stmt = this.con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void reConnectDB() {
		this.close();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.con = DriverManager.getConnection(SERVER_ADDR);
			this.stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
