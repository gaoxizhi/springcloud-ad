package net.gaox.sponsor.service;

import net.gaox.common.api.ApiException;
import net.gaox.sponsor.model.vo.AdUnitDistrictRequest;
import net.gaox.sponsor.model.vo.AdUnitDistrictResponse;
import net.gaox.sponsor.model.vo.AdUnitItRequest;
import net.gaox.sponsor.model.vo.AdUnitItResponse;
import net.gaox.sponsor.model.vo.AdUnitKeywordRequest;
import net.gaox.sponsor.model.vo.AdUnitKeywordResponse;
import net.gaox.sponsor.model.vo.AdUnitRequest;
import net.gaox.sponsor.model.vo.AdUnitResponse;
import net.gaox.sponsor.model.vo.CreativeUnitRequest;
import net.gaox.sponsor.model.vo.CreativeUnitResponse;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
public interface IAdUnitService {

    AdUnitResponse createUnit(AdUnitRequest request) throws ApiException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws ApiException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws ApiException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws ApiException;

    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws ApiException;
}
