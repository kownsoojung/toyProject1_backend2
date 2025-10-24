package toy.project.apiserver.domain.common.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import toy.project.apiserver.domain.common.user.entity.RefreshTokenEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    /**
     * 토큰으로 조회
     */
    Optional<RefreshTokenEntity> findByToken(String token);

    /**
     * 사용자 ID로 조회
     */
    List<RefreshTokenEntity> findByUserId(Long userId);

    /**
     * 사용자 ID와 만료되지 않은 유효한 토큰 조회
     */
    @Query("SELECT rt FROM RefreshTokenEntity rt WHERE rt.userId = :userId " +
           "AND rt.revoked = false AND rt.expiresAt > :now")
    List<RefreshTokenEntity> findValidTokensByUserId(
            @Param("userId") Long userId,
            @Param("now") LocalDateTime now
    );

    /**
     * 사용자의 모든 토큰 무효화
     */
    @Modifying
    @Query("UPDATE RefreshTokenEntity rt SET rt.revoked = true WHERE rt.userId = :userId")
    void revokeAllByUserId(@Param("userId") Long userId);

    /**
     * 만료된 토큰 삭제
     */
    @Modifying
    @Query("DELETE FROM RefreshTokenEntity rt WHERE rt.expiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);

    /**
     * 토큰 존재 여부
     */
    boolean existsByToken(String token);
}

