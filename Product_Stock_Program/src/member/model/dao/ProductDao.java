package member.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import member.model.exception.ProductException;
import member.model.vo.Product;
import member.model.vo.ProductIO;

public class ProductDao {
	
	private Properties prop = new Properties();
	private Properties prop2 = new Properties();
	
	public ProductDao() {
		String fileName = "resources/product-query.properties";
		String fileName2 = "resources/productIO-query.properties";
		try {
			prop.load(new FileReader(fileName));
			prop2.load(new FileReader(fileName2));
//			System.out.println(prop);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public List<Product> selectAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");
		List<Product> list = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			list = new ArrayList<>();
			
			while(rset.next()) {
				String productId = rset.getString("product_id");
				String productName = rset.getString("product_name");
				int price = rset.getInt("price");
				String description = rset.getString("description");
				int stock = rset.getInt("stock");
				Product product = new Product(productId, productName, price, description, stock);
				list.add(product);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	public Product selectOne(Connection conn, String productId) {
		Product product = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rset = pstmt.executeQuery();
			
			if(rset.next()){
				product = new Product();
				product.setProductId(rset.getString("product_id"));
				product.setProductName(rset.getString("product_name"));
				product.setPrice(rset.getInt("price"));
				product.setDescription(rset.getString("description"));
				product.setStock(rset.getInt("stock"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return product;
	}
	
	public List<Product> selectByName(Connection conn, String productName) {
		List<Product> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectByName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+productName+"%");
			rset = pstmt.executeQuery();		
			list = new ArrayList<Product>();
	
			while(rset.next()) {
				Product product = new Product();
				product.setProductId(rset.getString("product_id"));
				product.setProductName(rset.getString("product_name"));
				product.setPrice(rset.getInt("price"));
				product.setDescription(rset.getString("description"));
				product.setStock(rset.getInt("stock"));
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	public int insertProduct(Connection conn, Product product) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,product.getProductId());
			pstmt.setString(2,product.getProductName());
			pstmt.setInt(3,product.getPrice());
			pstmt.setString(4,product.getDescription());
			result = pstmt.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateProduct(Connection conn, Product product) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getProductName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setString(3, product.getDescription());			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	public int deleteProduct(Connection conn, String productId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	public List<ProductIO> selectIOAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop2.getProperty("selectIOAll");
		List<ProductIO> list = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			list = new ArrayList<>();
			
			while(rset.next()) {
				int ioNo = rset.getInt("io_no");
				String productId = rset.getString("product_id");
				Date ioDate = rset.getDate("iodate");
				int amount = rset.getInt("amount");
				String status = rset.getString("status");
				ProductIO productIO = new ProductIO(ioNo,productId,ioDate,amount,status);
				list.add(productIO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}


	public int ToReceiveStock(Connection conn, ProductIO p) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop2.getProperty("ToReceiveStock");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,p.getProductId());
			pstmt.setInt(2,p.getAmount());
			pstmt.setString(3,p.getStatus());
			result = pstmt.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	public int ToReleaseStock(Connection conn, ProductIO p) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop2.getProperty("ToReleaseStock");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,p.getProductId());
			pstmt.setInt(2,p.getAmount());
			pstmt.setString(3,p.getStatus());
			result = pstmt.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


}
