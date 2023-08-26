package ua.com.alevel.web.dto.response;

import ua.com.alevel.persistence.entity.Card;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardResponseDto extends ResponseDto {

    private String number;
    private String CVV;
    private String balance;
    private String validUntil;

    private String pinCode;

    private String ownerFullName;

    public CardResponseDto(Card card, String ownerFullName) {
        setId(card.getId());
        setCreated(card.getCreated());
        setUpdated(card.getUpdated());
        setVisible(card.getVisible());
        this.balance = card.getBalance().toString();
        this.number = card.getNumber();
        this.CVV = card.getCvv();
        this.pinCode = card.getPinCode();
        this.validUntil = getDateString(card.getValidUntil());
        this.ownerFullName = ownerFullName;
    }

    private String getDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");

        return dateFormat.format(date);
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.toString();
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
