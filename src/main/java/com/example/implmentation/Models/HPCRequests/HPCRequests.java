package com.example.implmentation.Models.HPCRequests;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Researcher.Researcher;
import com.example.implmentation.Models.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "HPCRequests")
public class HPCRequests {
    @ManyToOne
    @Id
    @MapsId("id")
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_Researcher"))
    private Researcher researcher;

    @ManyToOne
    @Id
    @JoinColumn(name = "itemId",foreignKey = @ForeignKey(name = "fk_Hpc"), referencedColumnName = "serialNumber")
    private Items items;
    private String dateOfAcquisition;
    private String dateOfReturn;
    private String Hours;
}
