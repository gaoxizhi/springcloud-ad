package net.gaox.sponsor.service;

import net.gaox.common.api.ApiException;
import net.gaox.sponsor.model.vo.CreateUserRequest;
import net.gaox.sponsor.model.vo.CreateUserResponse;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
public interface IUserService {

    /**
     * <h2>创建用户</h2>
     */
    CreateUserResponse createUser(CreateUserRequest request) throws ApiException;
}