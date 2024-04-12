package homework0409;

import lombok.Data;

import java.util.Date;

@Data
public class Account {
    private int accountId;
    private Date openDate;
    private String openPoint;
    private String accountOwner;
    private int balance;
    private String isOpenBank;
    private String accountNum;
    private int userId;
}