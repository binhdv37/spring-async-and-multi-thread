package com.example.jwtandwebsocket.dao.roleAsync;


import com.example.jwtandwebsocket.dto.role.RoleDto;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public interface RoleAsyncDao {

    ListenableFuture<List<RoleDto>> findAllAsync();

}
