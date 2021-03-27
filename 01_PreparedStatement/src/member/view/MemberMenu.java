package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {

	private MemberController memberController = new MemberController();
	private Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		
		String menu = "=================회원 관리 프로그램=================\n"
					+ "1. 회원 전체조회\n"
					+ "2. 회원 아이디 조회\n"
					+ "3. 회원 이름 조회\n"
					+ "4. 회원 가입\n"
					+ "5. 회원 정보변경\n"
					+ "6. 회원 탈퇴\n"
					+ "0. 프로그램 끝내기\n"
					+ "---------------------------------------------\n"
					+ "선택 : ";
					
		
		while(true) {
			System.out.print(menu);
			int choice = sc.nextInt();
			Member member = null;
			int result = 0;
			String msg = null;
			List<Member> list = null;
			String memberId = null;
			String memberName = null;
			
			switch(choice) {
			case 1 : 
				list = memberController.selectAll();       
				displayMemberList(list);
				break;
			case 2 : 
				memberId = inputMemberId();
				member = memberController.selectOne(memberId);
				displayMember(member);
				break;
			case 3 : 
				memberName = inputMemberName();
				member = memberController.selectTwo(memberName);
				displayMember(member);
				break;
			case 4 :
				//1. 신규회원정보 입력 -> member객체
				member = inputMember();
				System.out.println("신규회원 확인 : " + member);
				//2. controller에 회원가입 요청(메소드호출) -> int리턴(처리된행의개수)
				result = memberController.insertMember(member);
				//3.int에 따른 분기처리
				msg = result > 0 ? "회원가입성공" : "회원가입실패";
				displayMsg(msg);
				break;
			case 5 : 
				System.out.print("변경할 회원 아이디 입력 : ");
				String updateMemberId = sc.next();
				System.out.println("기존 회원 정보 확인 : " + memberController.selectOne(updateMemberId));
				//정보변경할 회원입력
				member = updateMember(updateMemberId);
				result = memberController.updateMember(member);
				msg = result > 0 ? "회원정보변경 성공" : "회원정보변경 실패";
				displayMsg(msg);
				System.out.println("변경된 회원 정보 확인 : " + memberController.selectOne(updateMemberId));
				break;
			case 6 : 
				System.out.print("정말로 탈퇴하시겠습니까?(y/n) : ");
				if(sc.next().charAt(0) == 'y') {
					System.out.print("탈퇴할 회원 아이디 입력 : ");
					String deleteMemberId = sc.next();
					result = memberController.deleteMember(deleteMemberId);
					msg = result > 0 ? "회원탈퇴 성공" : "회원탈퇴 실패";
					displayMsg(msg);
				}	
				break;
			case 0 : 
				System.out.print("정말로 끝내시겠습니까?(y/n) : ");
				if(sc.next().charAt(0) == 'y')
					return;
				break;
					
			default : 
				System.out.println("잘못입력하셨습니다");
			
			}
			
		}
	}

	
	
	private Member updateMember(String memberId) {
		
		Member member = new Member();
		member.setMemberId(memberId);
		System.out.println("변경할 회원의 정보를 입력해주세요");
		//암호, 이메일, 전화번호, 주소, 취미
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		System.out.print("이메일 : ");
		member.setEmail(sc.next());
		System.out.print("전화번호 : ");
		member.setPhone(sc.next());
		sc.nextLine(); //개행문자날리기용
		System.out.print("주소 : ");
		member.setAddress(sc.nextLine());
		System.out.print("취미(,로 나열할것) : ");
		member.setHobby(sc.nextLine());
		return member;
	}


	//조회할 회원이름 입력
	private String inputMemberName() {
		System.out.print("조회할 이름 입력 : ");
		return sc.next();
	}

	//DB에서 조회한 1명의 회원출력
	private void displayMember(Member member) {
		if(member == null)
			System.out.println(">>>>> 조회된 회원이 없습니다");
		else {
			System.out.println("*****************************************");
			System.out.println(member);
			System.out.println("*****************************************");
		}
			
	}

	//조회할 회원아이디 입력
	private String inputMemberId() {
		System.out.print("조회할 아이디 입력 : ");
		return sc.next();
	}

	//DB에서 조회된 회원객체 n개를 출력
	private void displayMemberList(List<Member> list) {
		if(list == null || list.isEmpty()) {
			System.out.println(">>>> 조회된 행이 없습니다");
		}else {
			System.out.println("**************************************************");
			for(Member m : list) {
				System.out.println(m);
			}
			System.out.println("**************************************************");
		}
	}

	//DML처리결과 통보용
	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}

	//신규회원정보입력
	private Member inputMember() {
		System.out.println("새로운 회원정보를 입력하세요");
		Member member = new Member();
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		System.out.print("이름 : ");
		member.setMemberName(sc.next());
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		System.out.print("나이 : ");
		member.setAge(sc.nextInt());
		System.out.print("성별(M/F) : ");
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		System.out.print("이메일 : ");
		member.setEmail(sc.next());
		System.out.print("전화번호 : ");
		member.setPhone(sc.next());
		sc.nextLine(); //개행문자날리기용
		System.out.print("주소 : ");
		member.setAddress(sc.nextLine());
		System.out.print("취미(,로 나열할것) : ");
		member.setHobby(sc.nextLine());
		
		
		
		
		
		
		return member;
	}

}
