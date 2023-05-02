package com.example.tinyurlclone.kgs.model;

import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;


@Entity
@Table(name = "kgs_keys")
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "machine_id")
    private Short machineId = 1;

    @Override
    public String toString() {
        return new UID(this.id, ObjectID.URL, this.machineId).toString();
    }
}
