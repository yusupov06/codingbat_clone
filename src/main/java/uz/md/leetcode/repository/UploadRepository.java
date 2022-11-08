package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.md.leetcode.domain.Upload;

import java.util.UUID;


public interface UploadRepository extends JpaRepository<Upload, UUID> {
}
