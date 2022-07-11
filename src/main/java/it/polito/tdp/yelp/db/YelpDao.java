package it.polito.tdp.yelp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.yelp.model.Adiacenza;
import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.User;

public class YelpDao {
	
	
	public void getAllBusiness(Map<String,Business>idMap){
		String sql = "SELECT * FROM Business order by business_name ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
if(!idMap.containsKey(res.getString("business_id"))){
				Business business = new Business(res.getString("business_id"), 
						res.getString("full_address"),
						res.getString("active"),
						res.getString("categories"),
						res.getString("city"),
						res.getInt("review_count"),
						res.getString("business_name"),
						res.getString("neighborhoods"),
						res.getDouble("latitude"),
						res.getDouble("longitude"),
						res.getString("state"),
						res.getDouble("stars"));
				
				idMap.put(business.getBusinessId(), business);
			}
}
			res.close();
			st.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public List<Review> getAllReviews(){
		String sql = "SELECT * FROM Reviews";
		List<Review> result = new ArrayList<Review>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Review review = new Review(res.getString("review_id"), 
						res.getString("business_id"),
						res.getString("user_id"),
						res.getDouble("stars"),
						res.getDate("review_date").toLocalDate(),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("review_text"));
				result.add(review);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getAllUsers(){
		String sql = "SELECT * FROM Users";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<String>citta(){
		String sql = "select DISTINCT city as c "
				+ "from Business  "
				+ "order by city ";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(res.getString("c"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Business>getVertici(String citta,Map<String,Business>idMap){
		String sql = "select DISTINCT b.`business_id` as id,b.business_name as n "
				+ "from Business b "
				+ "where b.city=? "
				+ "order by n ";
		List<Business> result = new ArrayList<Business>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, citta);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(idMap.get(res.getString("id")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<Adiacenza>getArchi(String citta,Map<String,Business>idMap){
		String sql = "select DISTINCT b.`business_id`as id1,b2.business_id as id2,b.`longitude`as b1lo,b.`latitude`as b1la,b2.`longitude`as b2lo,b2.`latitude`as b2la "
				+ "from Business b,Business b2 "
				+ "where b.city=? and b2.city=b.city and b.`business_id`>b2.`business_id` ";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		LatLng pnt1,pnt2;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, citta);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				pnt1=new LatLng(res.getDouble("b1la"),res.getDouble("b1lo"));
				pnt2=new LatLng(res.getDouble("b2la"),res.getDouble("b2lo"));
				double peso=LatLngTool.distance(pnt1, pnt2, LengthUnit.KILOMETER);
				result.add(new Adiacenza(idMap.get(res.getString("id1")),idMap.get(res.getString("id2")),peso));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
