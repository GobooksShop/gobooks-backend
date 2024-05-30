package org.team.bookshop.domain.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

public class User {

    @Id @GeneratedValue(strategy =IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private String role;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private LocalDateTime deletedAt;


//    private String profileImage;//프로필이미지


//    private String provider; //인증공급자(구글 등)
//    private String providerId; //인증공급자 식별아이디
//    private String providerToken; //인증공급자 토큰
//    private String providerRefreshToken;//인증공급자 리프레시토큰
//    private String providerTokenExpire;//인증공급자 토큰만료일


//    private boolean isDeleted; //삭제여부
//    private boolean isVerified; //인증여부
//    private boolean isBlocked; //차단여부
//    private boolean isEmailVerified; //이메일인증여부
//    private boolean isPhoneVerified; //휴대폰인증여부
//    private boolean isTwoFactor; //2단계인증활성화여부
//    private boolean isTwoFactorVerified; //2단계인증확인여부
//    private boolean isTwoFactorBlocked; //2단계인증차단여부
//    private boolean isTwoFactorEmailVerified; //2단계이메일인증여부
//    private boolean isTwoFactorPhoneVerified; //2단계휴대폰인증여부


    /**
     * OAuth??
     */
//    private boolean isTwoFactorApp; //앱 기반 2단계인증활성화여부
//    private boolean isTwoFactorAuth; //2단계인증활성화여부
//    private boolean isTwoFactorAuthVerified; //2단계인증확인여부
//    private boolean isTwoFactorAuthBlocked; //2단계인증차단여부
//    private boolean isTwoFactorAuthEmailVerified; //2단계인증이메일인증여부
//    private boolean isTwoFactorAuthPhoneVerified; //2단계인증휴대폰인증여부
//    private boolean isTwoFactorAuthAppVerified; //앱 기반 2단계 인증확인여부
//    private boolean isTwoFactorAuthAppBlocked; //앱 기반 2단계 차단여부
//    private boolean isTwoFactorAuthAppEmailVerified; //앱 기반 2단계 이메일인증여부
//    private boolean isTwoFactorAuthAppPhoneVerified; //앱 기반 2단계 전화인증여부
//    private boolean isTwoFactorAuthAppAuth; //다른 앱 기반 인증방법 사용여부
}
