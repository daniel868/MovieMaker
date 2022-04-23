package com.example.amproiect2.entities;

import javax.persistence.*;

@Entity
@Table
public class MediaFile {
    @Id
    @SequenceGenerator(
            name="file_sequence",
            sequenceName = "file_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "file_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(nullable = false)
    private String awsObjectKey;

    @Column(nullable = false)
    private String objectFileName;

    @Column(nullable = false)
    private String projectName;

    public MediaFile() {
    }

    public MediaFile(String awsObjectKey, String objectFileName, String projectName) {
        this.awsObjectKey = awsObjectKey;
        this.objectFileName = objectFileName;
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAwsObjectKey() {
        return awsObjectKey;
    }

    public void setAwsObjectKey(String awsObjectKey) {
        this.awsObjectKey = awsObjectKey;
    }

    public String getObjectFileName() {
        return objectFileName;
    }

    public void setObjectFileName(String objectFileName) {
        this.objectFileName = objectFileName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
