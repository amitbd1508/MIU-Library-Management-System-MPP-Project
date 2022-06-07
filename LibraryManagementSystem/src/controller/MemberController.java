package controller;

import java.util.HashMap;
import java.util.List;

import common.AppContext;
import common.Role;
import model.Book;
import model.BookCopy;
import model.Member;
import service.MemberService;

public class MemberController {

	private MemberService mService;
	public MemberController() {
		this.mService = new MemberService();
	}
	
	public Member addNewMember(String f, String l, String p, String a, String c, String s, String z) throws Exception {
		Role role = AppContext.loginUser.getUserRole();
		if(role == Role.LIBRARIAN || role == Role.ADMIN) {
			return this.mService.addNewMember(f, l, p, a, c, s, z);
		}
		throw new Exception("Unauthorize access");
		
	}
	
	public Member getMemberByid(String id) {
		
		return mService.getMemberById(id);
	}
	
	public boolean checkoutBook(Member member, BookCopy nextAvailableCopy) {
		
		return mService.checkoutBook(nextAvailableCopy, member);
		
	}
	
	public List<Member> getLibraryMembers() {
		
		return this.mService.getAllMember();
	}
}
