package com.example.jwtandwebsocket.service.roleAsync;

import com.example.jwtandwebsocket.dto.role.RoleDto;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public interface RoleAsyncService {

    ListenableFuture<List<RoleDto>> findAllAsync();

}
