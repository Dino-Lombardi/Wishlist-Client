package be.wishlist.javabeans;

import java.time.LocalDate;
import java.util.ArrayList;

import be.wishlist.DAO.DAO;
import be.wishlist.DAO.GiftListDAO;
import be.wishlist.enums.GiftListStatus;

public class GiftList {
	private static final DAO<GiftList> giftlistDAO = new GiftListDAO();
	
	
	private int idgiftlist;
	private String title;
	private String description;
	private LocalDate creationdate;
	private LocalDate expirationdate;
	private GiftListStatus status;
	private String sharelink;
	private User owner;
	private ArrayList<Gift> gifts;
	
	public GiftList() {
		this.gifts = new ArrayList<Gift>();
	}
	
	public GiftList(int idgiftlist, String title, String description, LocalDate creationdate,
			LocalDate expirationdate, GiftListStatus status, String sharelink, User owner) {
		this.idgiftlist = idgiftlist;
		this.title = title;
		this.description = description;
		this.creationdate = creationdate;
		this.expirationdate = expirationdate;
		this.status = status;
		this.sharelink = sharelink;
		this.owner = owner;
		this.gifts = new ArrayList<Gift>();
	}
		
	
	public int getIdGiftlist() {
		return idgiftlist;
	}
	
	public void setIdGiftlist(int idgiftlist) {
		this.idgiftlist = idgiftlist;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDate getCreationdate() {
		return creationdate;
	}
	
	public void setCreationDate(LocalDate creationdate) {
		this.creationdate = creationdate;
	}
	
	public LocalDate getExpirationDate() {
		return expirationdate;
	}
	
	public void setExpirationDate(LocalDate expirationdate) {
		this.expirationdate = expirationdate;
	}
	
	public GiftListStatus getStatus() {
		return status;
	}
	
	public void setStatus(GiftListStatus status) {
		this.status = status;
	}
	
	public String getSharelink() {
		return sharelink;
	}
	
	public void setSharelink(String sharelink) {
		this.sharelink = sharelink;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public ArrayList<Gift> getGifts() {
		return gifts;
	}
	
	public void setGifts(ArrayList<Gift> gifts) {
		this.gifts = gifts;
	}
	
	public void addGift(Gift gift) {
		this.gifts.add(gift);
	}
	
	public void removeGift(Gift gift) {
		this.gifts.remove(gift);
	}
	
	// Appeler dans GiftDAO
	public boolean insert() {
		return false;
	}
	
	public GiftList find(int id) {
		return null;
	}
	
	public static ArrayList<GiftList> getGiftListsByUser(User user) {
		return giftlistDAO.findAll(user.getIdUser());
	}
	
	
	@Override
	public String toString() {
		return "GiftList [idgiftlist=" + idgiftlist + ", title=" + title + ", description=" + description
				+ ", creationdate=" + creationdate + ", expirationdate=" + expirationdate + ", status=" + status
				+ ", owner=" + owner + ", gifts=" + gifts + "]";
	}
		
	
	
	
}
