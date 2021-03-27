package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
	public void mainMenu() {
		String menu = "=================회원 관리 프로그램=================\n"
					+ "1. 회원 전체조회\n"
					+ "2. 회원 아이디 조회\n"
					+ "3. 회원 이름 조회\n"
					+ "4. 회원 가입\n"
					+ "5. 회원 정보변경\n"
					+ "6. 회원 탈퇴\n"
					+ "0. 프로그램 끝내기\n"
					+ "==================================================\n"
					+ "선택 : ";
		
		String subMenu = "-----------------회원정보 변경 메뉴-----------------\n"
					   + "1. 암호 변경\n"
					   + "2. 이메일 변경\n"
					   + "3. 전화번호 변경\n"
					   + "4. 주소 변경\n"
					   + "9. 메인메뉴 돌아가기\n"
					   + "----------------------------------------------\n"
					   + "선택 : ";
		
		do {
			System.out.print(menu);
			String choice = sc.next(); //문자열로 받아서 InputMissMatchException이 발생하지 않게한다
			List<Member> list = null;
			Member member = null;
			int result = 0;
			String msg = null;
			String memberId = null;
			String memberName = null;
			
			switch(choice) {
			case "1" : 
				list = memberController.selectAll();
				displayMemberList(list);
				break;
			case "2" : 
				memberId = inputMemberId();
				member = memberController.selectOne(memberId);
				displayMember(member);
				break;
			case "3" : 
				memberName = inputMemberName();
				list = memberController.selectByName(memberName);
				displayMemberList(list);
				break;
			case "4" : 
				member = inputMember();
				System.out.println(">>> 신규회원 확인 : " + member);
				result = memberController.insertMember(member);
				msg = result > 0 ? "회원 가입 성공!" : "회원 가입 실패!";
				displayMsg(msg);
				break;
			case "5" : 
				memberId = inputMemberId();
				member = memberController.selectOne(memberId);
				displayMember(member);
				if(member == null)
					break;
				
				outer:
				while(true) {
					System.out.print(subMenu);
					String choice2 = sc.next();
					int result2 = 0;
					switch(choice2) {
					case "1" : 
						System.out.print("변경할 암호 : ");
						member.setPassword(sc.next());
						result2 = memberController.updateMember(member);
						msg = result2 > 0 ? "회원 수정 성공!" : "회원 수정 실패!";
						displayMsg(msg);
						if(result2 > 0)
							System.out.println(memberController.selectOne(memberId));
						break;
					case "2" : 
						System.out.print("변경할 이메일 : ");
						member.setEmail(sc.next());
						result2 = memberController.updateMember(member);
						msg = result2 > 0 ? "회원 수정 성공!" : "회원 수정 실패!";
						displayMsg(msg);
						if(result2 > 0)
							System.out.println(memberController.selectOne(memberId));
						break;
					case "3" : 
						System.out.print("변경할 전화번호 : ");
						member.setPhone(sc.next());
						result2 = memberController.updateMember(member);
						msg = result2 > 0 ? "회원 수정 성공!" : "회원 수정 실패!";
						displayMsg(msg);
						if(result2 > 0)
							System.out.println(memberController.selectOne(memberId));
						break;
					case "4" : 
						sc.nextLine();
						System.out.print("변경할 주소 : ");
						member.setAddress(sc.nextLine());
						result2 = memberController.updateMember(member);
						msg = result2 > 0 ? "회원 수정 성공!" : "회원 수정 실패!";
						displayMsg(msg);
						if(result2 > 0)
							System.out.println(memberController.selectOne(memberId));
						break;
					case "9" : 
						break outer;
					
					}
				}
				
				break;
				
			case "6" : 
				memberId = inputMemberId();
				result = memberController.deleteMember(memberId);
				msg = result > 0 ? "회원 탈퇴 성공!" : "회원 탈퇴 실패!";
				displayMsg(msg);
				break;
			case "0" : 
				System.out.print("정말 끝내시겠습니까?");
				if(sc.next().charAt(0) == 'y')
					return;
				break;
			default :
				System.out.println("잘못입력하셨습니다");
			
			}
			
			
		} while(true);
	}

	
	//조회할 회원이름 입력
	private String inputMemberName() {
		System.out.print("조회할 이름 입력 : ");
		return sc.next();
	}


	//DB에서 조회한 1명의 회원 출력
	private void displayMember(Member member) {
		if(member == null) {
			System.out.println(">>>> 조회된 회원이 없습니다.");
		}else {
			System.out.println("================================================================");
			System.out.println(member);
			System.out.println("================================================================");
		}
	}


	//조회할 회원아이디 입력
	private String inputMemberId() {
		System.out.print("아이디 입력 : ");
		return sc.next();
	}


	//신규회원정보입력
	private Member inputMember() {
		System.out.println("새로운 회원정보를 입력하세요.");
		Member member = new Member();
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		System.out.print("이름 : ");
		member.setMemberName(sc.next());
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		System.out.print("나이 : ");
		member.setAge(sc.nextInt());
		System.out.print("성별(M/F) : ");//m, f
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		System.out.print("이메일: ");
		member.setEmail(sc.next());
		System.out.print("전화번호(-빼고 입력) : ");
		member.setPhone(sc.next());
		sc.nextLine();//버퍼에 남은 개행문자 날리기용 (next계열 - nextLine)
		System.out.print("주소 : ");
		member.setAddress(sc.nextLine());
		System.out.print("취미(,로 나열할것) : ");
		member.setHobby(sc.nextLine());
		return member;
	}



	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}

	//n행의 회원정보 출력
	private void displayMemberList(List<Member> list) {
		
		if(list != null && !list.isEmpty()) {
			System.out.println("====================================================================================");
			for(int i = 0; i < list.size(); i++) {
				System.out.println((i+1) + " : " + list.get(i));
			}
			System.out.println("====================================================================================");
			
		}else {
			System.out.println(">>>> 조회된 회원정보가 없습니다");
		}
			
		
	}

}
