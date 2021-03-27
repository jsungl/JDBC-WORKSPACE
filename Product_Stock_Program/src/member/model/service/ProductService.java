package member.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import member.model.dao.ProductDao;
import member.model.vo.Product;
import member.model.vo.ProductIO;

public class ProductService {
	
	private ProductDao productDao = new ProductDao();
	
	
	public List<Product> selectAll(){
		Connection conn = getConnection();
		List<Product> list = productDao.selectAll(conn);
		close(conn);
		return list;
	}
	
	
	public Product selectOne(String productId) {
		Connection conn = getConnection();
		Product product = productDao.selectOne(conn,productId);
		close(conn);
		return product;
	}
	
	
	public List<Product> selectByName(String productName){
		Connection conn = getConnection();
		List<Product> list = productDao.selectByName(conn, productName);
		close(conn);
		return list;
	}
	
	
	public int insertProduct(Product product) {
		Connection conn = getConnection();
		int result = productDao.insertProduct(conn, product);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	
	public int updateProduct(Product product) {
		Connection conn = getConnection();
		int result = productDao.updateProduct(conn, product);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	

	public int deleteProduct(String productId) {
		Connection conn = getConnection();
		int result = productDao.deleteProduct(conn, productId);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}


	public List<ProductIO> selectIOAll() {
		Connection conn = getConnection();
		List<ProductIO> list = productDao.selectIOAll(conn);
		close(conn);
		return list;
	}


	public int ToReceiveStock(ProductIO p) {
		Connection conn = getConnection();
		int result = productDao.ToReceiveStock(conn, p);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}


	public int ToReleaseStock(ProductIO p) {
		Connection conn = getConnection();
		int result = productDao.ToReleaseStock(conn, p);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}



}
