package com.cotton.dao.version;

import com.cotton.model.user.User;
import com.cotton.model.version.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionDAO extends JpaRepository<AppVersion, Long> {


	List<AppVersion> findByAppTypeOrderByIdDesc(String appType);
}
