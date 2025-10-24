package toy.project.apiserver.com.provider;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;
    private final long tokenRenewalThreshold;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration}") long accessTokenExpTime,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpTime,
            @Value("${jwt.token-renewal-threshold}") long tokenRenewalThreshold
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
        this.tokenRenewalThreshold = tokenRenewalThreshold;
    }

    /**
     * Access Token 생성
     * @param userEntity
     * @return Access Token String
     */
    public String createAccessToken(toy.project.apiserver.domain.common.user.entity.UserEntity userEntity) {
        return createToken(userEntity, accessTokenExpTime, "ACCESS");
    }

    /**
     * Refresh Token 생성
     * @param userEntity
     * @return Refresh Token String
     */
    public String createRefreshToken(toy.project.apiserver.domain.common.user.entity.UserEntity userEntity) {
        return createToken(userEntity, refreshTokenExpTime, "REFRESH");
    }

    /**
     * JWT 생성
     * @param userEntity
     * @param expireTime
     * @param tokenType
     * @return JWT String
     */
    private String createToken(toy.project.apiserver.domain.common.user.entity.UserEntity userEntity, long expireTime, String tokenType) {
        Claims claims = Jwts.claims();
        claims.put("memberId", userEntity.getUserId());
        claims.put("username", userEntity.getUsername());
        claims.put("email", userEntity.getEmail());
        claims.put("role", userEntity.getRole().name());
        claims.put("centerId", userEntity.getCenterId());
        claims.put("tokenType", tokenType);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime / 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Token에서 User ID 추출
     * @param token
     * @return User ID
     */
    public Long getUserId(String token) {
        return parseClaims(token).get("memberId", Long.class);
    }


    /**
     * JWT 검증
     * @param token
     * @return IsValidate
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * 토큰 갱신이 필요한지 확인
     * @param token
     * @return 갱신 필요 여부
     */
    public boolean shouldRenewToken(String token) {
        try {
            Claims claims = parseClaims(token);
            Date expiration = claims.getExpiration();
            Date now = new Date();
            
            long timeUntilExpiration = expiration.getTime() - now.getTime();
            
            // 만료 임계값(15분) 이내면 갱신 필요
            return timeUntilExpiration > 0 && timeUntilExpiration < tokenRenewalThreshold;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Access Token 만료시간 반환 (밀리초)
     */
    public long getAccessTokenExpTime() {
        return accessTokenExpTime;
    }

    /**
     * Refresh Token 만료시간 반환 (밀리초)
     */
    public long getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    /**
     * 토큰에서 토큰 타입 추출
     */
    public String getTokenType(String token) {
        return parseClaims(token).get("tokenType", String.class);
    }
}
