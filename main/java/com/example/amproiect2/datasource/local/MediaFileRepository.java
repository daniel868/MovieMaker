package com.example.amproiect2.datasource.local;

import com.example.amproiect2.entities.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {

    @Query(value = "select COUNT(*) from media_file",
            nativeQuery = true)
    int getEntitiesCount();


    @Query(value = "select * from media_file where id=?1", nativeQuery = true)
    void selectById(Long id);

    @Query(value = "delete from media_file where id=?1", nativeQuery = true)
    int delete(Long id);
}
