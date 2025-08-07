package co.com.nequi.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product")
public class ProductEntity {
  @Id private Integer id;
  private Integer sucursalId;
  private String name;
  private Integer stock;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
