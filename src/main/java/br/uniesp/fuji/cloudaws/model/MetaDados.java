package br.uniesp.fuji.cloudaws.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Entity
@Table(name = "META_DADOS")
@Getter @Setter
public class MetaDados {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "VERSION")
    private String version;

    public MetaDados(String fileName, String filePath, String version) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.version = version;
    }
}