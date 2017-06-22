package com.jzwy.zkx.product.rest.api.action.repair;

import com.jzwy.zkx.core.assembler.VODTOAssembler;
import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.product.rest.api.common.BaseAction;
import com.jzwy.zkx.product.rest.api.vo.FaultCategoryVO;
import com.jzwy.zkx.product.service.repair.dto.FaultCategoryDTO;
import com.jzwy.zkx.product.service.repair.query.FaultCategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.jzwy.zkx.product.service.repair.FaultCategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by wangsic on 6/21/2017.
 */
@Controller
@RequestMapping(value = "/faultcategory")
public class FaultCategoryAction extends BaseAction {
    @Autowired
    private FaultCategoryService faultCategoryService;

    @Autowired
    private VODTOAssembler<FaultCategoryVO, FaultCategoryDTO> faultCategoryVODTOAssembler;

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public Response<Void> add(@RequestBody FaultCategoryVO faultCategoryVO) {
        try {
            FaultCategoryDTO faultCategoryDTO = this.faultCategoryVODTOAssembler.voToDto(faultCategoryVO);
            Response<Void> result = faultCategoryService.add(faultCategoryDTO);

            if (!result.isSuccessful()) {
                return newErrorReturnJson(result.getMessage());
            }
            return newDataReturnJson(result.getData());

        } catch (Exception e) {
            return newErrorReturnJson("添加故障类别操作操作失败");
        }


    }

    @ResponseBody
    @RequestMapping(value = "/detail", produces = "application/json; charset=utf-8")
    public Response detail(@RequestParam("id") Long id) {
        try {
            Response<FaultCategoryDTO> response = this.faultCategoryService.get(id);
            FaultCategoryVO faultCategoryVO = faultCategoryVODTOAssembler.dtoToVo(response.getData());
            return newDataReturnJson(faultCategoryVO);

        } catch (Exception e) {
            return newErrorReturnJson("查询故障类别详情失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/list", produces = "application/json; charset=utf-8")
    public Response list(HttpServletRequest request) {
        try {
            FaultCategoryQuery query = new FaultCategoryQuery();
            query.setCode(this.getString(request, "code", null));
            query.setName(this.getString(request, "name", null));
            query.setPageIndex(this.getInt(request, "pageIndex", null));
            query.setPageSize(this.getInt(request, "pageSize", null));
            PagedResultsResponse<FaultCategoryDTO> response = faultCategoryService.list(query);
            List<FaultCategoryVO> faultCategoryVOList = faultCategoryVODTOAssembler.dtoListToVoList(response.getData());
            return newListReturnResponse(faultCategoryVOList, response.getPage());
        } catch (Exception e) {
            return newErrorReturnJson("查询故障类别列表失败");
        }
    }


}
