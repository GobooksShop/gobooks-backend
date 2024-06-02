package org.team.bookshop.domain.user.controller;


import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//
//    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @PostMapping("/api/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginDto userLoginDto) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(),
//                    userLoginDto.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            String jwt = jwtUtil.generateToken(userLoginDto.getEmail());
//            return ResponseEntity.ok(new AuthResponse(jwt));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Authentication failed");
//        }
//    }

}
