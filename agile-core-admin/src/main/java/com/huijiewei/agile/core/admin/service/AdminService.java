package com.huijiewei.agile.core.admin.service;

import com.devskiller.friendly_id.FriendlyId;
import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.entity.AdminAccessToken;
import com.huijiewei.agile.core.admin.manager.AdminGroupPermissionManager;
import com.huijiewei.agile.core.admin.mapper.AdminMapper;
import com.huijiewei.agile.core.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.admin.request.AdminLoginRequest;
import com.huijiewei.agile.core.admin.request.AdminRequest;
import com.huijiewei.agile.core.admin.response.AdminAccountResponse;
import com.huijiewei.agile.core.admin.response.AdminLoginResponse;
import com.huijiewei.agile.core.admin.response.AdminResponse;
import com.huijiewei.agile.core.admin.security.AdminIdentity;
import com.huijiewei.agile.core.exception.BadRequestException;
import com.huijiewei.agile.core.exception.ConflictException;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.response.ListResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
public class AdminService {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final AdminRepository adminRepository;
    private final AdminAccessTokenRepository adminAccessTokenRepository;
    private final AdminGroupPermissionManager adminGroupPermissionManager;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        AdminAccessTokenRepository adminAccessTokenRepository,
                        AdminGroupPermissionManager adminGroupPermissionManager) {
        this.adminRepository = adminRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
        this.adminGroupPermissionManager = adminGroupPermissionManager;
    }

    public AdminLoginResponse login(String clientId, String userAgent, @Valid AdminLoginRequest request) {
        Admin admin = request.getAdmin();
        String accessToken = FriendlyId.createFriendlyId();

        Optional<AdminAccessToken> adminAccessTokenOptional = this.adminAccessTokenRepository.findByClientId(clientId);

        AdminAccessToken adminAccessToken = adminAccessTokenOptional.orElseGet(AdminAccessToken::new);

        if (!adminAccessToken.hasId()) {
            adminAccessToken.setAdminId(admin.getId());
            adminAccessToken.setClientId(clientId);
        }

        adminAccessToken.setUserAgent(userAgent);
        adminAccessToken.setAccessToken(accessToken);

        this.adminAccessTokenRepository.save(adminAccessToken);

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminBaseResponse(admin));
        adminLoginResponse.setGroupPermissions(this.adminGroupPermissionManager.getPermissionsByAdminGroupId(admin.getAdminGroup().getId()));
        adminLoginResponse.setGroupMenus(this.adminGroupPermissionManager.getMenusByAdminGroupId(admin.getAdminGroup().getId()));
        adminLoginResponse.setAccessToken(accessToken);

        return adminLoginResponse;
    }

    public void logout(AdminIdentity identity) {
        this.adminAccessTokenRepository.deleteByAdminIdAndClientId(
                identity.getAdmin().getId(),
                identity.getClientId());

    }

    public AdminAccountResponse account(AdminIdentity identity) {
        Admin admin = identity.getAdmin();

        AdminAccountResponse adminAccountResponse = new AdminAccountResponse();
        adminAccountResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminBaseResponse(admin));
        adminAccountResponse.setGroupPermissions(this.adminGroupPermissionManager.getPermissionsByAdminGroupId(admin.getAdminGroup().getId()));
        adminAccountResponse.setGroupMenus(this.adminGroupPermissionManager.getMenusByAdminGroupId(admin.getAdminGroup().getId()));

        return adminAccountResponse;
    }

    public ListResponse<AdminResponse> getAll() {
        ListResponse<AdminResponse> response = new ListResponse<>();
        response.setItems(AdminMapper.INSTANCE.toAdminResponses(this.adminRepository.findAllWithAdminGroupByOrderByIdAsc()));

        return response;
    }

    public AdminResponse getById(Integer id) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        return AdminMapper.INSTANCE.toAdminResponse(adminOptional.get());
    }

    @Validated(AdminRequest.Create.class)
    public AdminResponse create(@Valid AdminRequest request) {
        if (this.adminRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("手机号码已被使用");
        }

        if (this.adminRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("电子邮箱已被使用");
        }

        Admin admin = AdminMapper.INSTANCE.toAdmin(request);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        this.adminRepository.save(admin);

        return AdminMapper.INSTANCE.toAdminResponse(admin);
    }

    @Validated(AdminRequest.Edit.class)
    public void profile(@Valid AdminRequest request, AdminIdentity identity) {
        this.update(identity.getAdmin(), request, true);
    }

    public AdminResponse profile(AdminIdentity identity) {
        return AdminMapper.INSTANCE.toAdminResponse(identity.getAdmin());
    }

    private Admin update(Admin current, AdminRequest request, boolean isOwner) {
        if (this.adminRepository.existsByPhoneAndIdNot(request.getPhone(), current.getId())) {
            throw new BadRequestException("手机号码已被使用");
        }

        if (this.adminRepository.existsByEmailAndIdNot(request.getEmail(), current.getId())) {
            throw new BadRequestException("电子邮箱已被使用");
        }

        Admin admin = AdminMapper.INSTANCE.toAdmin(request, current);

        if (!StringUtils.isEmpty(request.getPassword())) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (isOwner) {
            admin.setAdminGroup(current.getAdminGroup());
        }

        this.adminRepository.save(admin);

        return admin;
    }

    @Validated(AdminRequest.Edit.class)
    public AdminResponse edit(Integer id, @Valid AdminRequest request, AdminIdentity identity) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        Admin current = adminOptional.get();

        Admin admin = this.update(current, request, identity.getAdmin().getId().equals(current.getId()));

        return AdminMapper.INSTANCE.toAdminResponse(admin);
    }

    public void delete(Integer id, AdminIdentity identity) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        Admin admin = adminOptional.get();

        if (admin.getId().equals(identity.getAdmin().getId())) {
            throw new ConflictException("管理员不能删除自己");
        }

        this.adminRepository.delete(admin);
    }
}
