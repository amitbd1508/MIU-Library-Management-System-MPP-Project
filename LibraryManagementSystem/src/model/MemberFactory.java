package model;

public abstract class MemberFactory {

    public static Member create(String fname, String lname, String tel, String street, String city, String state, String zipcode) {
        Address address = new Address(street, city, state, zipcode);
        CheckoutRecord record = new CheckoutRecord();
        Member lm = new Member(fname, lname, tel, address);
        record.setLibraryMember(lm);

        return lm;
    }
}
