package member.controller;

import java.util.List;

import member.model.exception.ProductException;
import member.model.vo.Member;
import member.model.vo.Product;
import member.model.vo.ProductIO;
import member.model.service.ProductService;
import member.view.ProductStockMenu;

public class ProductController {

	private ProductService productService = new ProductService();
	

	public List<Product> selectAll() {
		List<Product> list = null;
		try {
			list = productService.selectAll();
		} catch(ProductException e) {
			new ProductStockMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list;
	}
	

	public Product selectOne(String productId) {
		Product product = null; 
		try {
			product = productService.selectOne(productId);
		} catch (ProductException e) {
			e.printStackTrace();
			new ProductStockMenu().displayError(e.getMessage());
		}
		return product;
	}
	
	public int deleteProduct(String productId) {
		int result = 0;
		try {
			result = productService.deleteProduct(productId);
		} catch (ProductException e) {
			e.printStackTrace();
			new ProductStockMenu().displayError(e.getMessage());
		}
		
		return result;
	}
	
	public int updateProduct(Product product) {
		int result = 0;
		try {
			result = productService.updateProduct(product);
		} catch (ProductException e) {
			e.printStackTrace();
			new ProductStockMenu().displayError(e.getMessage());
		}
		
		return result;
	}
	
	public List<Product> selectByName(String productName) {
		List<Product> list = null;
		try {
			list = productService.selectByName(productName);
		} catch (ProductException e) {
			e.printStackTrace();
			new ProductStockMenu().displayError(e.getMessage());
		}
		return list;
	}


	public int insertProduct(Product product) {
		int result = 0;
		try {
			result = productService.insertProduct(product);
		} catch (ProductException e) {
			e.printStackTrace();
			new ProductStockMenu().displayError(e.getMessage());
		}
		
		return result;
	}


	/**
	 * 전체 상품 입출고 내역 조회
	 */
	public List<ProductIO> selectIOAll() {
		List<ProductIO> list = productService.selectIOAll();
		return list;
	}


	/**
	 * 상품 입고
	 */
	public int ToReceiveStock(ProductIO p) {
		int result = 0;
		result = productService.ToReceiveStock(p);
		return result;
	}

	/**
	 * 상품 출고
	 */
	public int ToReleaseStock(ProductIO p) {
		int result = 0;
		result = productService.ToReleaseStock(p);
		return result;
	}
	
}
