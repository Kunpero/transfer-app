package rs.mladost.transfer.repository;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TRANSFER_OPERATION")
@Accessors(chain = true)
public class TransferOperationModel {
    @Id
    private long id;
    private long amount;
    @Column(name = "SENDER_ID")
    private long senderId;
    @Column(name = "RECEIVER_ID")
    private long receiverId;
    @Column(name = "OPERATION_DATE")
    private LocalDateTime operationDate;
}
