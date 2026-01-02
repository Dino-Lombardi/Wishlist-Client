package be.wishlist.javabeans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import be.wishlist.enums.GiftStatus;
import be.wishlist.enums.InvitationStatus;

public class test 
{
	public static void main(String[] args) {
		User user = new User(26,null,null,null,null);
		GiftList gl = new GiftList(1,null,null,null,null,null,null,null);
		Invitation i = new Invitation(InvitationStatus.PENDING, user, gl);
		Invitation.create(i);
	}
	
}
