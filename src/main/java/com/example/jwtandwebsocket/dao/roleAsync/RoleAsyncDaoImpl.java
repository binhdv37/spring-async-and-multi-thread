package com.example.jwtandwebsocket.dao.roleAsync;

import com.example.jwtandwebsocket.dto.role.RoleDto;
import com.example.jwtandwebsocket.entity.role.RoleEntity;
import com.example.jwtandwebsocket.repo.role.RoleRepository;
import com.example.jwtandwebsocket.utils.service.JpaExecutorService;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RoleAsyncDaoImpl implements RoleAsyncDao {

    private final RoleRepository roleRepository;
    @Lazy
    private final JpaExecutorService jpaExecutorService;

    @Override
    public ListenableFuture<List<RoleDto>> findAllAsync() {
        return Futures.submit(() -> {
            Thread.sleep(3000);
            return roleRepository.findAll().stream()
                    .map(RoleEntity::toData)
                    .collect(Collectors.toList());
        }, jpaExecutorService.getExecutorService());
    }

}
