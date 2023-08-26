package ua.com.alevel.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity {
    @Column(name = "card_number", unique = true)
    private String number;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valid_until")
    private Date validUntil;

    @Column(name = "card_cvv")
    private String cvv;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "card_balance")
    private BigDecimal balance;

    @Column(name = "owner_id")
    private String ownerId;

    public Card() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 4);
        validUntil = calendar.getTime();

//        balance = BigDecimal.valueOf(0);
        balance = BigDecimal.valueOf(new Random().nextInt(1000000) + 1);

    }

    public Card(String number, Date validUntil, String cvv, BigDecimal balance, String pinCode) {
        this.number = number;
        this.validUntil = validUntil;
        this.cvv = cvv;
        this.balance = balance;
        this.pinCode = pinCode;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number.equals(card.number) && cvv.equals(card.cvv) && pinCode.equals(card.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, cvv, pinCode);
    }
}
