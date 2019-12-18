package net.gaox.sponsor.service;

import net.gaox.sponsor.model.vo.CreativeRequest;
import net.gaox.sponsor.model.vo.CreativeResponse;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request);
}
