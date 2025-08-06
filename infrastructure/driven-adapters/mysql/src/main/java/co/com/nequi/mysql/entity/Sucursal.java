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
@Table("sucursal")
public class Sucursal {
  @Id private Integer id;
  private Integer franchiseId;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
