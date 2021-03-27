package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.ProductController;
import member.model.vo.Product;
import member.model.vo.ProductIO;

public class ProductStockMenu {

	private Scanner sc = new Scanner(System.in);
	private ProductController productController = new ProductController();
	
	public void mainMenu() {
		do {
			String choice = displayMenuAndChoice();
			
			List<Product> list = null;
			Product product = null;
			int result = 0;
			String msg = null;
			String productId = null;
			String productName = null;
			
			switch(choice) {
			case "1": 
				list = productController.selectAll();
				displayProductList(list);
				break;
			case "2": 
				productId = inputProductId();
				product = productController.selectOne(productId);
				displayProduct(product);
				break;
			case "3": 
				productName = inputProductName();
				list = productController.selectByName(productName);
				displayProductList(list);
				break;
			case "4": 				
				product = inputProduct();
				System.out.println(">>> 신규상품정보 확인 : " + product);
				result = productController.insertProduct(product);
				msg = result > 0 ? "상품 추가 성공!" : "상품 추가 실패!";
				displayMsg(msg);
				break;
			case "5": 
				updateProductMenu();
				break;
			case "6": 
				productId = inputProductId();
				result = productController.deleteProduct(productId);
				msg = result > 0 ? "상품 삭제 성공!" : "상품 삭제 실패!";
				displayMsg(msg);
				break;
			case "7":
				ProductIOMenu();
				break;
			case "9" : 
				System.out.print("정말 끝내시겠습니까?(y/n) : ");
				if(sc.next().charAt(0) == 'y')
					return;
				break;
			default : 
				System.out.println("잘못 입력하셨습니다.");
			}
		} while (true);
		
	}
	
	
	
	
	
	
	/**
	 * 상품 입출고 메뉴
	 */
	private void ProductIOMenu() {
		String menu = "****** 상품 입출고 메뉴******\r\n" + 
				  "1. 전체입출고내역조회\r\n" + 
				  "2. 상품입고\r\n" + 
				  "3. 상품출고\r\n" + 
				  "9. 메인메뉴 돌아가기\r\n" + 
				  "입력 : ";
		
		String productId = inputProductId();
		
		int choice = 0;
		while(true){

			Product product = productController.selectOne(productId);
			if(product == null) {
				System.out.println("해당 상품이 존재하지 않습니다.");
				return;
			}
			
			//displayProduct(product);
			System.out.print(menu);
			choice = sc.nextInt();
			
			List<ProductIO> list = null;
			String msg = null;
			int result = 0;
			ProductIO productIO = null;
			
			switch (choice) {
			case 1:
				list = productController.selectIOAll();
				displayProductIOList(list);
				break;
			case 2:
				productIO = inputProdutIO();
				result = productController.ToReceiveStock(productIO);
				msg = result > 0 ? "상품 입고 성공!" : "상품 입고 실패!";
				displayMsg(msg);
				break;
			case 3:
				productIO = inputProdutIO();
				result = productController.ToReleaseStock(productIO);
				msg = result > 0 ? "상품 입고 성공!" : "상품 출고 실패!";
				displayMsg(msg);
				break;
			case 9: return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}
			
//			int result = productController.updateProduct(product);
//			displayMsg(result > 0 ? "정보 수정 성공!" : "정보 수정 실패!");
		}
		
	}

	
	/**
	 * 전체입출고내역조회
	 */
	private void displayProductIOList(List<ProductIO> list) {
		if(list != null && !list.isEmpty()) {
			System.out.println("==========================================================================================");
			for(int i = 0; i < list.size(); i++)
				System.out.println((i + 1) + " : " + list.get(i));
			System.out.println("==========================================================================================");
		}
		else {
			System.out.println(">>> 조회된 입출내역이 없습니다.");
		}
		
	}


	/**
	 * 상품 정보 추가
	 */
	private Product inputProduct() {
		System.out.println("새로운 상품정보를 입력하세요.");
		Product product = new Product();
		System.out.print("상품 아이디 : ");
		product.setProductId(sc.next());
		System.out.print("상품 이름 : ");
		product.setProductName(sc.next());
		System.out.print("가격 : ");
		product.setPrice(sc.nextInt());
		sc.nextLine();
		System.out.print("설명 : ");
		product.setDescription(sc.nextLine());
		
		return product;
	}
	
	/**
	 * 상품 입출고
	 */
	
	private ProductIO inputProdutIO() {
		ProductIO productIO = new ProductIO();
		System.out.print("상품아이디 : ");
		productIO.setProductId(sc.next());
		System.out.print("양 : ");
		productIO.setAmount(sc.nextInt());
		System.out.print("입/출고 확인 (I/O) : ");
		productIO.setStatus(sc.next());
		return productIO;
	}
	
	
	
	/**
	 * 상품 재고 관리 프로그램 메뉴
	 */
	private String displayMenuAndChoice() {
		String menu = "========== 상품재고관리 프로그램 ==========\n"
				+ "1.전체상품조회\n"
				+ "2.상품아이디검색\n" 
				+ "3.상품명검색\n" 
				+ "4.상품추가\n" 
				+ "5.상품정보변경\n" 
				+ "6.상품삭제\n" 
				+ "7.상품 입/출고 메뉴\n" 
				+ "9.프로그램 종료\n"
				+ "----------------------------------\n"
				+ "선택 : "; 
		System.out.print(menu);
		return sc.next();
	}

	
	/**
	 * 상품 정보 변경 메뉴
	 */
	private void updateProductMenu() {
		String menu = "****** 상품 정보 변경 메뉴******\r\n" + 
					  "1. 상품명변경\r\n" + 
					  "2. 가격변경\r\n" + 
					  "3. 설명변경\r\n" + 
					  "9. 메인메뉴 돌아가기\r\n" + 
					  "입력 : ";
		
		String productId = inputProductId();
		
		int choice = 0;
		while(true){

			Product product = productController.selectOne(productId);
			//조회된 회원정보가 없는 경우, 리턴
			if(product == null) {
				System.out.println("해당 상품이 존재하지 않습니다.");
				return;
			}
			
			displayProduct(product);
			
			System.out.print(menu);
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				System.out.print("변경할 상품명 : ");
				product.setProductName(sc.next());
				break;
			case 2:
				System.out.print("변경할 가격 : ");
				product.setPrice(sc.nextInt());
				break;
			case 3:
				System.out.print("변경할 설명 : ");
				product.setDescription(sc.nextLine());
				break;
			case 9: return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}
			
			int result = productController.updateProduct(product);
			displayMsg(result > 0 ? "정보 수정 성공!" : "정보 수정 실패!");
		}

	}


	/**
	 * n행의 상품정보 출력
	 * @param list
	 */
	private void displayProductList(List<Product> list) {
		if(list != null && !list.isEmpty()) {
			System.out.println("==========================================================================================");
			for(int i = 0; i < list.size(); i++)
				System.out.println((i + 1) + " : " + list.get(i));
			System.out.println("==========================================================================================");
		}
		else {
			System.out.println(">>> 조회된 상품 정보가 없습니다.");
		}
	}


	/**
	 * 상품 이름으로 조회
	 */
	private String inputProductName() {
		System.out.print("조회할 이름 입력 : ");
		return sc.next();
	}

	
	/**
	 * DB에서 조회한 1개의 상품정보 출력
	 */
	private void displayProduct(Product product) {
		if(product == null)
			System.out.println(">>>> 조회된 상품이 없습니다.");
		else {
			System.out.println("****************************************************************");
			System.out.println(product);
			System.out.println("****************************************************************");
		}
	}

	
	/**
	 * 조회할 상품아이디 입력
	 */
	private String inputProductId() {
		System.out.print("아이디 입력 : ");
		return sc.next();
	}


	/**
	 * DML처리결과 통보용 
	 * @param msg
	 */
	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}


	/**
	 * 사용자에게 오류메세지 출력하기
	 * @param errorMsg
	 */
	public void displayError(String errorMsg) {
		System.err.println(errorMsg);
	}

}







