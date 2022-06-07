package dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import dao.Dao;
import datapersistant.DataAccessFacade;
import datapersistant.DataPersistantFacade;
import model.Member;
import model.Address;
import model.Author;
import model.Member;

public class MemberDao implements Dao<Member> {
	
	private static DataAccessFacade accessFacade;
	public static DataPersistantFacade persistantFacade;

	public MemberDao() {
		persistantFacade = new DataPersistantFacade();
		accessFacade = new DataAccessFacade();
	}
	
	@Override
	public Member get(String id) {
		return accessFacade.getMember(id);
	}

	@Override
	public Collection<Member> getAll() {
		HashMap<String, Member> members = accessFacade.readMemberMap();
		return members.values()
	            .stream()
	            .collect(Collectors.toList());
	}

	@Override
	public void save(Member t) {
		persistantFacade.saveNewMember(t);
		
	}

	@Override
	public void update(Member t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(Member t) {
		// TODO Auto-generated method stub
		
	}
	
	public Member getMember(String f, String l, String p, String a, String c, String s, String z) {
		var addr= new Address(a,c,s,z);
		return new Member(f, l, p, addr);
	
	}

}
