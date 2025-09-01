package com.bridgeon.app.domain.info.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foundation_infos")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoundationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foundation_info_id",  nullable = false)
    private Long id;

}
