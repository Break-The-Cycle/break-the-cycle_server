package brave.btc.controller;

import brave.btc.domain.User;
import brave.btc.dto.dupCheck.DupCheckResponseDto;
import brave.btc.dto.dupCheck.DupCheckRequestDto;
import brave.btc.dto.login.LoginRequestDto;
import brave.btc.dto.login.LoginResponseDto;
import brave.btc.dto.register.RegisterRequestDto;
import brave.btc.dto.register.RegisterResponseDto;
import brave.btc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    @Operation(summary = "register", description = "회원 가입")
    @PostMapping("/api/v1/register")
    public RegisterResponseDto registerV1(@RequestBody @Valid RegisterRequestDto request, BindingResult bindingResult) {
        RegisterResponseDto response = new RegisterResponseDto(false, false, false, false, false, false);

        //값 조건 오류
        if (bindingResult.hasErrors()) {
            setError(bindingResult, response);
        } else if (request.isId_dup()){         //아이디 중복체크 여부
            response.setId_dup_error(true);
        } else {
            User user = new User(request.getName(), request.getPhone_number(),request.getLogin_id(), request.getPassword());
            userService.join(user);
            response.setSuccess(true);
            log.info("회원 가입 완료");
        }
        return response;
    }

    @Operation(summary = "ID Duplication Check", description = "아이디 중복 체크")
    @GetMapping("api/v1/register/dupcheck")
    public DupCheckResponseDto dupCheckV1(@RequestBody DupCheckRequestDto request) {

        DupCheckResponseDto response = new DupCheckResponseDto(true);

        String id = request.getId();
        if (userService.findUser(id) == null) {
            response.setExist(false);
        }

        return response;
    }

    @Operation(summary = "Login", description = "로그인 ID PW 확인")
    @PostMapping("api/v1/login")
    public LoginResponseDto loginV1(@RequestBody LoginRequestDto request) {

        LoginResponseDto response = new LoginResponseDto(true);

        User user = userService.findUser(request.getId());

        //아이디 or 비밀번호 오류
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            response.setCorrect(false);
        }
        return response;
    }

    private static void setError(BindingResult bindingResult, RegisterResponseDto response) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        for (ObjectError error : errors) {
            String message = error.getDefaultMessage();
            if (message.equals("이름 오류")) {
                response.setName_error(true);
            } else if (message.equals("전화번호 오류")) {
                response.setPhone_error(true);
            } else if (message.equals("아이디 오류")) {
                response.setId_error(true);
            } else {
                response.setPwd_error(true);
            }
        }
    }

}
