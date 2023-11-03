package nftauction.web.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "top_collectors")

public class TopCollector {
  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "nft_count")
  private int nftCount;

}
