package com.example.jwtandwebsocket.service.roleAsync;

import com.example.jwtandwebsocket.dao.roleAsync.RoleAsyncDao;
import com.example.jwtandwebsocket.dto.role.RoleDto;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleAsyncServiceImpl implements RoleAsyncService {

    private final RoleAsyncDao roleAsyncDao;

    @Override
    public ListenableFuture<List<RoleDto>> findAllAsync() {
        return roleAsyncDao.findAllAsync();
    }
}
