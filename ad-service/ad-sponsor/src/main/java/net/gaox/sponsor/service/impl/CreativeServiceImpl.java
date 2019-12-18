package net.gaox.sponsor.service.impl;

import net.gaox.sponsor.dao.CreativeRepository;
import net.gaox.sponsor.entity.Creative;
import net.gaox.sponsor.service.ICreativeService;
import net.gaox.sponsor.model.vo.CreativeRequest;
import net.gaox.sponsor.model.vo.CreativeResponse;
import org.springframework.stereotype.Service;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {

        Creative creative = creativeRepository.save(
                request.convertToEntity()
        );

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}