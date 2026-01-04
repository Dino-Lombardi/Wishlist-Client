package be.wishlist.javabeans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	public static LocalDate parseFrenchDate(String date) throws DateTimeParseException {
		if (date == null || date.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
	
	public static LocalDate parseEnglishDate(String date) throws DateTimeParseException{
		if (date == null || date.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
	}
	
	public static GiftListStatus parseGiftListStatus(String status) throws IllegalArgumentException {
		if (status == null || status.trim().isEmpty()) {
			return null;
		}
		return GiftListStatus.valueOf(status);
	}
	
	public boolean isDateExpired() {
		return (!expirationdate.isAfter(LocalDate.now()));
	}
		
	
	public Map<String, String> validate() {
        Map<String, String> errors = new HashMap<>();
        
        // Validation du titre
        if (this.title == null || this.title.trim().isEmpty()) {
            errors.put("title", "Le titre est obligatoire.");
        } else if (this.title.length() > 50) {
            errors.put("title", "Le titre ne doit pas dépasser 50 caractères.");
        }
        
        // Validation de la description
        if (this.description != null && this.description.length() > 500) {
            errors.put("description", "La description ne doit pas dépasser 500 caractères.");
        }
        
        // Validation de la date d'expiration
        if (this.expirationdate == null) {
            errors.put("expirationdate", "La date d'expiration est obligatoire.");
        } else {
            LocalDate today = LocalDate.now();
            if (!this.expirationdate.isAfter(today)) {
                errors.put("expirationdate", "La date d'expiration doit être strictement dans le futur (pas aujourd'hui).");
            }
        }
        
        // Validation du statut
        if (this.status == null) {
            errors.put("status", "Le statut est obligatoire.");
        } else if (this.status == GiftListStatus.EXPIRED) {
            errors.put("status", "Le statut EXPIRED ne peut pas être défini manuellement.");
        }
        
        // Validation du propriétaire (pour création)
        if (this.owner == null) {
            errors.put("owner", "La liste doit avoir un propriétaire.");
        }
        
        return errors;
    }
	
	
	public void sortGiftsByPriority() {
        if (gifts != null && !gifts.isEmpty()) {
            gifts.sort((g1, g2) -> Integer.compare(g2.getPriority(), g1.getPriority()));
        }
    }
	
	// Appeler dans GiftDAO
	public boolean insert() {
		return giftlistDAO.create(this);
	}
	
	public static GiftList find(int id) {
		return giftlistDAO.find(id);
	}
	
	public static ArrayList<GiftList> getGiftListsByUser(User user) {
		return giftlistDAO.findAll(user.getIdUser());
	}
	
	public boolean fetchGifts() {
		this.gifts = Gift.getGiftsByGiftList(idgiftlist);
		return gifts != null;
	}
	
	public boolean update() {
		return giftlistDAO.update(this);
	}
	
	public boolean updateExpiredDate() {
		if(this.isDateExpired()) {
			status = GiftListStatus.EXPIRED;
			return giftlistDAO.update(this);
		}
			
		return false;
	}
	
	public boolean delete() {
		return giftlistDAO.delete(this);
	}
	
	
	@Override
	public String toString() {
		return "GiftList [idgiftlist=" + idgiftlist + ", title=" + title + ", description=" + description
				+ ", creationdate=" + creationdate + ", expirationdate=" + expirationdate + ", status=" + status
				+ ", owner=" + owner + ", gifts=" + gifts + "]";
	}
}
