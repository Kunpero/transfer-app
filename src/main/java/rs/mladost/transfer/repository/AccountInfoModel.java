package rs.mladost.transfer.repository;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "ACCOUNT_INFO")
@Accessors(chain = true)
public class AccountInfoModel {
    @Id
    private long id;
    @Column
    private long balance;
    @Column
    private String password;
}
