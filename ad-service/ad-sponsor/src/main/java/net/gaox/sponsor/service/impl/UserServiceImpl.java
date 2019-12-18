package net.gaox.sponsor.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.gaox.common.api.ApiException;
import net.gaox.sponsor.constant.Constants;
import net.gaox.sponsor.dao.AdUserRepository;
import net.gaox.sponsor.entity.AdUser;
import net.gaox.sponsor.service.IUserService;
import net.gaox.sponsor.utils.CommonUtils;
import net.gaox.sponsor.model.vo.CreateUserRequest;
import net.gaox.sponsor.model.vo.CreateUserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws ApiException {

        if (!request.validate()) {
            throw new ApiException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.
                findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new ApiException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername())
        ));

        return new CreateUserResponse(
                newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime()
        );
    }
}