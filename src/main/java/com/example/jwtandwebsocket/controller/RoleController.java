package com.example.jwtandwebsocket.controller;

import com.example.jwtandwebsocket.common.constant.AuthorityConstant;
import com.example.jwtandwebsocket.common.constant.RespCode;
import com.example.jwtandwebsocket.common.exception.MyAppException;
import com.example.jwtandwebsocket.dto.role.RoleDto;
import com.example.jwtandwebsocket.service.security.model.SecurityUser;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController extends BaseController {

    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ROLE_VIEW + "\")")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable(value = "id") UUID roleId) throws MyAppException {
        return new ResponseEntity<>(checkNullAndToBaseResp(roleService.findById(roleId)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ROLE_VIEW + "\")")
    @GetMapping("/all")
    public ResponseEntity<?> getAllRole() throws MyAppException {
        return new ResponseEntity<>(checkNullAndToBaseResp(roleService.findAll()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ROLE_CREATE + "\", \"" + AuthorityConstant.ROLE_UPDATE + "\")")
    @PostMapping
    public ResponseEntity<?> saveRole(@RequestBody RoleDto roleDto) throws MyAppException {
        SecurityUser securityUser = getCurrentUser();
        return new ResponseEntity<>(checkNullAndToBaseResp(roleService.save(roleDto, securityUser.getId())), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ROLE_DELETE + "\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") UUID roleId) throws MyAppException {
        return new ResponseEntity<>(checkNullAndToBaseResp(roleService.deleteById(roleId)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ROLE_VIEW + "\")")
    @GetMapping("/all-async")
    public DeferredResult<ResponseEntity<?>> getAllAsync() throws MyAppException {

        ListenableFuture<List<RoleDto>> data = roleAsyncService.findAllAsync();

        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();
        result.onTimeout(() -> {
            System.out.println("Time out boyz");
            result.setErrorResult(
                    toErrorResponse(new MyAppException("Time out boyz!", RespCode.INTERNAL))
            );
            data.cancel(true);
        });

        Futures.addCallback(data, new FutureCallback<List<RoleDto>>() {
            @Override
            public void onSuccess(List<RoleDto> roleDtos) {
                System.out.println("On success");
                result.setResult(new ResponseEntity<>(toBaseResponse(roleDtos), HttpStatus.OK));
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("On failure");
                result.setErrorResult(
                        toErrorResponse(new MyAppException(throwable.getMessage(), throwable, RespCode.INTERNAL))
                );
            }
        }, MoreExecutors.directExecutor()); // execute in current thread

        System.out.println("Return deffered result, free request thread");

        return result;
    }

}
