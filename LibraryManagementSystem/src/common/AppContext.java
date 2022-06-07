package common;

import java.util.HashSet;
import java.util.Set;

import model.Author;
import model.User;

public class AppContext {
	public static User loginUser;
	public static Set<Author>  author = new HashSet<Author>(); 

}
