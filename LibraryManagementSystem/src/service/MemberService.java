package service;

import java.time.LocalDate;

import dao.impl.BookDao;
import dao.impl.MemberDao;
import datapersistant.DataPersistantFacade;

import java.util.Collection;
import java.util.List;

import dao.impl.MemberDao;
import datapersistant.DataPersistantFacade;
import model.*;

public class MemberService {
	private MemberDao memberDao;
	private static BookDao bookDao;
	private DataPersistantFacade dataPersistantFacade;
	
	
	
	public MemberService() {
		this.memberDao = new MemberDao();
		this.dataPersistantFacade = new DataPersistantFacade();
		bookDao = new BookDao();
	}



	public Member addNewMember(String f, String l, String p, String a, String c, String s, String z) {
		try {
			
			var addr= new Address(a,c,s,z);
			Member m =  new Member(f, l, p, addr);
			memberDao.save(m);
			
			return m;
		}catch(Exception ex){
			System.out.println("Exception ad member service -> addNewMember msg:"+ex.getMessage());
			return null;
		}
	}
	
	public List<Member> getAllMember(){
		Collection<Member> members = this.memberDao.getAll();
		return (List<Member>)members;
		
	}
	
	public Member getMemberById(String id) {
		
		return memberDao.get(id);
	}
	
	public BookCopy getAvailableCopy(Book book) {

		for (BookCopy bc : book.getCopies()) {
			if (bc.isAvailable()) {
				return bc;
			}
		}

		return null;
	}

	public boolean checkoutBook(BookCopy nextAvailableCopy, Member member) {


		if (nextAvailableCopy != null && member != null) {
			Book book = nextAvailableCopy.getBook();
			int length = book.getMaxCheckoutLength();
			LocalDate today = LocalDate.now();
			LocalDate dueDate = today.plusDays(length);
			CheckoutRecord record = (member.getRecord() == null) ? new CheckoutRecord() : member.getRecord();
			record.add(nextAvailableCopy);
			member.setRecord(record);
			nextAvailableCopy.changeAvailability();
			memberDao.save(member);
			bookDao.save(book);
			return true;
			
		}
		return false;

	}


}
