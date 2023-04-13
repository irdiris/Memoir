package com.example.implmentation.Models.HPCSchedule;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Researcher.Researcher;
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
@Table(name ="HPCSchedule")
public class HPCSchedule {
    @ManyToOne
    @Id
    @MapsId("id")
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_Researcher_Using"))
    private Researcher researcher;
    @OneToOne
    @Id
    @JoinColumn(name = "itemId",foreignKey = @ForeignKey(name = "fk_Hpc_InUse"), referencedColumnName = "serialNumber")
    private Items items;
    private String dateOfAcquisition;
    private String dateOfReturn;
    private String hours;
    private String state;

}
